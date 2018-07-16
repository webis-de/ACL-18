package de.aitools.ie.articles;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.uima.UIMAFramework;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

import de.aitools.ie.uima.type.article.ArticleMetaData;
import de.aitools.ie.uima.type.article.MainText;
import de.aitools.ie.uima.type.article.Title;
import de.aitools.ie.uima.usage.UIMAAnnotationFileWriter;

public class DataPreprocessor implements Consumer<Article> {

  //////////////////////////////////////////////////////////////////////////////
  //                                   UIMA                                   //
  //////////////////////////////////////////////////////////////////////////////

  private static final String TYPE_SYSTEM_NAME =
      "/uima-descriptors/type-systems/ArticleTypeSystem.xml";

  private static final TypeSystemDescription TYPE_SYSTEM =
      DataPreprocessor.createTypeSystem();

  private static final String TITLE_SEPARATOR = "\n\n";

  //////////////////////////////////////////////////////////////////////////////
  //                                  FOLDS                                   //
  //////////////////////////////////////////////////////////////////////////////
  
  public static final Set<String> FOLD1 = DataPreprocessor.makeSet(
      "politico", "addicting-info", "right-wing-news"
  );
  
  public static final Set<String> FOLD2 = DataPreprocessor.makeSet(
      "abc", "the-other-98", "freedom-daily"
  );
  
  public static final Set<String> FOLD3 = DataPreprocessor.makeSet(
      "cnn", "occupy-democrats", "eagle-rising"
  );

  //////////////////////////////////////////////////////////////////////////////
  //                                 MEMBERS                                  //
  //////////////////////////////////////////////////////////////////////////////
  
  private final File outputDirectory;
  
  private final CasWriter[] trainingDirectories;
  
  private final CasWriter[] testDirectories;

  //////////////////////////////////////////////////////////////////////////////
  //                              CONSTRUCTOR                                 //
  //////////////////////////////////////////////////////////////////////////////
  
  public DataPreprocessor(final File outputDirectory)
  throws IOException {
    outputDirectory.mkdirs();
    if (!outputDirectory.isDirectory()) {
      throw new IOException("Not a directory " + outputDirectory);
    }
    this.outputDirectory = outputDirectory;
    
    this.trainingDirectories = new CasWriter[] {
      this.createWriter("fold1-training"),
      this.createWriter("fold2-training"),
      this.createWriter("fold3-training"),
    };
    
    this.testDirectories = new CasWriter[] {
      this.createWriter("fold1-test"),
      this.createWriter("fold2-test"),
      this.createWriter("fold3-test"),
    };
  }

  //////////////////////////////////////////////////////////////////////////////
  //                              FUNCTIONALITY                               //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  public void accept(final Article article) {
    final CAS cas = DataPreprocessor.toCas(article);
    for (final CasWriter writer : this.getTargetWriters(article)) {
      writer.accept(cas);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  //                                 HELPERS                                  //
  //////////////////////////////////////////////////////////////////////////////
  
  protected CasWriter createWriter(final String part) {
    return new CasWriter(
        new File(this.outputDirectory, "buzzfeed-" + part).toPath());
  }
  
  protected CasWriter[] getTargetWriters(final Article article) {
    final String portal = article.getPortal();
    if (FOLD1.contains(portal)) {
      return new CasWriter[] {
          this.testDirectories[0],
          this.trainingDirectories[1], this.trainingDirectories[2]
      };
    } else if (FOLD2.contains(portal)) {
      return new CasWriter[] {
          this.testDirectories[1],
          this.trainingDirectories[0], this.trainingDirectories[2]
      };
    } else if (FOLD3.contains(portal)) {
      return new CasWriter[] {
          this.testDirectories[2],
          this.trainingDirectories[0], this.trainingDirectories[1]
      };
    } else {
      throw new IllegalArgumentException("Unknown portal: " + portal);
    }
  }
  
  private static final Set<String> makeSet(final String... entries) {
    final Set<String> set = new HashSet<>(entries.length);
    for (final String entry : entries) {
      set.add(entry);
    }
    return Collections.unmodifiableSet(set);
  }

  //////////////////////////////////////////////////////////////////////////////
  //                              UIMA Conversion                             //
  //////////////////////////////////////////////////////////////////////////////
  
  public static class CasWriter implements Consumer<CAS> {
    
    private final Path outputFolder;
    
    private int count;
    
    public CasWriter(final Path outputFolder) {
      outputFolder.toFile().mkdirs();
      this.outputFolder = outputFolder;
      this.count = 0;
    }

    @Override
    public synchronized void accept(final CAS cas) {
      String name = String.format("%010d", this.count);
      try {
        final File outputFile = new File(outputFolder + "/" + name + ".xmi");
        System.out.println("Writing to " + outputFile);
        UIMAAnnotationFileWriter.write(cas, outputFile);
      } catch (final IOException e) {
        throw new UncheckedIOException(e);
      }
      this.count++;
    }
    
  }

  public static CAS toCas(final Article article) {
    final CAS cas = DataPreprocessor.createCas();

    try {
      final JCas jcas = cas.getJCas();
      jcas.setDocumentLanguage(Locale.ENGLISH.getCountry());

      final int textOffset =
          article.getTitle().length() + TITLE_SEPARATOR.length();
      jcas.setDocumentText(
          article.getTitle() + TITLE_SEPARATOR + article.getMainText());

      final ArticleMetaData meta = new ArticleMetaData(
          jcas, 0, jcas.getDocumentText().length());
      meta.setUri(article.getUri());
      meta.setAuthor(article.getAuthor());
      meta.setPortal(article.getPortal());
      meta.setOrientation(article.getOrientation());
      meta.setVeracity(article.getVeracity().toString());
      meta.setOffsetInSource(0);
      meta.setDocumentSize(jcas.getDocumentText().length());
      meta.setLastSegment(true); // There is only one segment per article!
      meta.addToIndexes();

      final Title title = new Title(jcas, 0, article.getTitle().length());
      title.addToIndexes();

      final MainText mainText = new MainText(
          jcas, textOffset, jcas.getDocumentText().length());
      mainText.addToIndexes();

      for (final Span paragraph : article.getParagraphs()) {
        paragraph.addAsParagraph(jcas);
      }

      for (final Span quote : article.getQuotes()) {
        quote.addAsQuote(jcas);
      }

      for (final Span link : article.getLinks()) {
        link.addAsLink(jcas);
      }
    } catch (final CASException e) {
      throw new IllegalStateException(e);
    }
    return cas;
  }

  private static CAS createCas() {
    try {
      return CasCreationUtils.createCas(
          DataPreprocessor.TYPE_SYSTEM, null, null);
    } catch (final ResourceInitializationException e) {
      throw new IllegalStateException(e);
    }
  }

  private static TypeSystemDescription createTypeSystem()  {
    final URL typeSystemUrl =
        DataPreprocessor.class.getResource(TYPE_SYSTEM_NAME);
    if (typeSystemUrl == null) {
      throw new IllegalStateException(
          "Could not load type system from resource: "
              + TYPE_SYSTEM_NAME);
    }
    try {
      return UIMAFramework.getXMLParser().parseTypeSystemDescription(
          new XMLInputSource(typeSystemUrl));
    } catch (final InvalidXMLException | IOException e) {
      throw new IllegalStateException(e);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  //                                     MAIN                                 //
  //////////////////////////////////////////////////////////////////////////////
  
  public static void main(final String[] args) throws IOException {
    final Path inputFolder = Paths.get(args[0]);
    final Path outputFolder = Paths.get(args[1]);
    
    final DataPreprocessor preprocessor =
        new DataPreprocessor(outputFolder.toFile());
    Article.readAll(inputFolder).forEach(preprocessor);
  }
  

}

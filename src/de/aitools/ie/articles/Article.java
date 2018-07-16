package de.aitools.ie.articles;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Article {

  //////////////////////////////////////////////////////////////////////////////
  //                                  MEMBERS                                 //
  //////////////////////////////////////////////////////////////////////////////

  private String title_;
  private String mainText_;
  private String uri_;
  private String author_;
  private String portal_;
  private String orientation_;
  private Veracity veracity_;
  private List<Span> paragraphs_;
  private List<Span> links_;
  private List<Span> quotes_;

  //////////////////////////////////////////////////////////////////////////////
  //                                CONSTRUCTORS                              //
  //////////////////////////////////////////////////////////////////////////////

  public Article() {
    this.title_ = null;
    this.mainText_ = null;
    this.uri_ = null;
    this.author_ = null;
    this.portal_ = null;
    this.orientation_ = null;
    this.veracity_ = null;
    this.paragraphs_ = new ArrayList<>();
    this.links_ = new ArrayList<>();
    this.quotes_ = new ArrayList<>();
  }

  public Article(String title, String mainText, String uri,
      List<Span> paragraphs) {
    this();
    this.setTitle(title);
    this.setMainText(mainText);
    this.setUri(uri);
    this.setParagraphs(paragraphs);
  }

  public Article(String title, String mainText, String uri,
      String author, String portal, String orientation, Veracity veracity,
      List<Span> paragraphs, List<Span> links, List<Span> quotes) {
    title_ = title;
    mainText_ = mainText;
    uri_ = uri;
    author_ = author;
    portal_ = portal;
    orientation_ = orientation;
    veracity_ = veracity;
    paragraphs_ = paragraphs;
    links_ = links;
    quotes_ = quotes;
  }

  public Article(String title, String mainText, String author,
      String portal, String orientation, List<Span> paragraphs, 
      List<Span> links, List<Span> quotes) {
    title_ = title;
    mainText_ = mainText;
    uri_ = null;
    author_ = author;
    portal_ = portal;
    orientation_ = orientation;
    veracity_ = null;
    paragraphs_ = paragraphs;
    links_ = links;
    quotes_ = quotes;
  }

  //////////////////////////////////////////////////////////////////////////////
  //                               GETTERS/SETTERS                            //
  //////////////////////////////////////////////////////////////////////////////

  @XmlElement(required = true)
  public String getTitle() {  
    return title_;
  }

  public void setTitle(final String title) {  
    title_ = title;
  }

  @XmlElement(required = true)
  public String getMainText() { 
    return mainText_;
  }

  public void setMainText(final String mainText) {  
    mainText_ = mainText;
  }

  @XmlElement(required = false, name = "paragraph")
  public List<Span> getParagraphs() { 
    return paragraphs_;
  }

  public void setParagraphs(List<Span> paragraphs) {
    paragraphs_ = paragraphs;
  }

  @XmlElement(required = false, name = "links")
  public List<Span> getLinks() { 
    return links_;
  }

  public void setLinks(List<Span> links) {
    links_ = links;
  }

  @XmlElement(required = false, name = "quotes")
  public List<Span> getQuotes() { 
    return quotes_;
  }

  public void setQuotes(List<Span> quotes) {
    quotes_ = quotes;
  }

  @XmlElement(required = true, name = "uri")
  public String getUri() { 
    return uri_;
  }

  public void setUri(String uri) {
    uri_ = uri;
  }

  @XmlElement(required = false, name = "author")
  public String getAuthor() { 
    return author_;
  }

  public void setAuthor(String author) {
    author_ = author;
  }

  @XmlElement(required = true, name = "portal")
  public String getPortal() { 
    return portal_;
  }

  public void setPortal(String portal) {
    portal_ = portal;
  }

  @XmlElement(required = true, name = "orientation")
  public String getOrientation() {
    return orientation_;
  }

  public void setOrientation(String orientation) {
    orientation_ = orientation;
  }

  @XmlElement(required = false, name = "veracity")
  public Veracity getVeracity() { 
    return veracity_;
  }

  public void setVeracity(Veracity veracity) {
    veracity_ = veracity;
  }

  //////////////////////////////////////////////////////////////////////////////
  //                                INPUT/OUTPUT                              //
  //////////////////////////////////////////////////////////////////////////////

  public static Article read(final Path filePath) {
    try {
      final JAXBContext context = JAXBContext.newInstance(Article.class);
      final Unmarshaller unmarshaller = context.createUnmarshaller();
      System.out.println("Reading from " + filePath);
      try (final InputStream input = new FileInputStream(filePath.toFile())) {
        return (Article) unmarshaller.unmarshal(input);
      }
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    } catch (final JAXBException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static Stream<Article> readAll(final Path folderPath)
  throws IOException {
    return Files.walk(folderPath)
      .filter(path -> path.getFileName().toString().endsWith(".xml"))
      .map(path -> Article.read(path));
  }

}

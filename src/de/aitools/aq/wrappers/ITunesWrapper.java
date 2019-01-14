package de.aitools.aq.wrappers;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.aitools.ie.articles.Article;
import de.aitools.ie.articles.Span;

public class ITunesWrapper extends Wrapper {

  @Override
  public Article parse(Document jsoupDocument) {
    /* Extract Title, Main text, Paragraph Structure,
     * Quotes, Links */
    String title = getTitle(jsoupDocument);
    String mainText = getMainText(jsoupDocument);
    String author = getAuthor(jsoupDocument);
    String portal = "ITunes";
    String orientation = "mainstream";
    List<Span> paragraphs = getParagraphs(jsoupDocument);
    List<Span> links = new ArrayList<Span>();
    List<Span> quotes = new ArrayList<Span>();
    
    /* Construct Article and return */
    Article article = new Article(title, mainText, author, portal,
        orientation, paragraphs, links, quotes);
    return article;
  }

   

  private String getAuthor(Document jsoupDocument) {
    return jsoupDocument.select("div.left h2").text();
  }


  private String getTitle(Document jsoupDocument) {
    return jsoupDocument.select("h1").text();
  }
  
  private String getMainText(Document jsoupDocument) {
    filter(jsoupDocument);
    return jsoupDocument.select("p").text();
  }  
  
  private List<Span> getParagraphs(Document jsoupDocument) {
    filter(jsoupDocument);
    List<Span> paragraphs = new ArrayList<Span>(); 
    Elements para = jsoupDocument.select("p");
    
    /* Create Span objects, calculate offset */
    int offset = 0;
    for (Element element : para) {
      int len = element.text().length();

      int start = offset;
      int end = offset + len;
      offset += len + 1;
      
      Span span = new Span(start, end);
      paragraphs.add(span);
    }
    return paragraphs;
  }

  private Document filter(Document jsoupDocument) {
    for(Element element : jsoupDocument.select("p.intro.has-preview-capable-text,"
        + "div.download"))
    {
      element.remove();
    }
    return jsoupDocument;
  }
  @Override
  public boolean isValidUri(String targetUri) {
    return targetUri.contains("https://itunes.apple.com/");
  }
}

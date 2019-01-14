package de.aitools.aq.wrappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.aitools.ie.articles.Article;
import de.aitools.ie.articles.Veracity;

public abstract class Wrapper {
    
    public abstract boolean isValidUri(final String targetUri);
    
    public Article parse(final String html, final String uri) {
      final Document jsoupDocument = Jsoup.parse(html);
      final Article article = this.parse(jsoupDocument, uri);
      article.setUri(uri);
      return article;
    }
    
    public Article parse(final String html, final String uri,
        final Veracity veracity) {
      final Article article = this.parse(html, uri);
      article.setVeracity(veracity);
      return article;
    }
  
    protected Article parse(final Document jsoupDocument, final String uri) {
      return this.parse(jsoupDocument);
    }
  
    protected abstract Article parse(final Document jsoupDocument);

}

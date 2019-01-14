package de.aitools.aq.wrappers;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.aitools.ie.articles.Article;
import de.aitools.ie.articles.Span;

public class CNNPodcastWrapper extends Wrapper {

	@Override
	public Article parse(Document jsoupDocument) {
		/* Extract Title, Main text, Paragraph Structure,
		 * Quotes, Links */
		String title = getTitle(jsoupDocument);
		String mainText = getMainText(jsoupDocument);
		String author = getAuthor(jsoupDocument);
		String portal = "CNN (Podcast)";
		String orientation = "mainstream";
		List<Span> paragraphs = getParagraphs(jsoupDocument);
		List<Span> links = getLinks(jsoupDocument);
		List<Span> quotes = new ArrayList<Span>();
		
		/* Construct Article and return */
		Article article = new Article(title, mainText, author, portal,
				orientation, paragraphs, links, quotes);
		return article;
	}

	 

	private String getAuthor(Document jsoupDocument) {
		return "CNN";
	}

	private String getTitle(Document jsoupDocument) {
		return jsoupDocument.select("headline a").first().text();
	}

	private String getMainText(Document jsoupDocument) {
		return jsoupDocument.select("p").text();
	}

	private List<Span> getParagraphs(Document jsoupDocument) {
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

	private List<Span> getLinks(Document jsoupDocument) {
		Elements paragraphs = jsoupDocument.select("p");
		String mainText = paragraphs.text();
		Elements a = paragraphs.select("a");
		List<Span> linkObjects = new ArrayList<Span>();
		
		/* Extract absolute links (href attribute in Span object) 
		 * and get index positions of Links in paragraphs.
		 * Create Span afterwards. */
		for (Element element : a) {
			String link = element.attr("abs:href");
			
			String word = element.text();
			int start = mainText.indexOf(word);
			int end = start + word.length();
			
			Span span = new Span(start, end, link);
			linkObjects.add(span);
		}
		return linkObjects;
	}
	@Override
	public boolean isValidUri(String targetUri) {
		return targetUri.contains("http://podcast.cnn.com/");
	}
}

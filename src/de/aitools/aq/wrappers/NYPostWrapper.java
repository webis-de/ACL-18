package de.aitools.aq.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.aitools.ie.articles.Article;
import de.aitools.ie.articles.Span;

public class NYPostWrapper extends Wrapper {

	@Override
	public Article parse(Document jsoupDocument) {
		/* Extract Title, Main text, Paragraph Structure,
		 * Quotes, Links */
		String title = getTitle(jsoupDocument);
		String mainText = getMainText(jsoupDocument);
		String author = getAuthor(jsoupDocument);
		String portal = "New York Post";
		String orientation = "mainstream";
		List<Span> paragraphs = getParagraphs(jsoupDocument);
		List<Span> links = getLinks(jsoupDocument);
		List<Span> quotes = getQuotes(jsoupDocument);
		
		/* Construct Article and return */
		Article article = new Article(title, mainText, author, portal,
				orientation, paragraphs, links, quotes);
		return article;
	}

	 

	private String getAuthor(Document jsoupDocument) {
		return jsoupDocument.select("div#author-byline p.byline a").text();
	}

	
	private String getTitle(Document jsoupDocument) {
		return jsoupDocument.select("div.article-header h1").text();
	}
	
	private String getMainText(Document jsoupDocument) {
		return jsoupDocument.select("div.entry-content.entry-content-read-more p").text();
	}

	private List<Span> getParagraphs(Document jsoupDocument) {
		List<Span> paragraphs = new ArrayList<Span>(); 
		Elements para = jsoupDocument.select("div.entry-content.entry-content-read-more p");
		
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
		Elements paragraphs = jsoupDocument.select("div.entry-content.entry-content-read-more p");
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

	private List<Span> getQuotes(Document jsoupDocument) {
		String mainText = jsoupDocument.select("div.entry-content.entry-content-read-more p").text();
		Elements quotes = jsoupDocument.select("blockquote");
		List<Span> quoteObjects = new ArrayList<Span>();
		
		/* Extract quotes */
		
		/* Get substrings between quotation marks */
		Pattern pattern2 = Pattern.compile("“(.*?)”");
		Matcher matcher2 = pattern2.matcher(mainText);
		
		/* As long as we find quotes, get their indices,
		 * create Span objects, put them into container */
		while(matcher2.find())
		{
			String word = matcher2.group(1);
			int start = mainText.indexOf(word);
			int end = start + word.length();
			
			Span span = new Span(start, end);
			quoteObjects.add(span);
		}		
		/* Get blockquotes */
		for(Element element : quotes) {
			String quote = element.text();
			int start = mainText.indexOf(quote);
			int end = start + quote.length();
			
			Span span = new Span(start, end);
			quoteObjects.add(span);
		}
		return quoteObjects;
	}
	@Override
	public boolean isValidUri(String targetUri) {
		return targetUri.contains("http://nypost.com/");
	}
}

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

public class ABCNewsWrapper extends Wrapper {
	
	public ABCNewsWrapper() { }

	@Override
	public Article parse(Document jsoupDocument) {
		/* Extract Title, Main text, Paragraph Structure,
		 * Quotes, Links */
		String title = getTitle(jsoupDocument);
		String mainText = getMainText(jsoupDocument);
		String author = getAuthor(jsoupDocument);
		String portal = "ABC News";
		String orientation = "mainstream";
		List<Span> paragraphs = getParagraphs(jsoupDocument);
		List<Span> links = getLinks(jsoupDocument);
		List<Span> quotes = getQuotes(jsoupDocument);
		
		/* Construct Article and return */
		Article article = new Article(title, mainText, author, portal,
				orientation, paragraphs, links, quotes);
		
		return article;
	}
	  
	public String getTitle(Document jsoupDocument)
	{
		Element e = filter(jsoupDocument);
		return e.select("h1").text();
	}
	
	public String getMainText(Document jsoupDocument)
	{
		Element e = filter(jsoupDocument);
		return e.select("p").text();
	}

	private String getAuthor(Document jsoupDocument) {
		Element e = filter(jsoupDocument);
		e.select("span.by-text").remove();
		return e.select("div.article-meta ul.authors li div.author,"
				+ "div.article-meta ul.authors li div.author.has-bio").text();
	}
	
	public List<Span> getParagraphs(Document jsoupDocument)
	{
		Element e = filter(jsoupDocument);
		List<Span> paragraphs = new ArrayList<Span>(); 
		Elements para = e.select("p");
		
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
	
	public List<Span> getLinks(Document jsoupDocument)
	{
		Element e = filter(jsoupDocument);
		String mainText = e.select("p").text();
		Elements paragraphs = e.select("p");
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
	
	public List<Span> getQuotes(Document jsoupDocument)
	{
		Element e = filter(jsoupDocument);
		String mainText = e.select("p").text();
		List<Span> quotes = new ArrayList<Span>();
		
		/* Extract quotes */
		
		/* Get substrings between quotation marks
		 * (Quotes on ABC pages) */
		Pattern pattern2 = Pattern.compile("\"(.*?)\"");
		Matcher matcher2 = pattern2.matcher(mainText);
		
		/* As long as we find quotes, get their indices,
		 * create Span objects, put them into container */
		while(matcher2.find())
		{
			String word = matcher2.group(1);
			int start = mainText.indexOf(word);
			int end = start + word.length();
			
			Span span = new Span(start, end);
			quotes.add(span);
		}		
		return quotes;
	}
	private Element filter(Document jsoupDocument) {
		Element tags = jsoupDocument.select("article").first();
		return tags;
	}

	@Override
	public boolean isValidUri(String targetUri) {
		return targetUri.contains("http://abcnews.go.com/");
	}
	
	// TODO: Comment extraction
}
package de.aitools.ie.articles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.uima.jcas.JCas;

import de.aitools.ie.uima.type.article.Link;
import de.aitools.ie.uima.type.article.MainText;
import de.aitools.ie.uima.type.article.Quote;
import de.aitools.ie.uima.type.core.Paragraph;

@XmlAccessorType(XmlAccessType.NONE)
public class Span {
	private int start_;
	private int end_;
	private String href_;
	
	public Span() { }
	
	public Span(int start, int end) { 
		start_ = start;
		end_ = end;
		href_ = "";
	}
	
	public Span(int start, int end, String href) { 
		start_ = start;
		end_ = end;
		href_ = href;
	}
	
	@XmlElement(required = true)
	public int getStart() {  
		return start_;
	}
	
	public void setStart(int start) {
		start_ = start;
	}
	
	@XmlElement(required = true)
	public int getEnd() {
		return end_;
	}
	
	public void setEnd(int end) {
		end_ = end;
	}
	
	@XmlAttribute(required = false, name="href")
	public String getHref() {
		return href_;
	}
	
	public void setHref(String href) {
		href_ = href;
	}
  
  //////////////////////////////////////////////////////////////////////////////
  //                              UIMA Conversion                             //
  //////////////////////////////////////////////////////////////////////////////
	
	public void addAsParagraph(final JCas jcas) {
	  final int textOffset = Span.getMainTextOffset(jcas);
    final Paragraph paragraph = new Paragraph(jcas,
        textOffset + this.getStart(), textOffset + this.getEnd());
    paragraph.addToIndexes();
	}
  
  public void addAsQuote(final JCas jcas) {
    final int textOffset = Span.getMainTextOffset(jcas);
    final Quote quote = new Quote(jcas,
        textOffset + this.getStart(), textOffset + this.getEnd());
    quote.addToIndexes();
  }
  
  public void addAsLink(final JCas jcas) {
    final int textOffset = Span.getMainTextOffset(jcas);
    final Link link = new Link(jcas,
        textOffset + this.getStart(), textOffset + this.getEnd());
    link.setHref(this.getHref());
    link.addToIndexes();
  }

  public static int getMainTextOffset(final JCas jcas) {
    return jcas.getAnnotationIndex(MainText.type).iterator().next().getBegin();
  }

}

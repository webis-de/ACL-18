package de.aitools.ie.articles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public enum FeatureSet {
  ALL {
    @Override
    public Collection<Pattern> patterns() {
      final List<Pattern> patterns = new ArrayList<>();
      patterns.addAll(FeatureSet.textStylePatterns());
      patterns.addAll(FeatureSet.hypertextStylePatterns());
      patterns.addAll(FeatureSet.topicPatterns());
      return patterns;
    }
  },
  TOPIC {
    @Override
    public Collection<Pattern> patterns() {
      return FeatureSet.topicPatterns();
    }
  },
  STYLE {
    @Override
    public Collection<Pattern> patterns() {
      final List<Pattern> patterns = new ArrayList<>();
      patterns.addAll(FeatureSet.textStylePatterns());
      patterns.addAll(FeatureSet.hypertextStylePatterns());
      return patterns;
    }
  },
  TEXT_STYLE {
    @Override
    public Collection<Pattern> patterns() {
      return FeatureSet.textStylePatterns();
    }
  },
  HYPERTEXT_STYLE {
    @Override
    public Collection<Pattern> patterns() {
      return FeatureSet.hypertextStylePatterns();
    }
  };
  
  public abstract Collection<Pattern> patterns();
  
  protected static Collection<Pattern> textStylePatterns() {
    final List<Pattern> patterns = new ArrayList<>();
    patterns.add(Pattern.compile("POS.Grams_.*"));
    patterns.add(Pattern.compile("C.G_.*"));
    patterns.add(Pattern.compile("Stopword.Grams_.*"));
    patterns.add(Pattern.compile("GeneralInquirer_.*"));
    patterns.add(Pattern.compile("Readability_.*"));
    return patterns;
  }
  
  protected static Collection<Pattern> hypertextStylePatterns() {
    final List<Pattern> patterns = new ArrayList<>();
    patterns.add(Pattern.compile("QuotationRatio_quotation_ratio"));
    patterns.add(Pattern.compile("LinkAmount_linkratio"));
    patterns.add(Pattern.compile("ParagraphLength_avgparagraphlength"));
    return patterns;
  }
  
  protected static Collection<Pattern> topicPatterns() {
    final List<Pattern> patterns = new ArrayList<>();
    patterns.add(Pattern.compile("Token.Grams_.*"));
    return patterns;
  }

}

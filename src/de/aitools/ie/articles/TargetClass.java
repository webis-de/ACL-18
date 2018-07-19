package de.aitools.ie.articles;

import java.util.HashMap;
import java.util.Map;

import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.JCas;

import de.aitools.ie.uima.type.article.ArticleMetaData;

public enum TargetClass {
  VERACITY {
    @Override
    public String getClassFeature() {
      return "veracity";
    }
    
    @Override
    public Map<String, String> getClassMapping() {
      return VERACITY_MAP;
    }
  },
  
  HYPERPARTISANSHIP {
    @Override
    public String getClassFeature() {
      return "orientation";
    }
    
    @Override
    public Map<String, String> getClassMapping() {
      return HYPERPARTISANSHIP_MAP;
    }
  },

  ORIENTATION {
    
    @Override
    public String getClassFeature() {
      return "orientation";
    }

    @Override
    public Map<String, String> getClassMapping() {
      return ORIENTATION_MAP;
    }
  };
  
  private static final Map<String, String> VERACITY_MAP =
      TargetClass.createVeracityClassMap();
  
  private static Map<String, String> createVeracityClassMap() {
    final Map<String, String> map = new HashMap<>();
    map.put("mostly false", "contains-false");
    map.put("mixture of true and false", "contains-false");
    map.put("mostly true", "mostly-true");
    return map;
  }
  
  private static final Map<String, String> HYPERPARTISANSHIP_MAP =
      TargetClass.createHyperpartisanshipClassMap();
  
  private static Map<String, String> createHyperpartisanshipClassMap() {
    final Map<String, String> map = new HashMap<>();
    map.put("left", "hyperpartisan");
    map.put("right", "hyperpartisan");
    map.put("mainstream", "mainstream");
    return map;
  }
  
  private static final Map<String, String> ORIENTATION_MAP =
      TargetClass.createOrientationClassMap();
  
  private static Map<String, String> createOrientationClassMap() {
    final Map<String, String> map = new HashMap<>();
    map.put("left", "left");
    map.put("right", "right");
    map.put("mainstream", "mainstream");
    return map;
  }
  
  public String getClassValue(final JCas jcas) {
    final ArticleMetaData metaData = (ArticleMetaData)
        jcas.getAnnotationIndex(ArticleMetaData.type).iterator().next();
    final String classFeature = this.getClassFeature();
    for (final Feature feature : metaData.getType().getFeatures()) {
      if (feature.getShortName().equals(classFeature)) {
        final String value = metaData.getFeatureValueAsString(feature);
        return this.getClassMapping().get(value);
      }
    }
    throw new IllegalStateException();
  }
  
  public abstract String getClassFeature();
  
  public abstract Map<String, String> getClassMapping();

}

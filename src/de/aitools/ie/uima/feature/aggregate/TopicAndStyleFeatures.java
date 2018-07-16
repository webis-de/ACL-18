package de.aitools.ie.uima.feature.aggregate;

import java.util.List;

import de.aitools.ie.uima.feature.IFeatureType;

public class TopicAndStyleFeatures extends GenericAggregateFeature {

  @Override
  protected void addFeatureTypes(
      final List<IFeatureType> featureTypes) {
    featureTypes.add(new TopicFeatures());
    featureTypes.add(new StyleFeatures());
  }

}

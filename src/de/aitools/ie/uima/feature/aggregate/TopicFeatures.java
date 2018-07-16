package de.aitools.ie.uima.feature.aggregate;

import java.util.List;

import de.aitools.ie.uima.feature.IFeatureType;
import de.aitools.ie.uima.feature.content.Token1Grams;

public class TopicFeatures extends GenericAggregateFeature {

  @Override
  protected void addFeatureTypes(final List<IFeatureType> featureTypes) {
    featureTypes.add(new Token1Grams());
  }

}

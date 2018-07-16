package de.aitools.ie.uima.feature.aggregate;

import java.util.List;

import de.aitools.ie.uima.feature.IFeatureType;
import de.aitools.ie.uima.feature.length.AverageParagraphLength;
import de.aitools.ie.uima.feature.length.LinkAmount;
import de.aitools.ie.uima.feature.length.QuotationRatio;

public class DomainSpecificFeatures extends GenericAggregateFeature {

  @Override
  protected void addFeatureTypes(final List<IFeatureType> featureTypes) {
    featureTypes.add(new QuotationRatio());
    featureTypes.add(new LinkAmount());
    featureTypes.add(new AverageParagraphLength());
  }

}

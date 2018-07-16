package de.aitools.ie.uima.feature.aggregate;

import java.util.List;

import de.aitools.ie.uima.feature.IFeatureType;
import de.aitools.ie.uima.feature.length.AverageParagraphLength;
import de.aitools.ie.uima.feature.length.LinkAmount;
import de.aitools.ie.uima.feature.length.QuotationRatio;
import de.aitools.ie.uima.feature.style.Char1Grams;
import de.aitools.ie.uima.feature.style.Char2Grams;
import de.aitools.ie.uima.feature.style.Char3Grams;
import de.aitools.ie.uima.feature.style.GeneralInquirerCategories;
import de.aitools.ie.uima.feature.style.POS1Grams;
import de.aitools.ie.uima.feature.style.POS2Grams;
import de.aitools.ie.uima.feature.style.POS3Grams;
import de.aitools.ie.uima.feature.style.ReadabilityScores;
import de.aitools.ie.uima.feature.style.Stopword1Grams;
import de.aitools.ie.uima.feature.style.Stopword2Grams;
import de.aitools.ie.uima.feature.style.Stopword3Grams;

public class StyleFeatures extends GenericAggregateFeature {

  @Override
  protected void addFeatureTypes(final List<IFeatureType> featureTypes) {
    featureTypes.add(new POS1Grams());
    featureTypes.add(new POS2Grams());
    featureTypes.add(new POS3Grams());
    featureTypes.add(new Char1Grams());
    featureTypes.add(new Char2Grams());
    featureTypes.add(new Char3Grams());
    featureTypes.add(new Stopword1Grams());
    featureTypes.add(new Stopword2Grams());
    featureTypes.add(new Stopword3Grams());
    featureTypes.add(new GeneralInquirerCategories());
    featureTypes.add(new ReadabilityScores());
    featureTypes.add(new QuotationRatio());
    featureTypes.add(new LinkAmount());
    featureTypes.add(new AverageParagraphLength());
  }

}

package de.aitools.ie.uima.feature.aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.uima.jcas.JCas;

import de.aitools.ie.uima.feature.IFeatureType;

public abstract class GenericAggregateFeature implements IFeatureType {
  
  private final List<IFeatureType> featureTypes;
  
  public GenericAggregateFeature() {
    this.featureTypes = new ArrayList<>();
  }
  
  protected abstract void addFeatureTypes(
      final List<IFeatureType> featureTypes);
  
  @Override
  public void initializeFeatureDetermination(
      final Properties configurationProps) {
    this.featureTypes.clear();
    this.addFeatureTypes(this.featureTypes);
    for (final IFeatureType featureType : this.featureTypes) {
      featureType.initializeFeatureDetermination(configurationProps);
    }
  }
  
  @Override
  public void updateCandidateFeatures(
      final JCas jcas, final int start, final int end) {
    for (final IFeatureType featureType : this.featureTypes) {
      featureType.updateCandidateFeatures(jcas, start, end);
    }
  }

  @Override
  public List<String> determineFeatures(
      final Properties configurationProps,
      final Properties normalizationProps) {
    final List<String> featureNames = new ArrayList<String>();
    for (final IFeatureType featureType : this.featureTypes) {
      featureNames.addAll(featureType.determineFeatures(
          configurationProps, normalizationProps));
    }
    return featureNames;
  }

  @Override
  public void initializeFeatureComputation(
      final List<String> allFeatureNames,
      final Properties configurationProps,
      final Properties normalizationProps) {
    this.featureTypes.clear();
    this.addFeatureTypes(this.featureTypes);
    for (final IFeatureType featureType : this.featureTypes) {
      featureType.initializeFeatureComputation(
          allFeatureNames, configurationProps, normalizationProps);
    }
  }

  @Override
  public List<Double> computeNormalizedFeatureValues(
      final JCas jcas, final int start, final int end) {
    List<Double> values = new ArrayList<Double>();
    for (final IFeatureType featureType : this.featureTypes) {
      values.addAll(featureType.computeNormalizedFeatureValues(
          jcas, start, end));
    }
    return values;
  }

}

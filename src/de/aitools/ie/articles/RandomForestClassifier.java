package de.aitools.ie.articles;

import java.io.File;
import java.util.Collection;
import java.util.regex.Pattern;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class RandomForestClassifier {
  
  // -------------------------------------------------------------------------
  // MEMBERS
  // -------------------------------------------------------------------------
  
  /**
   * The classifier provided in the constructor.
   */
  private final Classifier classifier;
  
  /**
   * Patterns of names of attributes to be kept from training and test set. 
   */
  private final Collection<Pattern> attributePatterns;
  
  /**
   * The instances read from the feature file provided in the constructor.
   */
  private final Instances training;
  
  // -------------------------------------------------------------------------
  // CONSTRUCTOR
  // -------------------------------------------------------------------------

  public RandomForestClassifier(
      final FeatureSet featureSet, final File trainingSetFeatureFile)
  throws Exception {
    this.attributePatterns = featureSet.patterns();
    this.classifier = new RandomForest();
    this.training = this.loadInstances(trainingSetFeatureFile);
    this.classifier.buildClassifier(this.training);
  }
  
  // -------------------------------------------------------------------------
  // FUNCTIONALITY
  // -------------------------------------------------------------------------
  
  public Evaluation evaluate(final File testSetFeatureFile)
  throws Exception {
    final Instances test = this.loadInstances(testSetFeatureFile);
    final Evaluation evaluation = new Evaluation(this.training);
    evaluation.evaluateModel(this.classifier, test);
    System.out.println();
    System.out.println("=== Summary ===");
    System.out.println(evaluation.toSummaryString());
    System.out.println();
    System.out.println(evaluation.toClassDetailsString());
    System.out.println();
    System.out.println(evaluation.toMatrixString());
    return evaluation;
  }

  
  // -------------------------------------------------------------------------
  // HELPERS
  // -------------------------------------------------------------------------
  
  protected Instances loadInstances(final File file) throws Exception {
    DataSource source = new DataSource(file.getAbsolutePath());
    Instances instances = source.getDataSet();
    if (instances.classIndex() == -1) {
      instances.setClassIndex(instances.numAttributes() - 1);
    }
    
    attributeloop : for (int a = instances.numAttributes() - 1; a >= 0; --a) {
      if (a == instances.classIndex()) { continue; }

      String attributeName = instances.attribute(a).name();
      for (Pattern pattern : this.attributePatterns) {
        if (pattern.matcher(attributeName).matches()) {
          continue attributeloop;
        }
      }
      // did not continue attribute loop
      instances.deleteAttributeAt(a);
    }
    
    return instances;
  }
  
  // -------------------------------------------------------------------------
  // MAIN
  // -------------------------------------------------------------------------
  
  public static void main(final String[] args) throws Exception {
    final FeatureSet featureSet = FeatureSet.valueOf(args[0]);
    final File trainingSetFeatureFile = new File(args[1]);
    final File testSetFeatureFile = new File(args[2]);
    
    final RandomForestClassifier classifier =
        new RandomForestClassifier(featureSet, trainingSetFeatureFile);
    classifier.evaluate(testSetFeatureFile);
  }
  
}

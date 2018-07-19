A Stylometric Inquiry into Hyperpartisan And Fake News
======================================================

This repository contains the code for reproducing results of the paper:

Martin Potthast, Johannes Kiesel, Kevin Reinartz, Janek Bevendorff, and Benno Stein. <a href="http://www.uni-weimar.de/medien/webis/publications/papers/stein_2018i.pdf" class="paper"><span class="title">A Stylometric Inquiry into Hyperpartisan and Fake News</span></a>. In <span class="booktitle">Proceedings of 56th Annual Meeting of the Association for Computational Linguistics (ACL 18)</span>, <span class="month">July</span> <span class="year">2018</span> 


Resources
---------
  - Download the <a href="https://doi.org/10.5281/zenodo.1181813" target="_blank">dataset</a>, place it under <code>data</code>, and extract it there.
  - Get the required libraries, <code>aitools4-ie-uima.jar</code> and <code>jsoup-1.6.1.jar</code>, from the <a href="https://github.com/webis-de/ACL-18/releases/tag/1.0.0" target="_blank">resources page</a> and place them under <code>lib</code>.


Building
--------
Just use <code>ant</code> in this directory. This will create a single <code>acl18-bundle.jar</code> JAR file that contains everything you need.


Classification experiments
--------------------------
Split the data into three folds (by portal/publisher) and convert to UIMA XMI.
  
    java -cp acl18-bundle.jar de.aitools.ie.articles.DataPreprocessor data/articles data/xmi

Then extract the features using UIMA and generate WEKA ARFF files for each task.
Note that this extracts all features. The actually used feature set is specified in the next step.

    java -cp acl18-bundle.jar de.aitools.ie.articles.FeatureExtractor VERACITY data/xmi data/veracity
    java -cp acl18-bundle.jar de.aitools.ie.articles.FeatureExtractor ORIENTATION data/xmi data/orientation
    java -cp acl18-bundle.jar de.aitools.ie.articles.FeatureExtractor HYPERPARTISANSHIP data/xmi data/hyperpartisanship

You can then train and test the classifier. Available feature sets are: TOPIC, TEXT\_STYLE, HYPERTEXT\_STYLE, STYLE (= TEXT\_STYLE + HYPERTEXT\_STYLE), ALL (= TOPIC + STYLE). The following command will build the TOPIC classifier for VERACITY on the first fold training set and evaluate it on the first fold test set.

    java -cp acl18-bundle.jar de.aitools.ie.articles.RandomForestClassifier TOPIC data/veracity/*-fold1-training.arff data/veracity/*-fold1-test.arff 



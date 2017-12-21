package com.param.utils

object MllibFunctionNames extends Enumeration {
  
  
  
  
  val UNARY_OPERATOR = "unary_operator"
  
  val BINARY_OPERATOR = "binary_operator"
  
  
  
  // 聚类函数
  val CLUSTER = "cluster"
  val K_MEANS = "kmeanscluster"
  val GMM = "gmmcluster"
  
  val BKM = "bisectingKMeans"
  
  // 分类函数  Classification
  val CLASSIFICATION = "Classification"
  val RANDOMFOREST_CLASSIFICATION = "randomforestclass"
  val DECISIONTREE_CLASSIFICATION = "decisiontreeClass"
  val NAIVEBAYES = "naivebayes"
  val GBTS_CLASSIFICATION = "gbtsclass"
  val LR_CLASSIFICATION = "lrclass"
  val SVM_WITH_SGD = "svm"
  
  val MLP = "mlp"

  // 回归函数
  val REGRESSION = "regression"
  val LINE_REGRESSION = "lineregression"
  val RANDOMFOREST_REGRESSION = "randomforestregression"
  val DECISIONTREE_REGRESSION= "decisiontreeregression"
  val GBTS_REGRESSION = "gbtsregression"
  val ISOTONIC_REGRESSION = "isotonicregression"
  val GENERAL_LINE_REGRESSION = "generalizedLinearRegression"
  // 模型预测
  val PREDICT_MODEL = "predict"
  val PREDICT_MODEL_WITH_LABEL = "predictwithlabel"
  
  // 保存模型
  val PERSIST_MODEL = "persist"
  
  // 读取模型
  val READ_MODEL = "readmodel"
  
  // 模型评估
  val PERFORMANCE = "performance"
  val PERFORMANCE_CLUSTER_KMEANS = "performanceclusterkmeans"
  val PERFORMANCE_REARESSION = "performanceregression"
  val PERFORMANCE_ISOTONIC = "performanceisotonic"
  val PERFORMANCE_BINARY_CLASSIFICATION = "performancebinaryclassification"
  val PERFORMANCE_MULTICLASS_CLASSIFICATION = "performancemulticlassification"
  
  
  //模型名称
  val KMeansModel = "kmm"
  val GaussianMixtureModel = "gmm"
  val RandomForestModel = "rfm"
  val DecisionTreeModel = "dtm"
  val NaiveBayesModel = "nbm"
  val GradientBoostedTreesModel = "gbtm"
  val LogisticRegressionModel = "lrm"
  val IsotonicRegressionModel = "irm"
  
  
  //特征选择
  
  
  val CHI_SQ_SELECTOR="ChiSqSelector"
  
  val RF_SELECTOR="rfselector"
  
  
  //特征提取
  val ATTRIBUTION_SELECTION_MODEL = "attributionselectionmodel"
  val SVD = "svd"
  val PCA = "pca"
  
  //Text 分词
  val TEXT = "text"
  val DOCUMENT_TOKENIZER = "documenttokenizer"
  val TFIDF = "tfidf"
  val LDA = "lda"
  
  //Associate model(关联模型)
  val ASSOCIATE_MODEL = "associatemodel"
  val FP_GROWTH = "fpgrowth"
  
  
  //Transfer
  val TRANSFER = "transfer"
  
  
}
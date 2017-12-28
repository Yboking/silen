package com.param.utils
/**
 * 全局常量
 */
object Constants {

  val ETL_KEYVALUE_DELIMITER = "="

  //参数值分隔符  eg :fields=age,name
  val ARGS_DELIMITER = ","

  //条件过滤中表示用户自定义操作类型,eg: 均值，分位数
  val ETL_FILTER_UDO = "is"

  //均值函数
  val ETL_BUILDIN_FUNC_AVG = "avg"

  val ETL_MULTI_GROUP_TAG = "group_tag"

  val ADD = "add"

  val SUBTRACT = "subtract"

  val DIVIDE = "divide"

  val MULTIPY = "multipy"

  val MOD = "mod"

  val POW = "pow"

  val NTHROOT = "nthroot"

  val MEDIA = "median"

  val BIGGER = ">"
  val BIGGER_EQ = ">="
  val SMALLER = "<"
  val SMALLER_EQ = "<="

  val EQUAL = "=="

  val NOT_EQUAL = "!="

  val QUANTILE = "quantile"

  val AGG_FUNCTIONS = "max,min,mean,avg,geometricAvg,harmonicAvg,stddev,variance,var_pop"

  val GEO_AVG = "geoavg"

  val HARM_AVG = "harmavg"

  val MAP_FUNCTIONS = "round,ceil,floor,abs,log,sin,asin,cos,acos,tan,atan,ctan"

  val FIELD_DELIMITER = ","

  val FIELD_OPTION_DELIMITER = ";"
  object OutputDir {

    val FEATURE_SELECT_FIELDS = "importantFields"

    val FEATURE_SELECT_DATA = "transformed"

    val MODEL_SAVE_DIR = "model"

    val MODEL_META_SAVE_DIR = "transformed/modelattr/"
    val MODEL_META_SAVE_FILENAME = "attr.json"

    val MODEL_TRANSFORMED_DATA_DIR = "transformed/data"

    val OUTPUT_FILENAME = "part-m-00000"

  }

  object MLModelName {

    val SVM = "SVM"

    val PIPELINE_MODEL = "PipelineModel"
  }

  object ETLARGS {

    val DEL_NONE_VAL_ROW = Array("usedFields", "noneValues")
    val DEL_NONE_VAL_ROW_DEFAULT = Array(null, "null,NaN,NAN,NA,\\s,nullValue")

    val FILL_NONE_VAL = Array("usedFields", "noneValues", "fillValue")
    val FILL_NONE_VAL_DEFAULT = Array(null, "null,NaN,NAN,NA,\\s", null)

    val BINARY_SPLIT = Array("splitType", "splitValue", "needRandom", "reIndex", "outputpath1", "outputpath2")
    val BINARY_SPLIT_DEFAULT = Array(null, null, "false", "false", null, null)

    val FIELDEXTRACT = Array("taskType","class","function", "inputpath", "fieldOption", "outputpath")
    val FIELDEXTRACT_DEFAULT = Array(null, null, null, "", null, null)

    val REMOVE_DUPLICATED = Array("function", "inputpath", "usedFields", "reIndex", "outputpath")
    val REMOVE_DUPLICATED_DEFAULT = Array(null, null, null, "false", null)

    val FILL_FUNCTIONS = Array("mean", "max", "min", "stddev", "variance")

    val COLUMNS_CUT = Array("function", "inputpath", "usedFields", "startIndex", "endIndex", "isReverseSelect", "outputpath")
    val COLUMNS_CUT_DEFAULT = Array(null, null, null, "0", "0", "false", null)
  }
  /**
   *
   * 算子参数设置
   */
  object MLARGS {

    val INPUT = Array("taskType", "class", "function", "inputpath", "delimiter", "header", "inferSchema")
    val INPUT_DEFAULT = Array(null, null, null, null, ",", "true", "false")

    val OUTPUT = Array("outputpath")
    val OUTPUT_DEFAULT = Array(null)

    val SAVE_CSV = Array("filename", "delimiter2", "header2", "outputpath")
    val SAVE_CSV_DEFAULT = Array(null, ",", "true", null)

    val PREDICT_OUTPUT = Array("labelOnly", "outputpath")
    val PREDICT_OUTPUT_DEFAULT = Array("false", null)

    val KMEANS = Array[String]("function", "inputpath", "usedFields", "k", "maxiterations", "outputpath")
    val KMEANS_DEFAULT = Array[String](null, null, null, "1", "5", null)

    val BisectingKMeans = KMEANS

    val GMM = KMEANS

    val RANDOMFOREST_CLASSIFICATION = Array[String]("usedFields", "labelCol", "numTrees", "maxDepth", "minInstancesPerNode", "featureSubsetStrategy", "impurity", "maxBins", "predictedCol", "outputpath")
    val RANDOMFOREST_CLASSIFICATION_ARGS_VALUE = Array[String](null, "label", "100", "5", "2", "sqrt", "gini", "32", "predictedLabel", null)

    val NAIVEBAYES = Array("usedFields", "labelCol", "smoothing", "predictionCol")
    val NAIVEBAYES_DEFAULT = Array(null, "label", "1.0", "predictedLabel")

    val GBTS_CLASSIFICATION = Array("usedFields", "labelCol", "impurity", "maxBins", "maxDepth", "maxIter", "predictedCol")
    val GBTS_CLASSIFICATION_DEFAULT = Array(null, "label", "gini", "32", "5", "52", "predictedLabel")

    val DECISION_TREE = Array("usedFields", "labelCol", "maxDepth", "minInstancesPerNode", "impurity", "maxBins", "predictedCol")
    val DECISION_TREE_DEFAULT = Array(null, "label", "5", "2", "gini", "32", "predictedLabel")

    val SVM_WITH_SGD = Array("usedFields", "labelCol", "maxIter", "regParm", "threshold", "predictedCol")
    val SVM_WITH_SGD_DEFAULT = Array(null, "label", "32", "0.01", "0.0", "predictedLabel")

    val MLP = Array("usedFields", "labelCol", "maxIter", "nodesHide", "predictedCol")
    val MLP_DEFAULT = Array(null, "label", "32", "6", "predictedLabel")

    val LR_CLASSIFY = Array("usedFields", "labelCol", "threshold", "elasticNetParam", "regParam", "predictedCol")
    val LR_CLASSIFY_DEFAULT = Array(null, "label", "0.5", "1.0", "1.0", "predictedLabel")

    val LINE_REGRESION = Array("usedFields", "labelCol", "maxIter", "regularizers", "regParam", "predictedCol")
    val LINE_REGRESION_DEFAULT = Array(null, "label", "32", "1.0", "1.0", "predictedLabel")

    val RANDOMFOREST_REGRESSION = Array[String]("usedFields", "labelCol", "numTrees", "maxDepth", "minInstancesPerNode", "featureSubsetStrategy", "maxBins", "predictedCol")
    val RANDOMFOREST_REGRESSION_DEFAULT = Array[String](null, "label", "100", "5", "2", "sqrt", "32", "predictedLabel")

    val DECISIONTREE_REGRESSION = Array("usedFields", "labelCol", "maxDepth", "minInstancesPerNode", "maxBins", "predictedCol")
    val DECISIONTREE_REGRESSION_DEFAULT = Array(null, "label", "5", "2", "32", "predictedLabel")

    val GBTS_REGRESSION = Array("usedFields", "labelCol", "maxBins", "maxDepth", "lossFunction", "maxIter", "predictedCol")
    val GBTS_REGRESSION_DEFAULT = Array(null, "label", "32", "30", "squared", "32", "predictedLabel")

    val ISOTONIC_REGRESSION = Array("usedFields", "labelCol", "isotonic", "predictedCol")
    val ISOTONIC_REGRESSION_DEFAULT = Array(null, "label", "true", "predictedLabel")

    val GENERAL_LINE_REGRESSION = Array("usedFields", "labelCol", "disFunctionFamily", "likeFunction", "maxIter", "regParam", "predictedCol")
    val GENERAL_LINE_REGRESSION_DEFAULT = Array(null, "label", "gaussian", "identity", "32", "1.0", "predictedLabel")

    val CHI_SQ_SELECTOR = Array("usedFields", "labelCol", "selectorType", "selectorThreshold", "numTopFeatures", "selectedCol")
    val CHI_SQ_SELECTOR_DEFAULT = Array(null, "label", "selectorType", "selectorThreshold", "0.5", "selectedFeatures")

    // args for prediction 

    val PREDICT_MODEL = Array[String]("function", "usedFields", "inputpathmodel", "inputpathdata", "delimiter", "header", "inferSchema")
    val PREDICT_MODEL_DEFAULT = Array[String](null, null, null, null, ",", "true", "false")

    val PERFORMANCE_EVALUATE = Array("function", "modelname", "usedFields", "inputpath", "delimiter", "header", "inferSchema", "modelInput", "outputpath")
    val PERFORMANCE_EVALUATE_DEFAULT = Array(null, null, null, null, ",", "true", "false", null, null)

    val PCA = Array("usedFields", "k", "outputpath")
    val PCA_DEFAULT = Array(null, "2", null)

    val RANDOMFOREST_SELECTOR = Array[String]("usedFields", "labelCol", "numTrees", "maxDepth", "minInstancesPerNode", "featureSubsetStrategy", "impurity", "maxBins", "importanceThreshold")
    val RANDOMFOREST_SELECTOR_DEFAULT = Array[String](null, "label", "100", "5", "2", "sqrt", "gini", "32", "0.3")

    val ASSOCIATE_MODEL = Array[String]("function", "inputpath", "outputpath", "delimiter", "minsupport", "minconfidence", "modelname")

    val PERSIST_MODEL = Array[String]("function", "inputpath", "outputpath", "modelname")

    val PREDICT_MODEL_WITH_LABEL = Array[String]("function", "inputpathmodel", "inputpathdata", "outputpath", "delimiter", "modelname", "labelpoint")

    val READ_MODEL = Array[String]("function", "inputpath", "outputpath", "modelname")

  }

}
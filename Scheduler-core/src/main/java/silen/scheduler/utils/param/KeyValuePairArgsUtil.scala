package silen.scheduler.utils.param

import java.util.HashMap

import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory

/**
 * key-value型键值对处理对象
 *
 */
object KeyValuePairArgsUtil {

  val logger = LoggerFactory.getLogger(KeyValuePairArgsUtil.getClass.getName)

  /**
   * 提取参数中的value值
   */
  def extractValueArgs(keyValueArgs: Array[String]): Array[String] = {
    val keyAndValueMap = new HashMap[String, String]();
    logger.info("----------------------所有参数-------------------------")
    keyValueArgs.foreach {
      x => logger.info("arg => " + x)
    }///////////////????

    //keyAndValueMap.put(arg.split("=")(0).toLowerCase(), arg.split("=")(1))
    keyValueArgs.foreach { arg =>
      val split = arg.split(Constants.ETL_KEYVALUE_DELIMITER);
      if (StringUtils.countMatches(arg, Constants.ETL_KEYVALUE_DELIMITER) > 1) {
        val index = arg.indexOf(Constants.ETL_KEYVALUE_DELIMITER);
        keyAndValueMap.put(split(0).toLowerCase(), arg.substring(index + 1, arg.length()));
      } else {
        if (split.length == 1)
          keyAndValueMap.put(split(0).toLowerCase(), "")
        else
          keyAndValueMap.put(split(0).toLowerCase(), split(1))
      }
    }
    val functionName = keyAndValueMap.get("function")

    //    var valueArgs = new ArrayBuffer[String](keyValueArgs.length);

    var argNames: Array[String] = null
    var argValuesParsed: Array[String] = null
    functionName match {

      /****************************以下是机器学习算法参数处理部分*******************************/

      case MllibFunctionNames.UNARY_OPERATOR => {

        argNames = Array[String]("function", "inputpath", "operator", "usedFields", "overwrite", "outputpath")

        val argValues = Array(null, null, null, null, "false", null)
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.BINARY_OPERATOR => {

        argNames = Array[String]("function", "inputpath", "operator", "operands", "resultCol", "outputpath")
        val argValues = Array(null, null, null, null, "resultColumn", null)
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.K_MEANS => {
        ArgsUtil.getArgsValue(keyAndValueMap, Constants.MLARGS.KMEANS, Constants.MLARGS.KMEANS_DEFAULT)
      }

      case MllibFunctionNames.BKM => {
        argNames = Constants.MLARGS.BisectingKMeans
        ArgsUtil.getArgsValue(keyAndValueMap, Constants.MLARGS.BisectingKMeans, Constants.MLARGS.KMEANS_DEFAULT)

      }
      case MllibFunctionNames.GMM => {
        argNames = Constants.MLARGS.GMM
        ArgsUtil.getArgsValue(keyAndValueMap, Constants.MLARGS.GMM, Constants.MLARGS.KMEANS_DEFAULT)
      }

      case MllibFunctionNames.PCA => {

        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.PCA)
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.PCA_DEFAULT)

        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

        //        if (keyAndValueMap.get("modlename").toLowerCase().equals("pca")) {
        //          ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "k", "modlename"))
        //        } else {
        //          ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "k", "rcond", "modlename"))
        //        }
      }

      case MllibFunctionNames.CHI_SQ_SELECTOR => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.CHI_SQ_SELECTOR) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.CHI_SQ_SELECTOR_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.RF_SELECTOR => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.RANDOMFOREST_SELECTOR) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.RANDOMFOREST_SELECTOR_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.RANDOMFOREST_CLASSIFICATION => {

        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.RANDOMFOREST_CLASSIFICATION)
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.RANDOMFOREST_CLASSIFICATION_ARGS_VALUE)

        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.DECISIONTREE_CLASSIFICATION => {

        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.DECISION_TREE) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.DECISION_TREE_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT

        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

      }

      case MllibFunctionNames.NAIVEBAYES => {

        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.NAIVEBAYES) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.NAIVEBAYES_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT

        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

      }

      case MllibFunctionNames.GBTS_CLASSIFICATION => {

        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.GBTS_CLASSIFICATION) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.GBTS_CLASSIFICATION_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT

        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.SVM_WITH_SGD => {

        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.SVM_WITH_SGD) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.SVM_WITH_SGD_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT

        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

      }

      case MllibFunctionNames.MLP => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.MLP) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.MLP_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.LR_CLASSIFICATION => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.LR_CLASSIFY) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.LR_CLASSIFY_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.LINE_REGRESSION => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.LINE_REGRESION) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.LINE_REGRESION_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.RANDOMFOREST_REGRESSION => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.RANDOMFOREST_REGRESSION) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.RANDOMFOREST_REGRESSION_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.DECISIONTREE_REGRESSION => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.DECISIONTREE_REGRESSION) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.DECISIONTREE_REGRESSION_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.GBTS_REGRESSION => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.GBTS_REGRESSION) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.GBTS_REGRESSION_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.ISOTONIC_REGRESSION => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.ISOTONIC_REGRESSION) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.ISOTONIC_REGRESSION_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.GENERAL_LINE_REGRESSION => {
        val argNames = Constants.MLARGS.INPUT ++ (Constants.MLARGS.GENERAL_LINE_REGRESSION) ++ Constants.MLARGS.OUTPUT
        val argValues = Constants.MLARGS.INPUT_DEFAULT ++ (Constants.MLARGS.GENERAL_LINE_REGRESSION_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.ASSOCIATE_MODEL => {
        ArgsUtil.getArgsValue(keyAndValueMap, Constants.MLARGS.ASSOCIATE_MODEL)
      }

      case MllibFunctionNames.TEXT => {
        //        if(keyValueArgs.length==5){
        if (keyAndValueMap.get("modelname").toLowerCase().equals("tfidf")) {
          logger.info("----------------------tfidf内部所有参数-------------------------")

          for (key <- keyAndValueMap.keySet().toArray) {
            println(key + ":" + keyAndValueMap.get(key))
          }

          //keyAndValueMap.foreach(e => println(e._1 +":"+e._2)
          ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "modelname"))
        } else {
          ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "k", "maxiterations", "docconcentration",
            "topicconcentration", "checkpointinterval", "modelname"))
        }

      }

      case MllibFunctionNames.PERFORMANCE => {

        val argNames = Constants.MLARGS.PERFORMANCE_EVALUATE
        val argValues = Constants.MLARGS.PERFORMANCE_EVALUATE_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

        //        if (keyAndValueMap.get("modelname").toLowerCase().equals("performanceclusterkmeans")) {
        //          ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpathmodel", "inputpathdata", "outputpath", "delimiter", "modelname"))
        //        } 
      }

      case MllibFunctionNames.PERSIST_MODEL => {

        ArgsUtil.getArgsValue(keyAndValueMap, Constants.MLARGS.PERSIST_MODEL)
      }

      case MllibFunctionNames.PREDICT_MODEL => {
        val argNames = (Constants.MLARGS.PREDICT_MODEL) ++ Constants.MLARGS.PREDICT_OUTPUT
        val argValues = (Constants.MLARGS.PREDICT_MODEL_DEFAULT) ++ Constants.MLARGS.PREDICT_OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case MllibFunctionNames.PREDICT_MODEL_WITH_LABEL => {
        ArgsUtil.getArgsValue(keyAndValueMap, Constants.MLARGS.PREDICT_MODEL_WITH_LABEL)
      }

      case MllibFunctionNames.READ_MODEL => {
        ArgsUtil.getArgsValue(keyAndValueMap, Constants.MLARGS.READ_MODEL)
      }

      /************************************以下是ETL算法参数处理部分***************************************/

      case EtlFunctionsName.ETL_READ_CSV => {
        val argNames = (Constants.MLARGS.INPUT) ++ Constants.MLARGS.OUTPUT
        val argValues = (Constants.MLARGS.INPUT_DEFAULT) ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

      }

      case EtlFunctionsName.ETL_WRITE_CSV => {

        val argNames = (Constants.MLARGS.INPUT) ++ Constants.MLARGS.SAVE_CSV
        val argValues = (Constants.MLARGS.INPUT_DEFAULT) ++ Constants.MLARGS.SAVE_CSV_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

      }

      case EtlFunctionsName.ETL_RANDOMSAMPLING => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "sampleType", "sampleSize", "reIndex", "outputpath"))

      }

      case EtlFunctionsName.ETL_STRATIFY_SAMPLE => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "fieldName", "sampleType", "sampleSize", "reIndex", "outputpath"))
      }

      case EtlFunctionsName.ETL_CONDITION_FILTER => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "option", "logicRule", "reIndex", "outputpath"))

      }

      case EtlFunctionsName.ETL_REMOVE_DUPLICATED => {

        val argNames = (Constants.ETLARGS.REMOVE_DUPLICATED)
        val argValues = (Constants.ETLARGS.REMOVE_DUPLICATED_DEFAULT)
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

      }

      case EtlFunctionsName.ETL_FIELDEXTRACT => {

        val argNames = (Constants.ETLARGS.FIELDEXTRACT)
        val argValues = (Constants.ETLARGS.FIELDEXTRACT_DEFAULT)
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

      }

      case EtlFunctionsName.ETL_MERGE_ROW => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("taskType","function", "inputpath1", "inputpath2", "reIndex", "outputpath"))

      }

      case EtlFunctionsName.ETL_MERGE_COL => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath1", "inputpath2", "reIndex", "outputpath"))

      }

      case EtlFunctionsName.ETL_ADD_INDEX => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath"))

      }

      case EtlFunctionsName.ETL_FIELDSPLIT => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "fieldName", "colDelimiter", "fieldDelimiter", "outputpath"))

      }

      case EtlFunctionsName.ETL_RENAME => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "oldName", "newName", "outputpath"))

      }

      case EtlFunctionsName.ETL_NORMALIZE => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "fieldName", "overwrite", "outputpath"))

      }

      case EtlFunctionsName.ETL_CONVERT_TYPE => {

        val argNames = Array[String]("function", "inputpath", "fieldName", "convertType", "overwrite", "outputpath")
        val argValues = Array(null, null, null, null, "false", null)
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case EtlFunctionsName.ETL_BINARY_SPLIT => {

        val argNames = (Constants.MLARGS.INPUT) ++ Constants.ETLARGS.BINARY_SPLIT
        val argValues = (Constants.MLARGS.INPUT_DEFAULT) ++ Constants.ETLARGS.BINARY_SPLIT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case EtlFunctionsName.ETL_MULTIPLE_SPLIT => {

        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "splitType", "splitValue", "outputpath"))

      }

      case EtlFunctionsName.ETL_DEL_NONE_VAL_ROW => {

        val argNames = (Constants.MLARGS.INPUT) ++ Constants.ETLARGS.DEL_NONE_VAL_ROW ++ Constants.MLARGS.OUTPUT
        val argValues = (Constants.MLARGS.INPUT_DEFAULT) ++ Constants.ETLARGS.DEL_NONE_VAL_ROW_DEFAULT ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }

      case EtlFunctionsName.ETL_FILL_NONE_VAL => {

        val argNames = (Constants.MLARGS.INPUT) ++ Constants.ETLARGS.FILL_NONE_VAL ++ Constants.MLARGS.OUTPUT
        val argValues = (Constants.MLARGS.INPUT_DEFAULT) ++ Constants.ETLARGS.FILL_NONE_VAL_DEFAULT ++ Constants.MLARGS.OUTPUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)

      }

      case EtlFunctionsName.ETL_JOIN => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath1", "inputpath2", "fields1", "fields2", "jointype", "outputpath"))
      }

      case EtlFunctionsName.ETL_UNION => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath1", "inputpath2", "outputpath", "delimiter", "fields1", "fields2"))
      }

      case EtlFunctionsName.ETL_SORT => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "fields", "isreverse"))
      }

      case EtlFunctionsName.ETL_FILESLICE => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath1", "outputpath2", "delimiter", "header", "begin", "end"))
      }

      case EtlFunctionsName.ETL_STRINGREPLACE => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "formerstring", "newstring", "fields"))
      }

      case EtlFunctionsName.ETL_TOLOWERCASE => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "fields"))
      }

      case EtlFunctionsName.ETL_TOUPPERCASE => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "fields"))
      }

      case EtlFunctionsName.ETL_MARK => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header"))
      }

      case EtlFunctionsName.ETL_REGEXMATCH => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "regstrarry", "fields", "isand"))
      }

      case EtlFunctionsName.ETL_DATEFORMAT => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "fields", "dateformat"))
      }

      case EtlFunctionsName.ETL_VARIANCE => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "field"))
      }

      case EtlFunctionsName.ETL_CENTRALMOMENT => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "field", "n"))
      }

      case EtlFunctionsName.ETL_COSINESIMILARITY => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "field1", "field2"))
      }

      case EtlFunctionsName.ETL_PEARSONSIMILARITY => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "field1", "field2"))
      }

      case EtlFunctionsName.ETL_DISTINCT => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "fields"))
      }

      case EtlFunctionsName.ETL_STANDAR => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header"))
      }

      case EtlFunctionsName.ETL_ROWCOUNT => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header"))
      }

      case EtlFunctionsName.ETL_TOPN => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "fields", "n"))
      }

      case EtlFunctionsName.ETL_MAXCOL => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "field"))
      }

      case EtlFunctionsName.ETL_MINCOL => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "field"))
      }

      case EtlFunctionsName.ETL_MEANCOL => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "field"))
      }

      case EtlFunctionsName.ETL_SUMCOL => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "field"))
      }

      case EtlFunctionsName.ETL_MULTIAGGRCOLS => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "aggmethods", "fields"))
      }

      case EtlFunctionsName.ETL_MAXALLROW => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header"))
      }

      case EtlFunctionsName.ETL_MINALLROW => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header"))
      }

      case EtlFunctionsName.ETL_FILTERS => {
        ArgsUtil.getArgsValue(keyAndValueMap, Array[String]("function", "inputpath", "outputpath", "delimiter", "header", "fields", "conditions", "values"))
      }

      case EtlFunctionsName.ETL_CUT => {

        val argNames = Constants.ETLARGS.COLUMNS_CUT
        val argValues =   Constants.ETLARGS.COLUMNS_CUT_DEFAULT
        ArgsUtil.getArgsValue(keyAndValueMap, argNames, argValues)
      }


      case _ => {
        logger.info("----------------------函数问题-------------------------")
        logger.error("输入函数不存在！function=" + functionName)
        logger.info("-----------------------------------------------------")
        throw new Exception("输入函数不存在！function=" + functionName)
      }

    }

  }

}
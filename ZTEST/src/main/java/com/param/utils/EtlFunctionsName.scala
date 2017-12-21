package com.param.utils

object EtlFunctionsName {
  val FUNCTION = "etl"

  val ETL_READ_CSV = "etl_read_csv"
  
  val ETL_WRITE_CSV = "etl_write_csv"
  /** 分层采样  */

  val ETL_STRATIFY_SAMPLE = "etl_stratify_sample"

  /**条件过滤  *******/
  val ETL_CONDITION_FILTER = "etl_condition_filter"

  /** 数据去重**/

  val ETL_REMOVE_DUPLICATED = "etl_remove_depulicated"

  /** 提取列 */
  val ETL_FIELDEXTRACT = "etl_fieldextract"

  /**按行合并**/
  val ETL_MERGE_ROW = "etl_merge_row"

  /** 按列合并**/
  val ETL_MERGE_COL = "etl_merge_col"

  /**  添加索引号****/
  val ETL_ADD_INDEX = "etl_add_index"

  /**  修改列名***/
  val ETL_RENAME = "etl_rename"

  /** 线性归一化****/
  val ETL_NORMALIZE = "etl_normalize"

  /** 特征类型转换****/
  val ETL_CONVERT_TYPE = "etl_convertFeature"

  /** 二类分组***/
  val ETL_BINARY_SPLIT = "etl_binary_split"

  /** 多类分组 ***/
  val ETL_MULTIPLE_SPLIT = "etl_multiple_split"
  
  val ETL_DEL_NONE_VAL_ROW ="etl_del_none_value_row"
  
  val ETL_FILL_NONE_VAL ="etl_fill_none_value"
  
  /** 不同数据集的Join操作 */
  val ETL_JOIN = "etl_join"

  /** 不同数据集的Union操作 */
  val ETL_UNION = "etl_union"

  /** sort操作 */
  val ETL_SORT = "etl_sort"

  /** 文件切片操作 */
  val ETL_FILESLICE = "etl_fileslice"

  /** 字符串替换 */
  val ETL_STRINGREPLACE = "etl_stringreplace"

  /** 字符串转小写 */
  val ETL_TOLOWERCASE = "etl_tolowercase"

  /** 字符串转大写 */
  val ETL_TOUPPERCASE = "etl_touppercase"

  /** 标记记录 */
  val ETL_MARK = "etl_mark"

  /** 正则匹配 */
  val ETL_REGEXMATCH = "etl_regexmatch"

  /** 字段拆分 */
  val ETL_FIELDSPLIT = "etl_fieldsplit"

  /** 日期格式转换 */
  val ETL_DATEFORMAT = "etl_dateformat"

  /** 样本方差 */
  val ETL_VARIANCE = "etl_variance"

  /** n阶中心距 */
  val ETL_CENTRALMOMENT = "etl_centralmoment"

  /** 余弦相似度 */
  val ETL_COSINESIMILARITY = "etl_cosinesimilarity"

  /** 皮尔逊相似度 */
  val ETL_PEARSONSIMILARITY = "etl_pearsonsimilarity"

  /** 去重 */
  val ETL_DISTINCT = "etl_distinct"

  /** 标准化列 */
  val ETL_STANDAR = "etl_standar"

  /** 计算每条记录在文件中出现的次数(全文件,注：每一行为一条记录) */
  val ETL_ROWCOUNT = "etl_rowcount"

  /** 计算指定列或者全文件的top-n个记录 */
  val ETL_TOPN = "etl_topn"

  /** 求指定列的最大值 */
  val ETL_MAXCOL = "etl_maxcol"

  /** 求指定列的最小值 */
  val ETL_MINCOL = "etl_mincol"

  /** 求指定列的平均值 */
  val ETL_MEANCOL = "etl_meancol"

  /** 求指定列的和 */
  val ETL_SUMCOL = "etl_sumcol"

  /** 根据指定方法myMethod(max、min、mean、sum),求指定列(列名称)的最大值/最小值/平均值/sum */
  val ETL_MULTIAGGRCOLS = "etl_multiaggrcols"

  /** 分别计算输入文件中所有行中的最大值  */
  val ETL_MAXALLROW = "etl_maxallrow"

  /** 分别计算输入文件中所有行中的最小值  */
  val ETL_MINALLROW = "etl_minallrow"

  /** 对数据进行随机采样  */
  val ETL_RANDOMSAMPLING = "etl_randomsampling"

  /** 条件过滤  */
  val ETL_FILTERS = "etl_filters"

  /** 数据裁剪 */
  val ETL_CUT = "etl_cutcol"

}
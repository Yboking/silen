package silen.scheduler.job.data
case class RequestBean(var modelPath: String, var data: Array[Array[String]], var function: String = "predict", var modelName: String = null) {

  def setModelPath(mph: String) = {

    this.modelPath = mph
    this
  }

  def setData(records: Array[Array[String]]) = {

    this.data = records
    this
  }

  def setFunction(func: String) = {

    this.function = func
    this
  }

  def setModelName(mname: String) = {

    this.modelName = mname
  }

  def toJson() = {

    ""
  }

  def this() = {

    this(null, null)
  }

}


package silen.scheduler.data.job

case class TaskDesc(
    id: Long = 0,
    ttype: String,
    command: Array[String],
    source: Int,
    target: Int,

    name: String = "TaskDesc",
    private var userId: String = null,

    private var jobId: String = null) {

  def getGlobalId = userId + "_" + jobId + "_" + id.toString()
  def getJobFullId = userId + "_" + jobId
  
  
  def setUserId(id :String) {
    
    this.userId = id
  }
  
  def getUserId() = this.userId
  
  
  def setJobId(id:String){
    this.jobId = id
  }
  
  def getJobId() = this.jobId
  
}


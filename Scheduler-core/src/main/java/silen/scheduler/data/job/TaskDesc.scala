package silen.scheduler.data.job

case class TaskDesc(
    id: Long = 0,
    ttype: String,
    command: Array[String], 
    source: Int, 
    target: Int,
    
    name:String = "TaskDesc",
    userId:String = "user001",
    
    jobId:String = "job001"
    

){
  
  
  def getJobId = userId  + "_" + jobId
}


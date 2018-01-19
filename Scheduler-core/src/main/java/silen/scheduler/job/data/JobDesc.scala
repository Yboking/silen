package silen.scheduler.job.data

case class JobDesc(id: String, userId: String, status:String ) {
  
  
  
  override def toString() = {
    
    id + "_" + userId
  }
}
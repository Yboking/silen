package silen.scheduler.service.client

import silen.scheduler.data.job.TaskDesc
import silen.scheduler.data.job.TaskType
import silen.scheduler.data.job.InvokeResult
import silen.scheduler.service.client.ServiceContext._
import silen.scheduler.utils.runtime.DateTimeUtil

object SchedulerSubmit {

  val tasks = Array(
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_fieldextract", "inputpath=E:/JAVA-EE/workspaces/data/output/1-4", "outputpath=E:/JAVA-EE/workspaces/data/output/4-5"), 4, 5, name = "etl_fieldextract"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=decisiontreeClass", "inputpath=E:/JAVA-EE/workspaces/data/output/2-3", "outputpath=E:/JAVA-EE/workspaces/data/output/3-5"), 3, 5, name = "decisiontreeClass"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=E:/JAVA-EE/workspaces/data/input/data2", "outputpath=E:/JAVA-EE/workspaces/data/output/1-4"), 1, 4, name = "etl_read_csv"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=E:/JAVA-EE/workspaces/data/input/input1", "outputpath=E:/JAVA-EE/workspaces/data/output/2-3"), 2, 3, name = "etl_read_csv"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=predict", "inputPathModel=E:/JAVA-EE/workspaces/data/output/3-5/model", "inputPathData=E:/JAVA-EE/workspaces/data/output/4-5", "outputpath=E:/JAVA-EE/workspaces/data/output/5-6"), 5, 6, name = "predict"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_write_csv", "filename=haha.csv", "inputpath=E:/JAVA-EE/workspaces/data/output/5-6", "outputpath=E:/JAVA-EE/workspaces/data/output/6-6"), 6, 6, name = "etl_write_csv"),
    TaskDesc(0, TaskType.RUN, null, 0, 0))

  def submitJob(tasks: Array[TaskDesc]) = {
    
    
    //TODO validate if the tasks have jobid 
    
    
    
    val jobId = buildJobId()
    for( t <- tasks){
      
      t.setJobId(jobId)
      
      t.setUserId(getRunTimeUser())
      
      
    }
    import scala.concurrent.ExecutionContext.Implicits.global
    val ss = new SingleService()
    import ss._

    for (t <- tasks) {
      ss.startTask[TaskDesc, InvokeResult](t, 5)
      
      // handle the response ?   Await.result(result, Duration(timeout, TimeUnit.SECONDS))
    }

  }
  
  
  def getRunTimeUser() = {
    
    "user:Tom"
  }
  
  
  def buildJobId() = {
    
    val timeTag = DateTimeUtil.currentDateTime("yyyyMMdd:HH:mm:ss:ms")
    
    "job:" + timeTag
    
    
  }

}
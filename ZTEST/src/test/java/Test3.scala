import youe.data.service.job.data.TaskDesc
import youe.data.service.job.data.TaskType
import youe.data.service.job.TaskGraph
import youe.data.service.job.data.NodeIdentity
import youe.data.service.job.JobContainer
import akka.actor.Props
import youe.data.service.job.DataNodeManager
import youe.data.service.job.RootNode
import youe.data.service.job.DataHandler

object Test3 {

  def main(args: Array[String]): Unit = {


    val startNode = JobContainer.createActor(Props(new DataNodeManager()))

    val messages = Array(
      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK","class=youe.data.scala.drivers.EtlDriver2","function=etl_read_csv", "inputpath=data/input/data2", "outputpath=data/output/1-4"), 1, 4),
      TaskDesc(1, TaskType.ASSIGN, Array("taskType=SPARK","class=youe.data.scala.drivers.EtlDriver2","function=etl_read_csv", "inputpath=data/input/input1", "outputpath=data/output/2-3"), 2, 3),
      TaskDesc(2, TaskType.ASSIGN, Array("taskType=SPARK","class=youe.data.scala.drivers.EtlDriver2","function=etl_fieldextract", "inputpath=data/output/1-4", "outputpath=data/output/4-5"), 4, 5),
      TaskDesc(3, TaskType.ASSIGN, Array("taskType=SPARK","class=youe.data.scala.drivers.MachineLearnDriver2","function=decisiontreeClass", "inputpath=data/output/2-3", "outputpath=data/output/3-5"), 3, 5),
      TaskDesc(4, TaskType.ASSIGN, Array("taskType=SPARK","class=youe.data.scala.drivers.MachineLearnDriver2","function=predict", "inputPathModel=data/output/3-5/model","inputPathData=data/output/4-5", "outputpath=data/output/5-6"), 5, 6),
      
       TaskDesc(5, TaskType.ASSIGN, Array("taskType=SPARK","class=youe.data.scala.drivers.EtlDriver2","function=etl_write_csv", "filename=haha.csv","inputpath=data/output/5-6", "outputpath=data/output/6-6"), 6, 6),
       TaskDesc(6, TaskType.RUN, null, 0, 0))

    for (ms <- messages) startNode ! ms
       

    
    
    
    
    println("ksdfe")
  }
}
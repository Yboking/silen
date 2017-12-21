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
      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK","class=youe.data.scala.drivers.EtlDriver2","function=etl_read_csv", "inputpath=data/input", "outputpath=data/output/kk"), 1, 2),
      TaskDesc(1, TaskType.ASSIGN, Array("function=kmeanscluster", "inputpath=data/input", "k=2", "maxiterations=3","outputpath=data/output/KM"), 2, 2)
//      ,TaskDesc(2, TaskType.ASSIGN, Array("function=etl_write_csv", "filename=haha.csv","inputpath=data/output/kk", "outputpath=data/output/22"), 3, 3)
      
      
      ,TaskDesc(3, TaskType.RUN, null, 0, 0))

    for (ms <- messages) startNode ! ms
       

    
    
    
    
    println("ksdfe")
  }
}
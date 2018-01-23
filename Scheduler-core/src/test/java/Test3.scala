import akka.actor.Props
import silen.scheduler.service.job.DataNodeManager
import silen.scheduler.service.job.JobContainer
import silen.scheduler.service.job.DataHandler
import silen.scheduler.data.job.JobDesc

object Test3 {

  
  
  def main(args: Array[String]): Unit = {

    
    
    println(JobDesc("job", "usr", "success"))
    
//    new DataHandler()
//    val startNode = JobContainer.createActor(Props(new DataNodeManager()))

    /**
     * Test Join Flow
     */
    //    val messages = Array(
    //      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_fieldextract", "inputpath=data/output/1-4", "outputpath=data/output/4-5"), 4, 5),
    //      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=decisiontreeClass", "inputpath=data/output/2-3", "outputpath=data/output/3-5"), 3, 5),
    //      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=data/input/data2", "outputpath=data/output/1-4"), 1, 4),
    //      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=data/input/input1", "outputpath=data/output/2-3"), 2, 3),
    //      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=predict", "inputPathModel=data/output/3-5/model", "inputPathData=data/output/4-5", "outputpath=data/output/5-6"), 5, 6),
    //      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_write_csv", "filename=haha.csv", "inputpath=data/output/5-6", "outputpath=data/output/6-6"), 6, 6),
    //      TaskDesc(0, TaskType.RUN, null, 0, 0))
    //
    //    for (ms <- messages) startNode ! ms

    /**
     *
     * Test Fork & Join Flow
     */

//    val messages = Array(
//      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=data/input/input1", "outputpath=data/output/1-2"), 1, 2),
//      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_binary_split", "splitType=0","splitValue=0.5","inputpath=data/output/1-2", "outputpath1=data/output/2-out1","outputpath2=data/output/2-out2"), 2, 3),
//      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_binary_split", "splitType=0","splitValue=0.5", "inputpath=data/output/1-2", "outputpath1=data/output/2-out1","outputpath2=data/output/2-out2"), 2, 4),
//      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=decisiontreeClass", "inputpath=data/output/2-out1", "outputpath=data/output/3-5"), 3, 5),
//      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_fieldextract", "inputpath=data/output/2-out2", "outputpath=data/output/4-5"), 4, 5),
//      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=predict", "inputPathModel=data/output/3-5/model", "inputPathData=data/output/4-5", "outputpath=data/output/5-6"), 5, 6),
//      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_write_csv", "filename=haha.csv", "inputpath=data/output/5-6", "outputpath=data/output/6-6"), 6, 6),
//      TaskDesc(0, TaskType.RUN, null, 0, 0))
//
//    for (ms <- messages) startNode ! ms

  }
}
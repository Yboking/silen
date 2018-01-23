
import akka.util.Timeout
import scala.concurrent.duration._

import java.util.concurrent.{ CyclicBarrier, TimeUnit, TimeoutException }
import scala.concurrent.Await
import silen.scheduler.service.client.ServiceContext._
import silen.scheduler.service.client.SingleService
import silen.scheduler.data.job.RequestBean
import silen.scheduler.data.job.TaskDesc
import silen.scheduler.data.job.InvokeResult
import silen.scheduler.data.job.TaskType

object Predict {

  def main(args: Array[String]): Unit = {

    val ss = new SingleService()
    import ss._
    import scala.concurrent.ExecutionContext.Implicits.global

    //    val rb = new RequestBean()
    //    ss.startTask(rb, 8)

    //    val result = ss.startTask[Message, InvokeResult](Message(content = "from client.."), 10)
    //    Await.result(result, Duration(10, TimeUnit.SECONDS))

    val tasks = Array(
      TaskDesc(0, TaskType.ASSIGN,
          Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_fieldextract", "inputpath=E:/JAVA-EE/workspaces/data/output/1-4", "outputpath=E:/JAVA-EE/workspaces/data/output/4-5"), 4, 5,name = "etl_fieldextract"),
      TaskDesc(0, TaskType.ASSIGN, 
          Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=decisiontreeClass", "inputpath=E:/JAVA-EE/workspaces/data/output/2-3", "outputpath=E:/JAVA-EE/workspaces/data/output/3-5"), 3, 5, name="decisiontreeClass"),
      TaskDesc(0, TaskType.ASSIGN, 
          Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=E:/JAVA-EE/workspaces/data/input/data2", "outputpath=E:/JAVA-EE/workspaces/data/output/1-4"), 1, 4, name="etl_read_csv"),
      TaskDesc(0, TaskType.ASSIGN, 
          Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=E:/JAVA-EE/workspaces/data/input/input1", "outputpath=E:/JAVA-EE/workspaces/data/output/2-3"), 2, 3,name ="etl_read_csv"),
      TaskDesc(0, TaskType.ASSIGN,
          Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=predict", "inputPathModel=E:/JAVA-EE/workspaces/data/output/3-5/model", "inputPathData=E:/JAVA-EE/workspaces/data/output/4-5", "outputpath=E:/JAVA-EE/workspaces/data/output/5-6"), 5, 6, name="predict"),
      TaskDesc(0, TaskType.ASSIGN, 
          Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_write_csv", "filename=haha.csv", "inputpath=E:/JAVA-EE/workspaces/data/output/5-6", "outputpath=E:/JAVA-EE/workspaces/data/output/6-6"), 6, 6, name="etl_write_csv"),
      TaskDesc(0, TaskType.RUN, null, 0, 0))

    for (t <- tasks) {

      ss.startTask[TaskDesc, InvokeResult](t, 5)

    }

  }

  //  def predictService(rb: RequestBean ) = {
  //    
  //    predictService(rb, timeout = 60)
  //  }

  def predictService(rb: RequestBean, timeout: Int = 60) = {

    val ss = new SingleService()
    import ss._
    import scala.concurrent.ExecutionContext.Implicits.global

    //    val rb = new RequestBean()
    //    ss.startTask(rb, 8)

    val result = ss.startTask[RequestBean, InvokeResult](rb, timeout)
    Await.result(result, Duration(timeout, TimeUnit.SECONDS))
    //    result.result(Duration(5, TimeUnit.SECONDS)).onSuccess({
    //      
    //      case ir : InvokeResult => ir
    //      
    //    })

    //    InvokeResult("success", "[0.12, 1.28]", "datetime")
  }

}
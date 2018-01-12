
import akka.util.Timeout
import scala.concurrent.duration._

import java.util.concurrent.{ CyclicBarrier, TimeUnit, TimeoutException }
import scala.concurrent.Await
import silen.scheduler.job.data.RequestBean
import silen.scheduler.job.data.InvokeResult
import silen.scheduler.service.client.ServiceContext._
import silen.scheduler.service.client.SingleService
import silen.scheduler.job.data.Message
import silen.scheduler.job.data.TaskDesc
import silen.scheduler.job.data.TaskType

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
      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_fieldextract", "inputpath=data/output/1-4", "outputpath=data/output/4-5"), 4, 5),
      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=decisiontreeClass", "inputpath=data/output/2-3", "outputpath=data/output/3-5"), 3, 5),
      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=data/input/data2", "outputpath=data/output/1-4"), 1, 4),
      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=data/input/input1", "outputpath=data/output/2-3"), 2, 3),
      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=predict", "inputPathModel=data/output/3-5/model", "inputPathData=data/output/4-5", "outputpath=data/output/5-6"), 5, 6),
      TaskDesc(0, TaskType.ASSIGN, Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_write_csv", "filename=haha.csv", "inputpath=data/output/5-6", "outputpath=data/output/6-6"), 6, 6),
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
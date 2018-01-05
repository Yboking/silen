
import akka.util.Timeout
import scala.concurrent.duration._

import java.util.concurrent.{ CyclicBarrier, TimeUnit, TimeoutException }
import scala.concurrent.Await
import silen.scheduler.job.data.RequestBean
import silen.scheduler.job.data.InvokeResult
import silen.scheduler.service.client.ServiceContext._
import silen.scheduler.service.client.SingleService
import silen.scheduler.job.data.Message

object Predict {

  def main(args: Array[String]): Unit = {

    val ss = new SingleService()
    import ss._
    import scala.concurrent.ExecutionContext.Implicits.global

    //    val rb = new RequestBean()
    //    ss.startTask(rb, 8)

    val result = ss.startTask[Message, InvokeResult](Message(content = "from client.."), 10)
    Await.result(result, Duration(10, TimeUnit.SECONDS))

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
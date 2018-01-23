package silen.scheduler.service.client
import akka.actor.ActorSystem
import akka.actor.Actor
import akka.pattern._
import akka.util.Timeout
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.ActorSelection
import akka.actor.ActorSelection.toScala
import silen.scheduler.service.client.ServiceContext._
import scala.concurrent.Future
import silen.scheduler.data.job.InvokeResult

class SingleService {


  /**
   * @task  request type
   * @timeout in seconds
   *
   */

  def startTask[T, R <: InvokeResult](task: T, timeout: Int)(implicit actor: ActorSelection) = {
    if (actor == null) println("actor system null")
    val result = actor.?(task)(Timeout(timeout seconds))

    //    result.mapTo[DataResult[R]]

    result.mapTo[InvokeResult]
      .recover({
        case e: AskTimeoutException =>
          e.printStackTrace(); e
        case e: java.util.concurrent.ExecutionException =>
          e.printStackTrace(); e

        case e: Exception =>

          e.printStackTrace(); e

      })

  }

}


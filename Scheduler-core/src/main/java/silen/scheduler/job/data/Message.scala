package silen.scheduler.job.data

case class Message(mType: Int = 0, content: Any)

object MessageType {

  val NODE_PRE_START = 1

  val NODE_RUNNING = 2
  

}
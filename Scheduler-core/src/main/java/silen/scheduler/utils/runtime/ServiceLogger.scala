package silen.scheduler.utils.runtime

trait ServiceLogger {

  def makeLoginfo(e: Throwable):String = {

    val msg = scala.collection.mutable.ArrayBuffer[String]()
    msg.append(e.toString)
    msg.append(e.getMessage)
     
    
    if(e.getCause != null){
      
      
      msg.append(makeLoginfo(e.getCause))
      
     
    }
    if (e.getStackTrace != null) {
      for (trace <- e.getStackTrace)
        msg.append(trace.toString())
    }
    msg.mkString("\r\n")
  }
}
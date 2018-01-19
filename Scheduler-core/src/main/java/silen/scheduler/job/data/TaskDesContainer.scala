package silen.scheduler.job.data

import scala.collection.mutable.ListBuffer

class TaskDesContainer {

  val map = scala.collection.mutable.HashMap[String, ListBuffer[TaskDesc]]()

  def addTask(globalJob: String, task: TaskDesc) {

    val tasks = map.getOrElse(globalJob, ListBuffer[TaskDesc]())

    map.put(globalJob, tasks.+=(task))
  }

  def removeTaskByJob(globalJob: String) {

    map.remove(globalJob)
  }

  def getTasksByJob(globalJob: String) = {

    val tmp = map.get(globalJob) match {
      case Some(t) => t
      case None => {

        throw new Exception(s"no tasks fonud for job $globalJob")
      }
    }

    tmp.toArray
  }

}
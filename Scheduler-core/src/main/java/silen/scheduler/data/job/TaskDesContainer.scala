package silen.scheduler.data.job

import scala.collection.mutable.ListBuffer

class TaskDesContainer {

  val map = scala.collection.mutable.HashMap[String, ListBuffer[TaskDesc]]()
  val nodeNames = scala.collection.mutable.HashMap[String, String]()
  def addTask(globalJob: String, task: TaskDesc) {

    val tasks = map.getOrElse(globalJob, ListBuffer[TaskDesc]())

    map.put(globalJob, tasks.+=(task))

    nodeNames.put(globalJob + "_" + task.source, task.name)
  }

  def removeTaskByJob(globalJob: String) {

    val tmpTasks = getTasksByJob(globalJob)

    for (t <- tmpTasks) {
      nodeNames.remove(globalJob + "_" + t.source)
    }

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

  def getNodeNameById(jobId: String, nodeId: String) = {
    
    nodeNames.getOrElse(jobId + "_" + nodeId, "Node Name")
  }

}
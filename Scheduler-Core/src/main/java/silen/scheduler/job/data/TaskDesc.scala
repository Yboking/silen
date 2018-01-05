package silen.scheduler.job.data

case class TaskDesc(id: Long = 0, ttype: String, command: Array[String], source: Int, target: Int)


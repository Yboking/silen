package youe.data.service.job

import youe.data.service.job.data.TaskDesc
import scala.collection.mutable.ArrayBuffer
import youe.data.service.job.data.TaskType

object TaskGraph {

  def apply(tasks: Array[TaskDesc]) = {
    new TaskGraph().init(tasks)
  }

}

class TaskGraph {

  var from: Array[Int] = null
  var to: Array[Int] = null
  
  //var edgeInfo: Array[Array[String]] = null

  var nodeInfo: Array[Array[String]] = null
  
  var firstEdge: Array[Int] = null
  var nextEdge: Array[Int] = null

  def init(tasks: Array[TaskDesc]) = {

    newCapacity(tasks.length)

    for (i <- 0 until firstEdge.length) {

      firstEdge(i) = -1
    }

    for (i <- 0 until tasks.length) {

      val task = tasks(i)
      from(i) = task.source
      to(i) = task.target
      
      
      nodeInfo(task.source) = task.command

      nextEdge(i) = firstEdge(from(i))

      firstEdge(from(i)) = i

    }

    this

  }

  def findStartNodes() = {

    val res = ArrayBuffer[Int]()
    //  节点编号从1开始
    for (i <- 1 until firstEdge.length) {

      if (firstEdge(i) != -1 && getPrenodes(i).length == 0) {
        res.append(i)
      }
    }
    res.toArray
  }

  def getPrenodes(id: Int) = {

    val res = ArrayBuffer[Int]()

    for (i <- 0 until to.length) {
      if (to(i) == id && from(i) != to(i)) {
        res.append(from(i))
      }
    }
    res.toArray
  }

  def getSuccnodes(id: Int) = {

    val res = ArrayBuffer[Int]()
    val edges = findNodeEdges(id)
    var notMatch = false
    for (edge <- edges) {
      res.append(to(edge))
    }
    res.toArray

  }

  def findNodeEdges(id: Int) = {

    val res = ArrayBuffer[Int]()
    var tmp = firstEdge(id)
    while (tmp != -1) {
      res.append(tmp)
      tmp = nextEdge(tmp)
    }
    res.toArray
  }

  def newCapacity(tasklength: Int) {

    from = new Array[Int](tasklength)
    to = new Array[Int](tasklength)

    //  节点编号从1开始
    firstEdge = new Array[Int](tasklength + 1 + 1)
    nextEdge = new Array[Int](tasklength)
    nodeInfo = new Array[Array[String]](firstEdge.length)
  }

  def getNodeNum = {

    val maxNum = firstEdge.length - 1
    //孤立点
    if (firstEdge(maxNum) == -1 && getPrenodes(maxNum).length == 0) {
      maxNum - 1
    } else
      maxNum

  }
  
  
  def getSingleNodeInfo(id :Int) = nodeInfo(id)

}




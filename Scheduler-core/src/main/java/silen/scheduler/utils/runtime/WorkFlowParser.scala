package silen.scheduler.utils.runtime

import silen.scheduler.core.oozieconf.OWorkFlow
import scala.collection.mutable.HashMap
import silen.scheduler.data.job.TaskDesc
import scala.collection.mutable.ArrayBuffer

class WorkFlowParser(owf: OWorkFlow) {

  val nodeMap = HashMap[String, Int]()
  val joinPoints = HashMap[String, String]()

  private def createNodeIndex(name: String) = {

    val newname = getJoinPoint(name);

    nodeMap.get(newname) match {

      case Some(index) => index

      case None => {

        val index = nodeMap.keySet.size + 1
        nodeMap.put(newname, index)
        index
      }
    }

  }
  def toNativeWorkFlow() = {

    val actions = owf.getAction()
    val tasks = ArrayBuffer[TaskDesc]()

    initJoints();

    for (action <- actions) {

      val source = createNodeIndex(action.getName());

      val targetNodeName = if ("end".equals(action.getOk().getTo))
        action.getName else action.getOk().getTo

      val target = createNodeIndex(targetNodeName);

      val spark = action.getSpark();

      val actionArgs = spark.getArg();

      val taskDesc = new TaskDesc(actionArgs, source, target,
        action.getName());
      tasks.append(taskDesc)

    }

    new NativeWorkFlow(tasks.toArray);

  }

  private def initJoints() {

    for (join <- owf.getJoin()) {

      joinPoints.put(join.getName(), join.getTo());

    }
  }

  private def getJoinPoint(name: String) = {

    var tmpname = name

    while (joinPoints.getOrElse(tmpname, null) != null) {

      tmpname = joinPoints.getOrElse(tmpname, null)
    }
    tmpname;
  }
}


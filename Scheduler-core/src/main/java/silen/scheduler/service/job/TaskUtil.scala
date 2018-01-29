package silen.scheduler.service.job

import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession

import org.apache.spark.SparkConf
import java.net.URLClassLoader
import java.net.URL
import java.io.File
import silen.scheduler.common.utils.RunConstants
import silen.scheduler.utils.runtime.UserClassLoader

import silen.scheduler.data.job.DataRes
import silen.scheduler.data.job.TaskType
import silen.scheduler.data.job.NodeIdentity

object TaskUtil {

  def checkSystem() = {
    true
  }

  def initRuntime(mode: String = RunConstants.RUN_LOCAL) {

    //TODO check task type 

    mode match {
      case RunConstants.RUN_LOCAL => {

        initJavaClassLoader()
      }

    }

  }

  def initJavaClassLoader() {

    if (checkSystem()) {
      UserClassLoader.initLoader
    }

  }

  def handleSparkTask(mainClass: String, args: Array[String]) = {

    try {
      System.setProperty("HADOOP_USER_NAME", "hdfs")
      initRuntime()

      val clazz = UserClassLoader.loadClass("extlibs/user.jar", mainClass)

      val method = clazz.getDeclaredMethod("main", classOf[Array[String]])
      val result = method.invoke(null, args)

      val t = classOf[String]
      if (result == null) {

        new DataRes(null, ResourceType.DISK, args(args.length - 1))
      } else if (result.isInstanceOf[DataFrame]) {

        new DataRes(null, ResourceType.DISK, args(args.length - 1))
      } else if (result.isInstanceOf[PipelineModel]) {

        new DataRes(null, ResourceType.DISK, args(args.length - 1))
      } else {

        val data: DataFrame = null
        new DataRes(null, ResourceType.DISK, data)

      }

    } catch {

      case e: Exception => {

        throw e;
      }

    }

  }
  //  def handleTask(cmd: Array[String], nodedata: Array[DataRes[Any]] = null) = {
  //    val args = KeyValuePairArgsUtil.extractValueArgs(cmd)
  //    println("handle task")
  //
  //    cmd.foreach { println }
  //    args(0) match {
  //
  //      case TaskType.SPARK => {
  //
  //        handleSparkTask(args(1), cmd.slice(2, cmd.length))
  //      }
  //
  //    }
  //
  //  }

  def handleTask(nid: NodeIdentity, nodedata: Array[DataRes[Any]]) = {

    try {
      val actParser = nid.actionParser
      actParser.getActionType() match {

        case TaskType.SPARK => {

          handleSparkTask(actParser.getActionEntrance(), actParser.getActionArgs().asInstanceOf[Array[String]])
        }

      }

    } catch {

      case e: Exception => {

        throw e
      }
    }

  }

}
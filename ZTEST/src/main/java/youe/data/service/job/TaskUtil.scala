package youe.data.service.job

import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession

import youe.data.service.job.data.DataRes
import youe.data.service.job.data.TaskType
import com.runtime.utils.UserClassLoader
import com.param.utils.KeyValuePairArgsUtil
import org.apache.spark.SparkConf

object TaskUtil {

  
  
  
  def handleSparkTask(mainClass: String, args: Array[String]) = {

    val conf = new SparkConf().set("spark.jars", "D:\\GitRepo\\ZTEST\\extlibs\\user.jar").setJars(Array("D:\\GitRepo\\ZTEST\\extlibs\\user.jar"))
   implicit val spark = SparkSession.builder().appName(this.getClass.getName)
    .master("local[*]").config(conf).getOrCreate()  
    
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

  }
  def handleTask(cmd: Array[String], nodedata: Array[DataRes[Any]] = null) = {
    val args = KeyValuePairArgsUtil.extractValueArgs(cmd)

    args(0) match {

      case TaskType.SPARK => {

        handleSparkTask(args(1), cmd.slice(2, cmd.length))
      }

    }

  }

}
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.spark.sql.Row
import silen.scheduler.common.utils.HiveRDD

object TestUDFRDD {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().set("url", "jdbc:hive2://172.16.0.133:10000/default").set("user", "hive").set("password", "hive")

    implicit val spark = SparkSession.builder().config(conf).appName(getClass.getName).master("local[*]").getOrCreate()

    val rdd = new HiveRDD("student", spark, 0, 8, 2)

    rdd.toDF(false).show()

  }
}



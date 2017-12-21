

import java.sql.{ Connection, ResultSet }

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import scala.reflect.ClassTag

//import org.apache.spark.{Partition, SparkContext, TaskContext}
//import org.apache.spark.api.java.{JavaRDD, JavaSparkContext}
//import org.apache.spark.api.java.JavaSparkContext.fakeClassTag
//import org.apache.spark.api.java.function.{Function => JFunction}
//import org.apache.spark.internal.Logging
//import org.apache.spark.util.NextIterator
import org.apache.spark.Partition
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.internal.Logging
import org.apache.spark.TaskContext
import org.apache.spark.internal.Logging
import org.apache.spark.sql.Row
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.BooleanType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.AtomicType

class TaskPartition(idx: Int, val lower: Long, val upper: Long) extends Partition {
  override def index: Int = idx
}

class HiveRDD(tableName: String, @transient spark: SparkSession, lower: Long = 0, upper: Long = 0, numPartitions: Int = 3) extends UDFRDD[Row](tableName, spark, lower, upper, numPartitions) {
  val rowkeyName = "rowkey"
  override def getMetaData() = {

    val meta = taskImpl.getMetaData()
    meta.+:((rowkeyName, IntegerType))
  }
  def toDF(withRowKey: Boolean = true) = {

    val result = spark.createDataFrame(this, StructType(getMetaData().toArray.map(t => StructField(t._1, t._2, true))))

    if (!withRowKey) result.drop(rowkeyName) else result

  }

}

abstract class UDFRDD[T: ClassTag](tableName: String, @transient spark: SparkSession, lower: Long, upper: Long, numPartitions: Int) extends RDD[T](spark.sparkContext, Nil) {

  var (lowerBound, upperBound) = (lower, upper)

  val url = context.getConf.get("url")
  val user = context.getConf.get("user")
  val password = context.getConf.get("password")

  @transient val taskImpl = new TaskImpl(url, user, password)

  {
    if (upperBound == 0) {
      upperBound = taskImpl.countData(tableName)
    }
    if (lowerBound < 0) lowerBound = 0
  }

  override def getPartitions: Array[Partition] = {
    val length = BigInt(1) + upperBound - lowerBound
    (0 until numPartitions).map { i =>
      val start = lowerBound + ((i * length) / numPartitions)
      val end = lowerBound + (((i + 1) * length) / numPartitions) - 1
      new TaskPartition(i, start.toLong, end.toLong)
    }.toArray
  }

  def resultSetConverter(rs: ResultSet): T = {

    Row.fromSeq(Array.tabulate[Object](rs.getMetaData.getColumnCount)(i => rs.getObject(i + 1))).asInstanceOf[T]

  }

  def getMetaData(): Array[(String, DataType)]

  override def compute(split: Partition, context: TaskContext): Iterator[T] = new SimpleIterator[T] {

    val part = split.asInstanceOf[TaskPartition]

    val conn = new TaskImpl(url, user, password).getConn()
    val (lower, upper) = (part.lower.toString, part.upper.toString)
    val sql = s"select * from (select row_number() over () as rnum , $tableName.* from $tableName ) t where rnum between $lower and $upper"

    val stmt = conn.createStatement()

    val rs = stmt.executeQuery(sql)

    override def getNext(): T = {
      if (rs.next()) {

        //    	  Row.fromSeq(Array.tabulate[Object](rs.getMetaData.getColumnCount)(i => rs.getObject(i + 1))).asInstanceOf[T]
        //        3.asInstanceOf[T]

        resultSetConverter(rs)
      } else {
        finished = true
        null.asInstanceOf[T]
      }
    }

    override def close() {
      try {
        if (null != rs) {
          rs.close()
        }
      } catch {
        case e: Exception => logWarning("Exception closing resultset", e)
      }
      try {
        if (null != stmt) {
          stmt.close()
        }
      } catch {
        case e: Exception => logWarning("Exception closing statement", e)
      }
      try {
        if (null != conn) {
          conn.close()
        }
        logInfo("closed connection")
      } catch {
        case e: Exception => logWarning("Exception closing connection", e)
      }
    }
  }

  class TaskImpl(url: String, user: String, password: String) extends Serializable {

    val query = s"select * from  $tableName"

    def getMetaData() = {
      var stmt = getConn().createStatement()
      val res = stmt.executeQuery(query);
      val colunmCount = res.getMetaData().getColumnCount();

      val result = new Array[(String, DataType)](colunmCount);
      for (i <- 0 until colunmCount) {

        val cname = res.getMetaData().getColumnName(i + 1);
        val ctype = res.getMetaData().getColumnTypeName(i + 1);

        result(i) = (cname, ctype match {
          case "int"     => IntegerType
          case "bigint"  => LongType
          case "string"  => StringType
          case "varchar" => StringType
          case "double"  => DoubleType
          case "boolean" => BooleanType

        })
      }

      result
    }

    def countData(tableName: String) = {

      var stmt = getConn().createStatement()
      val sql = "select count(1) from " + tableName;
      val res = stmt.executeQuery(sql);

      if (res.next()) {
        res.getString(1).toLong

      } else 0
    }

    def showData(stmt: Statement, tableName: String) = {

      val res = stmt.executeQuery(query);
      val colunmCount = res.getMetaData().getColumnCount();
      val colNameArr = new Array[String](colunmCount);
      val colTypeArr = new Array[String](colunmCount);

      for (i <- 0 until colunmCount) {

        val cname = res.getMetaData().getColumnName(i + 1);
        val ctype = res.getMetaData().getColumnTypeName(i + 1);

        ctype match {
          case "string" => res.getString(i + 1)
          case "int"    => res.getInt(i + 1)
          case "double" => res.getDouble(i + 1)

        }

      }
      while (res.next()) {
        res.getInt(1) + "\t" + res.getString(2);
      }
    }

    def loadData(stmt: Statement, tableName: String, filepath: String) =
      {
        val filepath = "user.txt";
        val sql = "load data local inpath '" + filepath + "' into table " + tableName;
        println("Running:" + sql);
        stmt.execute(sql);
      }

    def describeTables(stmt: Statement, tableName: String) = {
      val sql = "describe " + tableName;
      val res = stmt.executeQuery(sql);
      while (res.next()) {
        println(res.getString(1) + "\t" + res.getString(2));
      }
    }

    def showTables(stmt: Statement, tableName: String) = {
      val sql = "show tables '" + tableName + "'";
      val res = stmt.executeQuery(sql);
      if (res.next()) {
        println(res.getString(1));
      }
    }

    def createTable(stmt: Statement, tableName: String) = {
      val sql = "create table " + tableName + " (key int, value string)  row format delimited fields terminated by '\t'";
      stmt.execute(sql);
    }

    def dropTable(stmt: Statement) = {
      // 创建的表名  
      val tableName = "testHive";
      val sql = "drop table  " + tableName;
      stmt.execute(sql);
      tableName;
    }

    def getConn() = {

      val driverName = "org.apache.hive.jdbc.HiveDriver"
      Class.forName(driverName);
      DriverManager.getConnection(url, user, password);
    }

  }

}

abstract class SimpleIterator[U] extends Iterator[U] {

  private var gotNext = false
  private var nextValue: U = _
  private var closed = false
  protected var finished = false
  protected def getNext(): U

  protected def close()

  def closeIfNeeded() {
    if (!closed) {
      closed = true
      close()
    }
  }

  override def hasNext: Boolean = {
    if (!finished) {
      if (!gotNext) {
        nextValue = getNext()
        if (finished) {
          closeIfNeeded()
        }
        gotNext = true
      }
    }
    !finished
  }

  override def next(): U = {
    if (!hasNext) {
      throw new NoSuchElementException("End of stream")
    }
    gotNext = false
    nextValue
  }
}


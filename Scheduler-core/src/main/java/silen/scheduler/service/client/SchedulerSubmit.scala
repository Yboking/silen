package silen.scheduler.service.client

import silen.scheduler.data.job.TaskDesc
import silen.scheduler.data.job.TaskType
import silen.scheduler.data.job.InvokeResult
import silen.scheduler.service.client.ServiceContext._
import silen.scheduler.utils.runtime.DateTimeUtil
import java.util.Properties
import org.dom4j.Element
import silen.scheduler.utils.runtime.XMLHelper
import silen.scheduler.utils.runtime.WorkFlowParser
import java.io.FileOutputStream
import java.io.IOException
import org.dom4j.io.OutputFormat
import java.io.File
import org.dom4j.io.XMLWriter
import org.apache.commons.io.FileUtils

object SchedulerSubmit {

  val tasks = Array(
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_fieldextract", "inputpath=E:/JAVA-EE/workspaces/data/output/1-4", "outputpath=E:/JAVA-EE/workspaces/data/output/4-5"), 4, 5, name = "etl_fieldextract"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=decisiontreeClass", "inputpath=E:/JAVA-EE/workspaces/data/output/2-3", "outputpath=E:/JAVA-EE/workspaces/data/output/3-5"), 3, 5, name = "decisiontreeClass"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=E:/JAVA-EE/workspaces/data/input/data2", "outputpath=E:/JAVA-EE/workspaces/data/output/1-4"), 1, 4, name = "etl_read_csv"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_read_csv", "inputpath=E:/JAVA-EE/workspaces/data/input/input1", "outputpath=E:/JAVA-EE/workspaces/data/output/2-3"), 2, 3, name = "etl_read_csv"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.MachineLearnDriver2", "function=predict", "inputPathModel=E:/JAVA-EE/workspaces/data/output/3-5/model", "inputPathData=E:/JAVA-EE/workspaces/data/output/4-5", "outputpath=E:/JAVA-EE/workspaces/data/output/5-6"), 5, 6, name = "predict"),
    TaskDesc(0, TaskType.ASSIGN,
      Array("taskType=SPARK", "class=youe.data.scala.drivers.EtlDriver2", "function=etl_write_csv", "filename=haha.csv", "inputpath=E:/JAVA-EE/workspaces/data/output/5-6", "outputpath=E:/JAVA-EE/workspaces/data/output/6-6"), 6, 6, name = "etl_write_csv"),
    TaskDesc(0, TaskType.RUN, null, 0, 0))

  def submitJob(tasks: Array[TaskDesc]) = {

    //TODO validate if the tasks have jobid 

    val jobId = buildJobId()
    for (t <- tasks) {

      t.setJobId(jobId)

      t.setUserId(getRunTimeUser())

    }

    import scala.concurrent.ExecutionContext.Implicits.global
    val ss = new SingleService()
    import ss._

    for (t <- tasks) {
      ss.startTask[TaskDesc, InvokeResult](t, 5)

      // handle the response ?   Await.result(result, Duration(timeout, TimeUnit.SECONDS))
    }

  }

  def submitJob(element: Element, xmlProperties: Properties) {

//    val oozieDefinePath = writeOozieJobDefine(element)

    
    val fixElement  = fixNamespace(element)
    val owf = XMLHelper.parseOozieWorkFlow2(fixElement)
    val oconf = XMLHelper.xml2OozieConfig(xmlProperties)
    val wfparser = new WorkFlowParser(owf, oconf)

    val nativeWF = wfparser.toNativeWorkFlow()

    println("submitJob")
    for (task <- nativeWF.getTasks) {

      println(task)
    }

    submitJob(nativeWF.getTasks)
  }

  private def fixNamespace(element:Element) =  {

    
    XMLHelper.fixNamespace(element)
//    {
//      try {
//        val lines = FileUtils.readLines(new File(oldpath));
//
//        for (i <- 0 until lines.size()) {
//
//          val line = lines.get(i);
//          if (line.contains(":xmlns")) {
//            val newLine = line.replaceAll(":xmlns", "");
//            lines.remove(i);
//            lines.add(i, newLine);
//
//          }
//        }
//        FileUtils.writeLines(new File(newpath), lines);
//      } catch {
//
//        case e: IOException => {
//          e.printStackTrace();
//        }
//
//      }
//    }
  }

  def writeOozieJobDefine(element: Element) = {

    val folder = System.getProperty("java.io.tmpdir");
    if (folder != null) {

      val filename = DateTimeUtil.currentDateTime("YYYYMMddHHmmssms")

      val file = new File(folder + File.separator + filename);

      try {
        if (!file.getParentFile().exists())
          file.getParentFile().mkdirs();
        if (!file.exists())
          file.createNewFile();

        val out = new FileOutputStream(file);

        // 创建一个OutputFormat对象(XML以比较友好的格式输出)
        val format = OutputFormat.createPrettyPrint();
        // 设置XML文档编码
        format.setEncoding("UTF-8");
        // 设置XML文档缩进
        format.setIndent(true);
        // 创建一个XMLWriter对象
        val writer = new XMLWriter(out, format);
        writer.write(element);
        writer.close();
        file.getAbsolutePath
      } catch {

        case e: IOException =>
          {

            e.printStackTrace();
          }

          null
      }
    } else {

      throw new Exception("oozie job define write error")

    }

  }

  def getRunTimeUser() = {

    "user:Tom"
  }

  def buildJobId() = {

    val timeTag = DateTimeUtil.currentDateTime("yyyyMMdd:HH:mm:ss:ms")

    "job:" + timeTag

  }

}
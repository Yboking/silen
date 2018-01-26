
import silen.scheduler.data.job.TaskDesc
import silen.scheduler.data.job.TaskType
import silen.scheduler.service.client.SchedulerSubmit
import silen.scheduler.utils.runtime.XMLHelper
import silen.scheduler.utils.runtime.WorkFlowParser

object TestSubmit {
  def main(args: Array[String]): Unit = {

    //    println("${stateModel1_etl_read_csv_function}s".matches("\\$\\{.+\\}"))
    test2()
  }

  def test2() {

    val owf = XMLHelper.parseOozieWorkFlow("oozie-job-define.xml")
    val oconf = XMLHelper.xml2OozieConfig("oozie-job-config.xml")
    val wfparser = new WorkFlowParser(owf, oconf)

    val nativeWF = wfparser.toNativeWorkFlow()

    println(nativeWF)
    for (task <- nativeWF.getTasks) {

      println(task)
    }
    SchedulerSubmit.submitJob(nativeWF.getTasks)

  }
  def test1() {

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

    SchedulerSubmit.submitJob(tasks)
  }

}
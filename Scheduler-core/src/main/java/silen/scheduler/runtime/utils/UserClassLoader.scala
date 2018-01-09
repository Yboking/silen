package silen.scheduler.runtime.utils

import java.io.File
import java.net.URLClassLoader
import java.net.URL
import java.io.FilenameFilter
import scala.collection.mutable.ArrayBuffer
import silen.scheduler.common.utils.RunConstants

object UserClassLoader {

  
  //TODO need to update the class ?  
  def loadClass(filepath: String, classname: String) = {

    val file = new File(filepath);
    //    val loader = new URLClassLoader(Array[URL](file.toURI().toURL()), this.getClass.getClassLoader);
    val loader = new URLClassLoader(Array[URL](file.toURI().toURL()), Thread.currentThread().getContextClassLoader());

    //  val res =  loader.findResource("EtlArithmeticV2$$anonfun$23$$anonfun$apply$2.class")
    //		  val res =  loader.findResource("EtlArithmetic.class")

    //  println(res.toString())
    //    val c = loader.loadClass("youe.data.scala.etl.EtlArithmeticV2$$anonfun$23$$anonfun$apply$2");
    loader.loadClass(classname);

    //    val c = Thread.currentThread().getContextClassLoader.loadClass("youe.data.scala.etl.EtlArithmeticV2$$anonfun$23$$anonfun$apply$2")

  }

  def initLoader() {

    var deps = for (
      single <- System.getenv(RunConstants.WORKER_JAVA_CLASSPATH)
        .split(RunConstants.WINDOWS_ENV_DELIMITER)
    ) yield {
      new File(single).toURI().toURL()
    }

    deps = deps.++(listResource(new File(RunConstants.USER_DEPS_DIR), true)
      .map(_.toURI().toURL()))

    println(Thread.currentThread())
    Thread.currentThread().setContextClassLoader(

      new URLClassLoader(deps, Thread.currentThread().getContextClassLoader()))
  }

  def listResource(file: File, recursive: Boolean = false): Array[File] = {

    if (file.isDirectory()) {

      val result = ArrayBuffer[File]()
      for (
        f <- file.listFiles(new FilenameFilter() {

          def accept(dir: File, name: String) = {

            //TODO chose res to add
            true
          }
        })
      ) {

        if (f.isDirectory() && recursive) {

          result.appendAll(listResource(f, true))
        } else if (f.isFile()) {
          result.append(f)
        }
      }
      result.toArray
    } else {
      Array(file)
    }

  }

  def loadClass(classname: String) = {

    //    val file = new File(filepath);
    //    val loader = new URLClassLoader(Array[URL](file.toURI().toURL()));
    //    val clazz = loader.loadClass("oracle.jdbc.driver.OracleDriver");

  }

}
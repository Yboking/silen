package silen.scheduler.utils.runtime

import java.io.File
import java.net.URLClassLoader
import java.net.URL
import java.io.FilenameFilter
import scala.collection.mutable.ArrayBuffer
import silen.scheduler.common.utils.RunConstants
import silen.scheduler.service.job.ConfigKeys

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

    var userExtlibs = System.getProperty(ConfigKeys.USER_EXT_LIBS)
    if (userExtlibs == null) {
      userExtlibs = System.getenv(RunConstants.WORKER_JAVA_CLASSPATH)
    }

    if (userExtlibs != null) {
      var deps = for (single <- userExtlibs.split(RunConstants.WINDOWS_ENV_DELIMITER)) yield {
        val tmpRes = for (file <- listResource(new File(single), true)) yield {
          file.toURI().toURL()
        }
        tmpRes
      }
      val newdeps = deps.flatMap(x => x)
      println(Thread.currentThread())
      Thread.currentThread().setContextClassLoader(
        new URLClassLoader(newdeps, Thread.currentThread().getContextClassLoader()))
    }

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
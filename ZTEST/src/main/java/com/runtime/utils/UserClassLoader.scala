package com.runtime.utils

import java.io.File
import java.net.URLClassLoader
import java.net.URL

object UserClassLoader {

  def loadClass(filepath: String, classname: String) = {

    val file = new File(filepath);
//    val loader = new URLClassLoader(Array[URL](file.toURI().toURL()), this.getClass.getClassLoader);
    val loader = new URLClassLoader(Array[URL](file.toURI().toURL()), Thread.currentThread().getContextClassLoader());
    
//  val res =  loader.findResource("EtlArithmeticV2$$anonfun$23$$anonfun$apply$2.class")
//		  val res =  loader.findResource("EtlArithmetic.class")
  
//  println(res.toString())
    val c = loader.loadClass("youe.data.scala.etl.EtlArithmeticV2$$anonfun$23$$anonfun$apply$2");
    loader.loadClass(classname);

  }

  def loadClass(classname: String) = {

    //    val file = new File(filepath);
    //    val loader = new URLClassLoader(Array[URL](file.toURI().toURL()));
    //    val clazz = loader.loadClass("oracle.jdbc.driver.OracleDriver");

  }

}
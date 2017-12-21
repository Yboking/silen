package com.runtime.utils

import java.io.File
import java.net.URLClassLoader
import java.net.URL

object UserClassLoader {

  def loadClass(filepath: String, classname: String) = {

    val file = new File(filepath);
    val loader = new URLClassLoader(Array[URL](file.toURI().toURL()));
    loader.loadClass(classname);

  }

  def loadClass(classname: String) = {

    //    val file = new File(filepath);
    //    val loader = new URLClassLoader(Array[URL](file.toURI().toURL()));
    //    val clazz = loader.loadClass("oracle.jdbc.driver.OracleDriver");

  }

}
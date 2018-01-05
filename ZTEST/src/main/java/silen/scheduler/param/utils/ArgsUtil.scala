package silen.scheduler.param.utils

import java.util.HashMap
import org.apache.log4j.Logger

/**
 * 参数处理类
 */
object ArgsUtil {

  val log = Logger.getLogger(this.getClass);
  def getArgsValue(keyValueArgs: HashMap[String, String], argsNames: Array[String]): Array[String] = {
    val argsValue = new Array[String](argsNames.length)
    for (i <- 0 until argsNames.length) {
      println(argsNames(i))
      if (argsNames(i).equalsIgnoreCase("reIndex")) {

        argsValue(i) = keyValueArgs.getOrDefault(argsNames(i).toLowerCase(), "false").toString()
      } else
        argsValue(i) = keyValueArgs.get(argsNames(i).toLowerCase()).toString()
    }
    argsValue
  }

  def getArgsValue(keyValueArgs: HashMap[String, String], argsNames: Array[String], defaultValues: Array[String]): Array[String] = {
    val argsValue = new Array[String](argsNames.length)
    for (i <- 0 until argsNames.length) {
      argsValue(i) = keyValueArgs.getOrDefault(argsNames(i).toLowerCase(), defaultValues(i))
      log.info(argsNames(i) + "-->" + argsValue(i))
    }
    argsValue
  }

}

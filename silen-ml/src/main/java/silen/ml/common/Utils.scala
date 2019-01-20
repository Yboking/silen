package silen.ml.common

object Utils {

  def isEmpty(value :String) :Boolean = {

      return value == null || value.trim.equals("")

  }
}

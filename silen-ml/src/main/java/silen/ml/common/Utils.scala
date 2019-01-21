package silen.ml.common

object Utils {

  def isEmpty(value :Any) :Boolean = {

    if(value.isInstanceOf[String]){
      return value == null || value.toString.trim.equals("")
    }else if( value.isInstanceOf[List] ||
    value.isInstanceOf[Array]){
      return value.asInstanceOf[Seq].size == 0
    }
    return  value == null
  }
}

package silen.ml.common

import akka.Main

object ParamAssigner{
  

    def splitParam(size :Int = 3) {

    
    val groups = 10 / size
    
    
    var i = 0
    var indexSplit = for( i <- 0 until  groups - 1) yield {
       
      ( size * i, size * i + 2)   
    }
    indexSplit = indexSplit.:+(size * i, 10 -1 )
    
    
    indexSplit
    
  
  }
   
  def main(args: Array[String]): Unit = {
    
    
   val res =  splitParam()
   
   println(res)
  }
  
  
}
class ParamAssigner(param: Param) {

  var splitSizeDefault = 3
  def assign() {

  }

  def splitParam(size :Int = splitSizeDefault) {

    
    val groups = param.keys.length / size
    
    
    var i = 0
    var indexSplit = for( i <- 0 until  groups - 1) yield {
       
      ( size * i, size * i + 2)   
    }
    indexSplit = indexSplit.:+(size * i, param.keys.length -1 )
    
    
    
    
  
  }
  
  
  def getAssignedServers() {
    
    
    
  }
}
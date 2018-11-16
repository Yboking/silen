package silen.ml.data


import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class TrainSet {

  private val dataBuf = ArrayBuffer[Array[Double]]();
  private val labelBuf = ArrayBuffer[Double]();
  val attrs :Array[Attr] = null;
  private val labelIndex = 0;
  val labelNames = new mutable.HashMap[String, Double]()


  def fromFile(path :String, separator: String = ",") = {

//    val f = scala.io.Source.fromFile(path)
//    val lines = f.getLines()
//    for(l <- lines){
//
//      println(l)
//    }
    var labelCount = 0;
    for(line <- scala.io.Source.fromFile(path).getLines()){
      val values = line.split(separator);
      if(!labelNames.contains(values(0))){
        labelNames.put(values(0), labelCount)
        labelCount = labelCount + 1
      }
      val temp = new Array[Double](values.length - 1)
      for( i <- 1 until values.length){
        temp(i - 1) = values(i).toDouble;
      }
      dataBuf.append(temp);
      labelBuf.append(labelNames.get(values(0)).get)
    }
    this;
  }

  def record(index : Int) = {
    if(index <0 || index > dataBuf.size - 1){
      //todo throw exception
    }
    dataBuf(index);
  }

  def dimensions = {
    if (dataBuf.size == 0) {
      //todo throw exception
    }
    (dataBuf.size, if (labelBuf == null) dataBuf(0).length - 1 else dataBuf(0).length);
  }

  def data = dataBuf.toArray

  def labels = {
    labelBuf.toArray
  }


}

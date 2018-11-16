package silen.ml.data


import scala.collection.mutable.ArrayBuffer

class TrainSet {

  private val dataSet = ArrayBuffer[Array[Double]]();
  val labelSet :ArrayBuffer[Double] = null;
  val attrs :Array[Attr] = null;

  def fromFile(path :String, separator: String = ",") = {

    for(line <- scala.io.Source.fromFile(path).getLines()){
      dataSet.append(line.split(separator).map(_.toDouble));
    }
  }

  def record(index : Int) = {
    if(index <0 || index > dataSet.size - 1){
      //todo throw exception
    }
    dataSet(index);
  }

  def dimensions = {
    if (dataSet.size == 0) {
      //todo throw exception
    }
    (dataSet.size, if (labelSet == null) dataSet(0).length - 1 else dataSet(0).length);
  }

  def data = dataSet.toArray



}

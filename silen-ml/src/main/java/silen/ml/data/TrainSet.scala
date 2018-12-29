package silen.ml.data


import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class TrainSet {
  //todo
  def getLableIndex(d: Double): Int = {
    0
  }

  //todo
  def splitDataByFeature(findex: Int, selectFeatures: ArrayBuffer[Double]) : (TrainSet, TrainSet) = {

    null

  }


  //todo
  def splitLablesByFeatureValue(i: Int) : Map[Double, Array[Double]]= {

    null
  }
  //todo
  def selectValues(featureIndex: Int) = {

    Array[Double]()
  }


  //todo

  def splitDataByFeature(featureIndex: Int) = {

    Array[(String, TrainSet)]()

  }


  private val dataBuf = ArrayBuffer[Array[Double]]();
  private val labelBuf = ArrayBuffer[Double]();
  val attrs :Array[Attr] = null;
  private val labelIndex = 0;
  val labelNames = new mutable.HashMap[String, Double]()




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

  def splitLablesByFeature(findex: Int): Array[Array[Double]] = {
    //todo
    return null;
  }

  def splitByFeature(fname: String): Array[Array[Double]] = {
    //todo
    return null;
  }


  def size = data.length

  val lableType = 0
  var numOfAttrs = 0

}

object TrainSet{

  def fromFile(path :String, separator: String = ",") = {


    val train = new TrainSet()
    var labelCount = 0;
    for(line <- scala.io.Source.fromFile(path).getLines()){
      val values = line.split(separator);
      if(!train.labelNames.contains(values(0))){
        train.labelNames.put(values(0), labelCount)
        labelCount = labelCount + 1
      }
      val temp = new Array[Double](values.length - 1)
      for( i <- 1 until values.length){
        temp(i - 1) = values(i).toDouble;
      }
      if(temp.length > train.numOfAttrs){
        train.numOfAttrs = temp.length
      }
      train.dataBuf.append(temp);
      train.labelBuf.append(train.labelNames.get(values(0)).get)
    }
    train
  }
}
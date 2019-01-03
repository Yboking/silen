package silen.ml.data


import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


class TrainSet() {
  def produceChild() = {
    this
  }

  val options = new ArrayBuffer[Opt]()
  private val dataBuf = ArrayBuffer[Array[Double]]();
  private val labelBuf = ArrayBuffer[Double]();
  val attrs :Array[Attr] = null;
  private val labelIndex = 0;
  val labelNames = new mutable.HashMap[String, Double]()
  val lableType = 0
  var numOfAttrs = 0


  def addOpt(opt: SelectDataOpt) = {
    this.options.append(opt)
    this
  }

  def dataBuffer = this.dataBuf

  def selectData(findex: Int, featureValues: Array[Double]) = {

    val ts = this.produceChild()
    ts.addOpt(SelectDataOpt(findex, featureValues))
  }

  def splitDataByFeature(findex: Int, selectFeatures: (Array[Double], Array[Double])) = {

    (this, this)
  }





  def selectValues(_1: Array[Double]) = {
    this
  }

  def featureValues(i: Int) = {
    val set = scala.collection.mutable.HashSet[Double]()
    for (elem <- this.dataBuf) {
      set.add(elem(i))
    }
    set.toArray
  }

  def splitByFeature(i: Int) = {

    this
  }

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



  def labels = {
    if(options.isEmpty){
      labelBuf
    }else{

      val tempLabelBuf = new ArrayBuffer[Double]()

      for (opt <- options) {
        opt match {

          case SelectDataOpt(findex, featureValues) =>{

            var i =0
            dataBuf.iterator.foreach( record =>{
              if(featureValues.contains(record(findex))){

                tempLabelBuf.append(labelBuf(i))
                i = i + 1
              }
            })
          }
          case _ =>{
            throw  new Exception(s"not support opt $opt now ")
          }
        }
      }
      tempLabelBuf

    }
  }

  def splitLablesByFeature(findex: Int): Array[ArrayBuffer[Double]] = {
    //todo
    return null;
  }

  def splitByFeature(fname: String): Array[Array[Double]] = {
    //todo
    return null;
  }


  def size = {
    if(options.size == 0){
      dataBuf.length
    }else{
      var tempSize = 0
      for (opt <- options) {
        opt match {
          case SelectDataOpt(findex, featureValues) =>{
            dataBuf.iterator.foreach( record =>{
              if(featureValues.contains(record(findex))){
                tempSize = tempSize + 1
              }
            })
          }
          case _ =>{
            throw  new Exception(s"not support opt $opt now ")
          }
        }
      }
      tempSize
    }
  }


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



class Opt{
}

case class SelectDataOpt(findex: Int, featureValues: Array[Double]) extends Opt{

}
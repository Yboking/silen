package silen.ml.data


import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}


case class TrainSet(dataBuffer :ArrayBuffer[Array[Double]], labelBuffer :ArrayBuffer[Double]) {
  def produceChild() = {
    val child = new TrainSet(this.dataBuffer, this.labelBuffer)
    child.numOfAttrs = this.numOfAttrs
    child.options.appendAll(this.options)
    child
  }
  def this() = this(null, null)

  val options = new ArrayBuffer[Opt]()
//  private val dataBuf = ArrayBuffer[Array[Double]]();
//  private val labelBuf = ArrayBuffer[Double]();
  val attrs :Array[Attr] = null;
  private val labelIndex = 0;
  val labelNames = new mutable.HashMap[String, Double]()
  var numOfAttrs = 0


  def addOpt(opt: SelectDataOpt) = {
    this.options.append(opt)
    this
  }

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
    if(options.isEmpty){
      for (elem <- this.dataBuffer) {
        set.add(elem(i))
      }

    }else {
      for (opt <- options) {
        opt match {
          case SelectDataOpt(findex, featureValues) => {
            dataBuffer.iterator.foreach(record => {
              if (featureValues.contains(record(findex))) {
                set.add(record(findex))
              }
            })
          }
          case _ => {
            throw new Exception(s"not support opt $opt now ")
          }
        }
      }
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

  def splitDataByFeature(featureIndex: Int) = {

    Array[(String, TrainSet)]()

  }



  def record(index : Int) = {
    if(index <0 || index > dataBuffer.size - 1){
      //todo throw exception
    }
    dataBuffer(index);
  }

  def dimensions = {
    if (dataBuffer.size == 0) {
      //todo throw exception
    }
    (dataBuffer.size, if (labelBuffer == null) dataBuffer(0).length - 1 else dataBuffer(0).length);
  }


  def mergeOptions(options: ArrayBuffer[Opt]) = {
    val rtn = new ArrayBuffer[Opt]
    val iterator = options.iterator
    rtn.append(iterator.next())
    while(iterator.hasNext){
      val curOpt = iterator.next()
      for (i <- 0 until rtn.length) {
        if(rtn(i).equals(curOpt)){
          val merged = rtn(i).merge(curOpt)
          rtn.remove(i)
          rtn.insert(i, merged)
        }
      }
    }
    rtn
  }

  def labels = {
    if(options.isEmpty){
      labelBuffer
    }else{

      val tempLabelBuf = new ArrayBuffer[Double]()
      val newOptions = mergeOptions(options)
      for (opt <- newOptions) {
        opt match {
          case SelectDataOpt(filterFeatures :Seq[(Int, Array[Double])]) =>{
            var i =0
            dataBuffer.foreach( record =>{
              val filterValue = filterFeatures.forall(param =>{
                param._2.contains(record(param._1))
              })
              if(filterValue){
                tempLabelBuf.append(labelBuffer(i))
              }
              i = i + 1
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


  def size = {
    if(options.size == 0){
      dataBuffer.length
    }else{
      var tempSize = 0
      val newOptions = mergeOptions(options)
      for (opt <- newOptions) {
      for (opt <- options) {
        opt match {
          case SelectDataOpt(findex, featureValues) =>{
            dataBuffer.iterator.foreach( record =>{
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

    val train = new TrainSet(new ArrayBuffer[Array[Double]](), new ArrayBuffer[Double])
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
      train.dataBuffer.append(temp);
      train.labelBuffer.append(train.labelNames.get(values(0)).get)
    }
    train
  }
}



abstract  class Opt{
  def merge(curOpt: Opt): Opt
  def equals(target :Opt) :Boolean

}

case class SelectDataOpt(filterFeatures :(Int, Array[Double]) * ) extends Opt{
  val name = "select"
  override def merge(target: Opt): Opt = {

    val selectOpt = target.asInstanceOf[SelectDataOpt]
    val curParams = this.filterFeatures
    val newParams = selectOpt.filterFeatures
    SelectDataOpt(curParams.++(newParams))
  }

  override def equals(target: Opt): Boolean = {
    val selectOpt = target.asInstanceOf[SelectDataOpt]
    selectOpt.name.equals(this.name)
  }
}
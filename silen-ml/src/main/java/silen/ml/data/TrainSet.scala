package silen.ml.data


import silen.ml.classification.{ContiValue, DiscreteValue, FeatureValue}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


case class TrainSet(private val dataBuffer :ArrayBuffer[Array[Double]], private val labelBuffer :ArrayBuffer[Double]) {
  def selectData(findex :Int, featureValues :Array[FeatureValue]) = {

    val ts = this.produceChild()
    ts.addOpt(SelectDataOpt((findex, featureValues)))

  }


  def featureIgnored(i: Int) = {
    ignoreFeature.contains(i)
  }

  def addIgnoreFeature(findex: Int) = {
    ignoreFeature.append(findex)
  }

  def produceChild() = {
    val child = new TrainSet(this.dataBuffer, this.labelBuffer)
    child.numOfAttrs = this.numOfAttrs
    child.options.appendAll(this.options)
    child.ignoreFeature = this.ignoreFeature.clone()
    child.attrs = this.attrs
    child
  }
  def this() = this(null, null)

  val options = new ArrayBuffer[Opt]()
//  private val dataBuf = ArrayBuffer[Array[Double]]();
//  private val labelBuf = ArrayBuffer[Double]();
  var attrs :Array[Attr] = null;
  private val labelIndex = 0;
  val labelNames = new mutable.HashMap[String, Double]()
  var numOfAttrs = 0

  var ignoreFeature = ArrayBuffer[Int]()
  def addOpt(opt: SelectDataOpt) = {
    this.options.append(opt)
    this
  }

  def selectData(findex: Int, featureValues: Array[Double]) = {
    val ts = this.produceChild()
    ts.addOpt(new SelectDataOpt(findex, featureValues))
  }

  def splitDataByFeature(findex: Int, selectFeatures: (Array[Double], Array[Double])) = {

    (this, this)
  }





  def selectValues(_1: Array[Double]) = {
    this
  }

  val discreteNum = 6

  def featureValues(i: Int) :Array[FeatureValue] = {

    val set = scala.collection.mutable.Set[Double]()
    if (options.isEmpty) {
      for (elem <- this.dataBuffer) {
        set.add(elem(i))
      }
    } else {
      val newOptions = mergeOptions(options)
      for (opt <- newOptions) {
        opt match {
          case selectOpt: SelectDataOpt => {
            val filterFeatures = selectOpt.filterFeatures
            dataBuffer.foreach(record => {
              val filterValue = filterFeatures.forall(param => {
                param._2.contains(record(param._1))
              })

              if (filterValue) {
                set.add(record(i))
              }
            })

          }
          case _ => {
            throw new Exception(s"not support opt $opt now ")
          }
        }
      }
    }
    if (this.attrs(i).ftype == 0) {
      return set.toArray.map(x => DiscreteValue(x))
    }else if(this.attrs(i).ftype == 1) {

      if (set.size < discreteNum) {
        return set.toArray.map(x => DiscreteValue(x))
      }
      val sortedValues = set.toArray.sorted
      val range = sortedValues.size / discreteNum
      val rtn = ArrayBuffer[ContiValue]()
      var index = 0
      var from = 0
      var to = 0
      while (index < discreteNum) {
        from = index * range
        to = (index + 1) * range - 1
        rtn.append(ContiValue(sortedValues(from), sortedValues(to)))
        index = index + 1
      }
      from = index * range
      to = (index + 1) * range - 1

      if (from <= sortedValues.length - 1 && sortedValues.length - 1 <= to) {
        rtn.append(ContiValue(sortedValues(from), sortedValues(sortedValues.length - 1)))
      }
      return rtn.toArray
    }
    null
  }


  //todo

  def splitDataByFeature(featureIndex: Int) = {

    Array[(String, TrainSet)]()

  }


  def getAllRecords() = this.dataBuffer

  //todo put the child's data in memory
  def getRecord(index : Int) :Array[Double] = {
//    if(index <0 || index > dataBuffer.size - 1){
//      //todo throw exception
//    }

    if(options.isEmpty){
      dataBuffer(index);
    }else{

      val newOptions = mergeOptions(options)
      for (opt <- newOptions) {
        opt match {
          case selectOpt :SelectDataOpt =>{
            val filterFeatures = selectOpt.filterFeatures
            var i =0
            dataBuffer.foreach( record =>{
              val filterValue = filterFeatures.forall(param =>{
                param._2.contains(record(param._1))
              })
              if(filterValue && i == index){
                return record
              }else if(filterValue){
                i = i + 1
              }
            })
          }
          case _ =>{
            throw  new Exception(s"not support opt $opt now ")
          }
        }
      }
      throw new Exception(s"find no record by index: $index")
    }
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
          case selectOpt :SelectDataOpt =>{
            val filterFeatures = selectOpt.filterFeatures
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
        opt match {
          case selectOpt :SelectDataOpt =>{
            val filterFeatures = selectOpt.filterFeatures
            dataBuffer.foreach(record =>{
              val filterValue = filterFeatures.forall(param =>{
                param._2.contains(record(param._1))
              })
              if(filterValue){
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

  def fromFile(path :String, separator: String = ",", attTypes :Array[Int] = null) = {

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

    train.attrs =
    if(attTypes == null){
       Array.fill(train.numOfAttrs)(new Attr(null, 0))
    }else if(attTypes.length != train.numOfAttrs){
      throw  new Exception(s"Attribute num ${train.attrs.length} not match  parameter attTypes ${attTypes.length} ")
    }else{
       attTypes.map( v => new Attr(null, v))
    }
    train
  }
}



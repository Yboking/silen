package silen.ml.classification
import silen.ml.data.TrainSet

import scala.collection.mutable.{ArrayBuffer, HashMap}
import silen.ml.math.func.Functions._

import scala.collection.mutable


class ID3 {

  def entropy(dataSet: Array[Double]) = {
    val container = HashMap[Double, Int]()
    dataSet.foreach(e => {
      container.put(e, container.getOrElse(e, 0) + 1)
    })

    val size = dataSet.length.toDouble
    - container.map(kv => {
      val p = kv._2 / size
      p * log2(p)
    }
    ).reduce(_ + _)
  }

  def fit(trainSet: TrainSet) = {

    val initEntropy = initEntropy(trainSet.labels);
    val size = trainSet.size.toDouble
    var infoInc = 0.0
    var featureIndex = 0
    for (i <- 0 until trainSet.numOfAttrs) {
      val tmpTrainSet = trainSet.splitLablesByFeature(i)
      val entropyByFeature = tmpTrainSet.map( sub =>  ((sub.length / size) *  initEntropy(sub))
                     ).reduce(_+_)
      if(initEntropy - entropyByFeature > infoInc){
        infoInc  = initEntropy - entropyByFeature
        featureIndex = i
      }
    }

    val tree = new TNode(featureIndex)

    val fValues = trainSet.selectValues(featureIndex)
    val newTrainSet = trainSet.splitDataByFeature(featureIndex)

    fValues.zip(newTrainSet).foreach( t =>{
      tree.addChild(t._1, fit(t._2))

    })





  }
}

class TNode(index: Int){
  val children  = mutable.Map[String, TNode]()

  def getChild(value: String) = {
      children.get(value).get
  }

  def addChild(value: String, child: TNode) = {
    children.put(value, child)
  }

}

class DTree{
  val nodeIndex = HashMap[String, Int]()
  val nodes = ArrayBuffer[Array[Int]]()
  var numOfNode = 0


  def addNode(parentName:String, nodeName: String, valueSet: Array[Double]) = {


    if(parentName == null){


    }

    if(nodeIndex.get(nodeName) == null){

      nodeName
    }
  }
}
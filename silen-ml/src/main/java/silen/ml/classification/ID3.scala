package silen.ml.classification
import silen.ml.data.TrainSet
import silen.ml.math.func.Functions._

import scala.collection.mutable.{ArrayBuffer, HashMap}


class ID3 {


  def entropy(dataSet: ArrayBuffer[Double]) = {
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

  def fit(trainSet: TrainSet) :TNode = {
    val initEntropy = entropy(trainSet.labels);
    val size = trainSet.size.toDouble
    var infoInc = 0.0
    var featureIndex = 0
    for (i <- 0 until trainSet.numOfAttrs) {
      val tmpTrainSet = trainSet.splitLablesByFeature(i)
      val entropyByFeature = tmpTrainSet.map( sub =>  ((sub.length / size) *  entropy(sub))
                     ).reduce(_+_)
      if(initEntropy - entropyByFeature > infoInc){
        infoInc  = initEntropy - entropyByFeature
        featureIndex = i
      }
    }

    val tree = new TNode(featureIndex)
    val newTrainSet = trainSet.splitDataByFeature(featureIndex)
    newTrainSet.foreach( t =>{
      if(t._2.labels.distinct.length == 1){
        tree.addChild(t._1, new TNode().setLabelIndex(t._2.labels(0).toInt))

      }else{
        tree.addChild(t._1, fit(t._2))
      }
    })
    tree
  }
}

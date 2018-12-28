package silen.ml.classification

import silen.ml.data.TrainSet
import silen.ml.math.func.Functions._

import scala.collection.mutable.{ArrayBuffer, HashMap}


class CartTree {

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



  //todo
  def buildCombineGroups(keySet: Set[Double]):Iterator[(Array[Double], Array[Double])] = {
    null

  }


  def getGiniIndex(labels: ArrayBuffer[Double] *) = {
    0.0
  }

  def getGiniIndex(labels: Array[Double]) = {

    0.0

  }

  def fit(trainSet: TrainSet): CartNode = {

//    if(trainSet.labels.distinct.length == 1){
//      val node = new CartNode().setLabelIndex(trainSet.labels)
//    }
    //    if(trainSet.lableType == 0) {
    var impurity = getGiniIndex(trainSet.labels)
    var selectFeatures: ArrayBuffer[Double] = null
    var findex = 0
    var tempLablesMap: Map[Double, Array[Double]] = null
    for (i <- 0 until trainSet.numOfAttrs) {
      tempLablesMap = trainSet.splitLablesByFeatureValue(i)
      val combinedGroups = buildCombineGroups(tempLablesMap.keySet);
      combinedGroups.foreach(com => {
        val firstGroup = new ArrayBuffer[Double]
        val secondGroup = new ArrayBuffer[Double]
        for (elem <- com._1) {
          firstGroup.append(tempLablesMap.get(elem).get: _*)
        }
        for (elem <- com._2) {
          secondGroup.append(tempLablesMap.get(elem).get: _*)
        }
        val temp = getGiniIndex(firstGroup, secondGroup)
        if (temp < impurity) {
          impurity = temp
          selectFeatures = firstGroup
          findex = i
        }
      })
    }
    val node = new CartNode().setFeaturIndex(findex)

    val (leftData, rightData) = trainSet.splitDataByFeature(findex, selectFeatures)

    val lnode = fit(leftData)
    val rnode = fit(rightData)
    node.setLeft(lnode)
    node.setRight(rnode)
    node
  }
}

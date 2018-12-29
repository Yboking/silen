package silen.ml.classification

import silen.ml.data.TrainSet

import scala.collection.mutable.ArrayBuffer


class CartTree {


  //todo
  def buildCombineGroups(keySet: Set[Double]): Iterator[(Array[Double], Array[Double])] = {
    null

  }


  def getGiniIndex(labels: ArrayBuffer[Double]*) = {
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
      tempLablesMap = trainSet.selectValues(i)
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

    if (leftData.labels.distinct.length == 1) {
      val temp = new CartNode()
      temp.setLabelIndex(leftData.getLableIndex(leftData.labels(0)))
      temp.setValue(selectFeatures)
      node.setLeft(temp)
    } else {

      val temp = fit(leftData)
      temp.setValue(selectFeatures)
      node.setLeft(temp)
    }

    if (rightData.labels.distinct.length == 1) {
      val temp = new CartNode()
      temp.setLabelIndex(rightData.getLableIndex(rightData.labels(0)))
      temp.setValue(trainSet.selectValues(findex).intersect(selectFeatures))
      node.setLeft(temp)
    } else {
      val temp = fit(rightData)
      temp.setValue(trainSet.selectValues(findex).intersect(selectFeatures))
      node.setRight(temp)
    }
    node
  }
}

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


  def buildCombineGroups(trainSet: TrainSet, findex: Int) = {
    val featureValues = trainSet.selectValues(findex)


  }

  def buildCombineGroups(fvalues: Array[Double]) = {

    Array[(Array[Double], Array[Double])]()
  }

  def getGiniIndex(firstGroup: TrainSet, secondGroup: TrainSet) = {
    0.0
  }

  def fit(trainSet: TrainSet): CartNode = {

    var impurity = getGiniIndex(trainSet.labels)
    var selectFeatures: (Array[Double], Array[Double]) = null
    var findex = 0
    for (i <- 0 until trainSet.numOfAttrs) {
      val splitTrainSet  = trainSet.splitByFeature(i);
      val fvalues = splitTrainSet.featureValues(i)

      val combinedGroups = buildCombineGroups(fvalues);

      combinedGroups.foreach(group => {

        val firstGroup = splitTrainSet.selectValues(group._1)
        val secondGroup = splitTrainSet.selectValues(group._2)

        val temp = getGiniIndex(firstGroup, secondGroup)
        if (temp < impurity) {
          impurity = temp
          selectFeatures = group
          findex = i
        }
      })
    }
    val node = new CartNode().setFeaturIndex(findex)
    val (leftData, rightData) = trainSet.splitDataByFeature(findex, selectFeatures)

    if (leftData.labels.distinct.length == 1) {
      val temp = new CartNode()
      temp.setLabelIndex(leftData.getLableIndex(leftData.labels(0)))
      temp.setValue(selectFeatures._1)
      node.setLeft(temp)
    } else {

      val temp = fit(leftData)
      temp.setValue(selectFeatures._1)
      node.setLeft(temp)
    }

    if (rightData.labels.distinct.length == 1) {
      val temp = new CartNode()
      temp.setLabelIndex(rightData.getLableIndex(rightData.labels(0)))
      temp.setValue(selectFeatures._2)
      node.setLeft(temp)
    } else {
      val temp = fit(rightData)
      temp.setValue(selectFeatures._2)
      node.setRight(temp)
    }
    node
  }
}

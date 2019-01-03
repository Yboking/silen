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

    val map = scala.collection.mutable.HashMap[Double, Int]()
    for (elem <- labels) {
      map.put(elem, map.getOrElse(elem, 0) + 1)
    }
    map.map( kv => (kv._2 / labels.length.toDouble) * ( 1.0 - kv._2 / labels.length.toDouble)).sum
  }


  def buildCombineGroups(trainSet: TrainSet, findex: Int) = {
    val featureValues = trainSet.selectValues(findex)


  }

  def buildCombineGroups(fvalues: Array[Double]) = {

    val first = ArrayBuffer[Double]()
    val rtn = new Array[(Array[Double], Array[Double])](fvalues.length)
    var i = 0;
    for (f1 <- fvalues) {
      for (f2 <- fvalues) {
        if (f1 != f2) {
          first.append(f2)
        }
      }
      rtn(i) = (Array(f1), first.toArray)
      i = i + 1
    }
    rtn
  }

  def getGiniIndex(firstGroup: TrainSet, secondGroup: TrainSet) = {

    val size1 = firstGroup.size
    val size2 = secondGroup.size
    val gini1 = getGiniIndex(firstGroup.labels)
    val gini2 = getGiniIndex(secondGroup.labels)
    gini1 * (size1 / (size1 + size2).toDouble) + gini2 * (size2 / (size1 + size2).toDouble)
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

        val firstGroupTrain = splitTrainSet.selectData(group._1)
        val secondGroupTrain = splitTrainSet.selectData(group._2)

        val temp = getGiniIndex(firstGroupTrain, secondGroupTrain)
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

package silen.ml.classification

import silen.ml.data.TrainSet

import scala.collection.mutable.ArrayBuffer


class CartTree {


  def getGiniIndex(labels: ArrayBuffer[Double]) = {
    val map = scala.collection.mutable.HashMap[Double, Int]()
    for (elem <- labels) {
      map.put(elem, map.getOrElse(elem, 0) + 1)
    }
    map.map( kv => (kv._2 / labels.length.toDouble) * ( 1.0 - kv._2 / labels.length.toDouble)).sum
  }

  def getGiniIndex(firstGroup: TrainSet, secondGroup: TrainSet) :Double = {

    val size1 = firstGroup.size
    val size2 = secondGroup.size
    val gini1 = getGiniIndex(firstGroup.labels)
    val gini2 = getGiniIndex(secondGroup.labels)
    gini1 * (size1 / (size1 + size2).toDouble) + gini2 * (size2 / (size1 + size2).toDouble)
  }

  def buildCombineGroups(fvalues: Array[Double]) = {
    val rtn = new Array[(Array[Double], Array[Double])](fvalues.length)
    var i = 0;
    for (f1 <- fvalues) {
      val singleGroup = ArrayBuffer[Double]()
      for (f2 <- fvalues) {
        if (f1 != f2) {
          singleGroup.append(f2)
        }
      }
      rtn(i) = (Array(f1), singleGroup.toArray)
      i = i + 1
    }
    rtn
  }


  def buildCombineGroups(fvalues: Array[FeatureValue]) = {
    if (fvalues.length < 2) {
      throw new Exception(s"can not divicde to 2 subTree, fvalues length is ${fvalues.length} ")
    }
    val rtn = new Array[(Array[FeatureValue], Array[FeatureValue])](fvalues.length - 1)
    for (i <- 0 until fvalues.length - 1) yield {
      var j = 0
      val first = new Array[FeatureValue](i + 1)
      val two = new Array[FeatureValue](fvalues.length - first.length)
      for (j <- 0 to i) {
        first(j) = fvalues(j)
      }
      for (j <- first.length until fvalues.length) {
        two(j - first.length) = fvalues(j)
      }
      rtn(i) = (first, two)
    }
    rtn
  }

  def needGrow(trainSet: TrainSet): Boolean = {

    if(trainSet.labels.distinct == 1){
      return false
    }

    return true

  }

  def fit(trainSet: TrainSet): CartNode = {

    var impurity = getGiniIndex(trainSet.labels)
    var increment = 0.0
    var selectFeatures: (Array[FeatureValue], Array[FeatureValue]) = null
    var findex = 0
    for (i <- 0 until trainSet.numOfAttrs if(!trainSet.featureIgnored(i))) {
      val fvalues = trainSet.featureValues(i)
        val combinedGroups = buildCombineGroups(fvalues);
        combinedGroups.foreach(group => {
        val firstGroupTrain = trainSet.selectData(i, group._1)
        val secondGroupTrain = trainSet.selectData(i, group._2)

        val temp = getGiniIndex(firstGroupTrain, secondGroupTrain)
        if (impurity - temp > increment) {
          increment = impurity - temp
          selectFeatures = group
          findex = i
        }
      })
    }

    trainSet.addIgnoreFeature(findex)
    val node = new CartNode().setFeaturIndex(findex)
    val leftData = trainSet.selectData(findex, selectFeatures._1)
    val rightData = trainSet.selectData(findex, selectFeatures._2)

    for( tempData <- Seq(leftData, rightData)){

      val nodeValues = leftData.getFilterValues()
      if(!needGrow(tempData)){
        val temp = new CartNode()
        val tempLabels = tempData.labels
        temp.setLabel(tempLabels(0))
        temp.setValue(selectFeatures._1)
        node.setLeft(temp)

      }else{
        val temp = fit(tempData)
        temp.setValue(selectFeatures._1)
        node.setLeft(temp)

      }

    }


   val tempLabels = rightData.labels
    if (tempLabels.distinct.length == 1) {
      val temp = new CartNode()
      temp.setLabel(tempLabels(0))
      temp.setValue(selectFeatures._2)
      node.setRight(temp)
    } else {
      val temp = fit(rightData)
      temp.setValue(selectFeatures._2)
      node.setRight(temp)
    }
    node
  }
}

package silen.ml.classification

import silen.ml.data.TrainSet

import scala.collection.mutable.ArrayBuffer

class CartNode extends TNode {

  def isLeafNode() = this.left == null && this.right == null
  def printValue() = {
    val v = s"${this.featureIndex}:[${this.value}]"
    printf(v)
  }

  def print(path : ArrayBuffer[CartNode]) : Unit = {
    if(!isLeafNode()) {
      path.append(this)
      val newPath = path.clone()
      this.left.print(path)
      this.right.print(newPath)
    }else {
      path.foreach(node =>{
        printValue()
      })
    }
  }


  def setRight(rnode: CartNode) = {
   this.right = rnode
  }

  def setLeft(lnode: CartNode) = {
    this.left = lnode
  }

  def setFeaturIndex(findex: Int) = {
    this.featureIndex = findex
    this
  }

  var featureIndex = -1;
  var left: CartNode = null;
  var right: CartNode = null;



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

    if(trainSet.labels.distinct.length == 1){
      return false
    }

    if(trainSet.ignoreFeature.distinct.size == trainSet.numOfAttrs){
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
    createLeftTree(node, leftData)
    createRightTree(node, rightData)
    node
  }



  def createLeftTree(node: CartNode, trainSet: TrainSet) = {
    val child = createChildNode(trainSet)
    node.setLeft(child)
  }

  def createRightTree(node: CartNode, trainSet: TrainSet) = {
    val child = createChildNode(trainSet)
    node.setRight(child)
  }

  def createChildNode(trainSet: TrainSet) :CartNode = {
    val nodeValues = trainSet.getFilterValues()
    val temp = if(!needGrow(trainSet)){

      val tempLabels = trainSet.labels
      val node = new CartNode()
      node.setLabel( tempLabels(0) ).asInstanceOf[CartNode]
    }else{
      fit(trainSet)
    }
    temp.setValue(nodeValues)
    temp
  }

}

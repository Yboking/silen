package silen.ml.classification

class CartNode extends TNode {

  def setValue(selectFeatures: Any) = {
    this.value = selectFeatures
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
  var value: Any = null
}

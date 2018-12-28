package silen.ml.classification

import scala.collection.mutable.ArrayBuffer

class CartNode extends TNode {
  def setValue(selectFeatures: Any) = {


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
}

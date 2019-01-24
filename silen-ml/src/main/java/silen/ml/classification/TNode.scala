package silen.ml.classification

import scala.collection.mutable

class TNode(index: Int = -1){
  var labelIndex = -1.0;
  val children  = mutable.Map[String, TNode]()
  var value: Any = null
  def getChild(value: String) = {
    children.get(value).get
  }


  def addChild(value: String, child: TNode) = {
    children.put(value, child)
  }
  def setLabel(label :Double) ={
    this.labelIndex = label
    this
  }


  def setValue(selectFeatures: Any) = {
    this.value = selectFeatures
  }

}
package silen.ml.classification

import scala.collection.mutable

class TNode(index: Int = -1){
  var labelIndex = -1;
  val children  = mutable.Map[String, TNode]()

  def getChild(value: String) = {
    children.get(value).get
  }


  def addChild(value: String, child: TNode) = {
    children.put(value, child)
  }
  def setLabelIndex(lIndex: Int) ={
    this.labelIndex = lIndex
    this
  }

}
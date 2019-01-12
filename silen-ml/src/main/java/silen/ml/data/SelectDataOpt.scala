package silen.ml.data

import silen.ml.classification.{DiscreteValue, FeatureValue}


case class SelectDataOpt(filterFeatures :(Int, Array[FeatureValue]), more :(Int, Array[FeatureValue])* ) extends Opt{
  val name = "select"

  def this(disFeatures :(Int, Array[Double])) = {
    this((disFeatures._1, disFeatures._2.map(x => DiscreteValue(x))))
  }
  override def merge(target: Opt): Opt = {
    val selectOpt = target.asInstanceOf[SelectDataOpt]
    val curParams = this.filterFeatures
    val newParams = selectOpt.filterFeatures
    SelectDataOpt( curParams, newParams)
  }

  override def equals(target: Opt): Boolean = {
    val selectOpt = target.asInstanceOf[SelectDataOpt]
    selectOpt.name.equals(this.name)
  }
}
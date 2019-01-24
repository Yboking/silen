package silen.ml.data

import silen.ml.classification.{ContiValue, DiscreteValue, FeatureValue}


case class SelectDataOpt(filterFeatures : (Int, Array[FeatureValue]) * ) extends Opt{
  val name = "select"

  def this(findex :Int, values :Array[Double]) = {
    this((findex, values.map( x => DiscreteValue(x))))
  }

  def this(findex :Int, from :Double, to :Double) = {
    this((findex, Array(ContiValue(from, to))))
  }
  override def merge(target: Opt): Opt = {
    val selectOpt = target.asInstanceOf[SelectDataOpt]
    val curParams = this.filterFeatures
    val newParams = selectOpt.filterFeatures
    SelectDataOpt(curParams.union(newParams) :_*)
  }

  override def equals(target: Opt): Boolean = {
    val selectOpt = target.asInstanceOf[SelectDataOpt]
    selectOpt.name.equals(this.name)
  }

  override def getOptionValues(): Seq[FeatureValue] = {

      this.filterFeatures.map( t=>{
      t._2

    }).flatMap(x => x)
  }
}

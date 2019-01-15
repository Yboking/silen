package silen.ml.data

import silen.ml.classification.{DiscreteValue, FeatureValue}


case class SelectDataOpt(filterFeatures : Seq[(Int, Array[FeatureValue])] ) extends Opt{
  val name = "select"

  def this(disFeatures: (Int, Array[Double])) = {

    this(
//      Seq((disFeatures._1, disFeatures._2.map(x => DiscreteValue(x))))
//      Seq((1, Array(new DiscreteValue(0))))
      Seq(null)
     )
//    this(Seq((disFeatures._1, disFeatures._2.map(x => DiscreteValue(x)))))
    //    this(Seq(null))
  }
  override def merge(target: Opt): Opt = {
    val selectOpt = target.asInstanceOf[SelectDataOpt]
    val curParams = this.filterFeatures
    val newParams = selectOpt.filterFeatures
//    SelectDataOpt(curParams.union(newParams))
    this
  }

  override def equals(target: Opt): Boolean = {
    val selectOpt = target.asInstanceOf[SelectDataOpt]
    selectOpt.name.equals(this.name)
  }
}

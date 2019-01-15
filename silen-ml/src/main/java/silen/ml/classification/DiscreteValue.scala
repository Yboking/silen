package silen.ml.classification

case class DiscreteValue(value :Double) extends FeatureValue {


  override def equals (target: Any): Boolean = {
      value == target
  }
}

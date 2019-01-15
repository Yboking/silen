package silen.ml.classification

case class DiscreteValue(value :Double) extends FeatureValue {
  override def equalTo(target: Any): Boolean = {

    if(target == null){
      false
    }else{
      target.toString.toDouble == value
    }

  }
}

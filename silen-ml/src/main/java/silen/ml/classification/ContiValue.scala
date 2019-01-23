package silen.ml.classification


/**
  *  continuous value [from, to)
  * @param from
  * @param to
  */
case class ContiValue(from :Double, to :Double) extends FeatureValue {
  override def equals(target: Any): Boolean = {

    if(target == null ){
      false
    }else{
      val targetValue = target.toString.toDouble
     return from <= targetValue && targetValue <= to
    }
  }
}

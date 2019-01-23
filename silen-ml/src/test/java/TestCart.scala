import silen.ml.classification.{CartTree, DiscreteValue, FeatureValue}
import silen.ml.data.{SelectDataOpt, TrainSet}

object TestCart {

  def main(args: Array[String]): Unit = {
    val opt = SelectDataOpt((1, Array(new DiscreteValue(0))))
    val train = TrainSet.fromFile("data/iris.csv",separator = ",", Array(1,1,1,1))
//
//    for (elem <- train.featureValues(1)) {
//
//      println(elem)
//    }
    val cart = new CartTree()
    val tree = cart.fit(train)
    println(train)

  }
}

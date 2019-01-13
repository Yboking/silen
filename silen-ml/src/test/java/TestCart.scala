import silen.ml.classification.{CartTree, DiscreteValue, FeatureValue}
import silen.ml.data.{SelectDataOpt, TrainSet}

object TestCart {

  def main(args: Array[String]): Unit = {


    val opt = SelectDataOpt(Seq((1, Array(DiscreteValue(0.2)))))
    val train = TrainSet.fromFile("data/iris.csv")

    val cart = new CartTree()
    val tree = cart.fit(train)
    println(train)

   new  SelectDataOpt((1, Array(1.0)))
  }
}

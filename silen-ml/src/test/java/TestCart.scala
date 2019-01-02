import silen.ml.classification.CartTree
import silen.ml.data.TrainSet

object TestCart {


  def main(args: Array[String]): Unit = {


    val train = TrainSet.fromFile("data/iris.csv")

    val cart = new CartTree()
    val tree = cart.fit(train)


    println(train)
  }
}
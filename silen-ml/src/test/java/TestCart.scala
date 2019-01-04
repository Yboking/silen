import silen.ml.classification.CartTree
import silen.ml.data.TrainSet

import scala.collection.mutable

object TestCart {

  def main(args: Array[String]): Unit = {




   println(Array(5.1,3.8,1.6,0.2).eq(Array(5.1,3.8,1.6,0.2)))
   println(Array(5.1,3.8,1.6,0.2) == (Array(5.1,3.8,1.6,0.2)))
   println(Array(5.1,3.8,1.6,0.2).equals(Array(5.1,3.8,1.6,0.2)))
//    val train = TrainSet.fromFile("data/iris.csv")
//
//    val cart = new CartTree()
//    val tree = cart.fit(train)
//
//
//    println(train)
  }
}

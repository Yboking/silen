package silen.ml.common

import silen.ml.data.StaticLink

object SampleUtil {

  /**
   * no repeat randomSequence
   */
  def randomSequence(size: Int) = {

    val sl = new StaticLink(size)
    val seq = new Array[Int](size)
    var index = 0

    while (index < seq.length) {

      val selectIndex = sl.remove(defaultRandom.nextInt(sl.getSize()) + 1)

      seq(index) = selectIndex
      index = index + 1

    }
    seq
  }

  def main(args: Array[String]): Unit = {
    println("start")
    randomSequence2(10000000)
    println("end")
    
//    println(Array(3,1,35,2).slice(0, 4).mkString(","))
  }
  
  def randomSequence2(size: Int) = {

    val set = scala.collection.mutable.HashSet[Int]()
    while (set.size < size) {
      set.add(defaultRandom.nextInt(size) )
    }
    set.toArray

  }

  val defaultRandom = new java.util.Random()

  def randomSequence(size: Int, seed: Long) {

  }

}
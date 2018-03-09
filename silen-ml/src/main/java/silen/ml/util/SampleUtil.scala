package silen.ml.util

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

    val size = 100000
    var s = System.currentTimeMillis()

    val sl = new StaticLink(size)

    while (sl.getSize() > 0) {

      sl.remove(defaultRandom.nextInt(sl.getSize()) + 1)
    }
    
    println(System.currentTimeMillis() - s)

//    randomSequence(size)
//    println(System.currentTimeMillis() - s)

    s = System.currentTimeMillis()
    val set = scala.collection.mutable.HashSet[Int]()
    while (set.size < size) {
      set.add(defaultRandom.nextInt(size))
    }
    set.toArray
    println(System.currentTimeMillis() - s)

    //

    //    println(randomSequence(10000).mkString(","))

    //    val fs  = new FastStaticLink(8)
    //    println(fs)
    //    println(fs.remove(3))
    //    println(fs.remove(5))
    //    println(fs.remove(2))
    //    println(fs.remove(2))

  }

  val defaultRandom = new java.util.Random()

  def randomSequence(size: Int, seed: Long) {

  }

}
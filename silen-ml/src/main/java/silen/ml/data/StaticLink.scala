package silen.ml.data

class StaticLink(size: Int) {

  val slink = new Array[Int](size + 1)
  var linkSize = size
  for (i <- 0 until slink.length - 1) {
    slink(i) = i + 1
  }
  slink(slink.length - 1) = 0

  def remove(index: Int) = {

    var k = 0
    var pre = 0
    for (i <- 1 to index) {
      pre = k
      k = slink(k)
    }

    slink(pre) = slink(k)
    slink(k) = -1
    linkSize = linkSize - 1
    k
  }

  def getSize(): Int = linkSize

override  def toString() = {
    
    slink.mkString(",")
  }
  
}
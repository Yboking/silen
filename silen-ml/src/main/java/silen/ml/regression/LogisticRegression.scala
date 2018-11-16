package silen.ml.regression
import java.util.Random

import silen.ml.common.SampleUtil
import silen.ml.data.TrainSet

object LogisticRegression {

  def sigmoid(x: Double) = {
    1.0 / (1.0 + math.pow(math.E, -x))
  }


  def main(args: Array[String]): Unit = {

    val train = new TrainSet().fromFile("data/iris.csv");


    testLogisRegression(train.data, train.labels, train.data, train.labels);
  }
  /**
   * general random gradient desc
   *
   */

  def gradientDesc(train: Array[Array[Double]], labels: Array[Double], maxIter: Int) :Array[Double] = {

    var iterOver = false
    var k = 0
    var alpha = 0.0
    val random = new util.Random()
    var theta = Array.fill(4)(1.0)

    var iterCount = 0
    while (!iterOver) {

      val trainIndex = SampleUtil.randomSequence(train.length)

      for (i <- 0 until trainIndex.length) {

        alpha = 4.0 / (1.0 + k + i) + 0.0001
        val dataIndex = trainIndex(i) - 1

        val tmpdata = train(new Random().nextInt(train.size))

        val p = sigmoid(dot(tmpdata, theta))
        val error = labels(i) - p

        //  val increament = alpha * (1.0 / train.length  )  * dot( error, train(dataIndex) ) 
        val increament = dot(alpha * (error), train(dataIndex))

        theta = selfMinus(theta, increament)
      }

      iterCount = iterCount + 1
      if(iterCount >= maxIter){
        iterOver = true
      }
    }
    theta
  }



  def gradientDesc(train: TrainSet, maxIter: Int = 10) :Array[Double] = {

     gradientDesc(train.data, train.labels, maxIter);

  }

  def dot(vec1: Array[Double], vec2: Array[Double]) = {

    var sum = 0.0
    for (i <- 0 until vec1.length) {
      sum = sum + vec1(i) * vec2(i)
    }
    sum
  }

  def dot(value: Double, vec: Array[Double]) = {

    vec.map(_ * value)
  }

  def selfMinus(vec1: Array[Double], vec2: Array[Double]) = {

    for (i <- 0 until vec1.length) {

      vec1(i) = vec1(i) - vec2(i)
    }
    vec1
  }
  
 
  
  def transform( v :Double) = {

    println("v " + v  + " sigmoid :" + sigmoid(v))
    if(sigmoid(v) > 0.5) 1 else 0 
    
  }
  def testLogisRegression(train: Array[Array[Double]], labels: Array[Double], testdata :Array[Array[Double]], testLable: Array[Double]) {

    
    val theta = gradientDesc(train, labels, 10)
   
    var errorCount = 0
    var i = 0
    for( record <- testdata){
      
       val p = transform(dot(theta,  record))

      if(p == 0){
        print(23)
      }
       println("p :" + p)
       if(testLable(i).toInt != p) {
    	   errorCount = errorCount + 1
       }
      i = i + 1;
    }
    
    println(errorCount.toDouble / testdata.length)
    
  }
}
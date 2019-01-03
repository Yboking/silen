package silen.ml.classification

import silen.ml.common.SampleUtil
import silen.ml.data.TrainSet
import silen.ml.math.func.Functions._

object LogisticRegression {



  def main(args: Array[String]): Unit = {

    //    val trainSet = new TrainSet().fromFile("data/iris.csv");
    //
    //
    //    testLogisRegression(trainSet, trainSet.data, trainSet.labels);
    //    println(sigmoid(-2.4))

  }

  /**
   * general random gradient desc
   *
   */

  def gradientDesc(trainSet: TrainSet, maxIter: Int = 20) :Array[Double] = {


    val (data, labels) = (trainSet.dataBuffer, trainSet.labels)
    val (len, features) = trainSet.dimensions;
    var iterOver = false
    var alpha = 0.0
    var theta = Array.fill(features)(1.0)

    var iterCount = 0
    while (!iterOver) {

      val trainIndex = SampleUtil.randomSequence(len)

      for (i <- 0 until trainIndex.length) {

        alpha = 1.0 / (1.0 + iterCount + i) + 0.0001
        val dataIndex = trainIndex(i) - 1
//        val dataIndex = new Random().nextInt(train.length )

        val tmpdata = data(dataIndex)

        val p = sigmoid(dot(tmpdata, theta))
        val error = labels(dataIndex) - p

        println("error is :" + error)
        val increament = dot(alpha, dot(error, tmpdata))
        theta = selfAdd(theta, increament)
      }

      iterCount = iterCount + 1
      if(iterCount >= maxIter){
        iterOver = true
      }
    }
    theta
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


  def selfAdd(vec1: Array[Double], vec2: Array[Double]) = {

    for (i <- 0 until vec1.length) {

      vec1(i) = vec1(i) + vec2(i)
    }
    vec1
  }



  def transform( v :Double) = {

    println("v " + v  + " sigmoid :" + sigmoid(v))
    if(sigmoid(v) > 0.5) 1 else 0 
    
  }
  def testLogisRegression(train: TrainSet, testdata :Array[Array[Double]], testLable: Array[Double]) {

    
    val theta = gradientDesc(train, 1)
   
    var errorCount = 0
    var i = 0
    for( record <- testdata){
      
       val p = transform(dot(theta,  record))

       println("p :" + p)
       if(testLable(i).toInt != p) {
    	   errorCount = errorCount + 1
       }
      i = i + 1;
    }
    
    println(errorCount.toDouble / testdata.length)
    
  }
}
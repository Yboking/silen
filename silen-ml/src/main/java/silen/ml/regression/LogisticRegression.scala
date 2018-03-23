package silen.ml.regression
import scala.math
import silen.ml.common.SampleUtil

object LogisticRegression {

  def sigmoid(x: Double) = {
    1.0 / (1.0 + math.pow(math.E, -x))
  }

  
 def main(args: Array[String]): Unit = {
   
   val train = Array(Array(1.2,0.3),
                     Array(1.4, 2.1),
                     Array(4.1,3.1),
                     Array(6.1,11.2),
                     Array(18.1, 12.1))
   val labels = Array(1.0,0, 0 ,1,1)
   
   
   
   
   testLogisRegression(train, labels, train)                                                                                  
 }
  /**
   * general random gradient desc
   *
   */
  //  def gradientDesc(train: Array[Array[Double]], labels: Array[Double], maxIter: Int) = {
  //
  //    var iterOver = false
  //    var k = 0
  //    var alpha = 0.0
  //    val random = new util.Random()
  //    var theta = Array.fill(train.length)(1.0)
  //
  //    while (!iterOver) {
  //
  //      for (i <- 0 until train.length) {
  //
  //        alpha = 4.0 / (1.0 + k + i) + 0.001
  //        val dataIndex = random.nextInt(train.length)
  //
  //        val tmpdata = train(dataIndex)
  //
  //        val p = sigmoid(dot(tmpdata, theta))
  //        val error = labels(i) - p
  //
  //        //  val increament = alpha * (1.0 / train.length  )  * dot( error, train(dataIndex) ) 
  //        val increament = dot(alpha * (1.0 / train.length) * error, train(dataIndex))
  //
  //        theta = selfMinus(theta, increament)
  //      }
  //
  //    }
  //
  //  }

  def gradientDesc(train: Array[Array[Double]], labels: Array[Double], maxIter: Int) = {

    var iterOver = false
    var k = 0
    var alpha = 0.0
    val random = new util.Random()
    var theta = Array.fill(2)(1.0)

    var iterCount = 0
    while (!iterOver) {

      val trainIndex = SampleUtil.randomSequence2(train.length)

      for (i <- 0 until trainIndex.length) {

        alpha = 4.0 / (1.0 + k + i) + 0.001
        val dataIndex = trainIndex(i)

        val tmpdata = train(dataIndex)

        val p = sigmoid(dot(tmpdata, theta))
        val error = labels(i) - p

        //  val increament = alpha * (1.0 / train.length  )  * dot( error, train(dataIndex) ) 
        val increament = dot(alpha * (1.0 / train.length) * error, train(dataIndex))

        theta = selfMinus(theta, increament)
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
  
 
  
  def transform( v :Double) = {
    
    if(sigmoid(v) > 0.5) 1 else 0 
    
  }
  def testLogisRegression(train: Array[Array[Double]], labels: Array[Double], testdata :Array[Array[Double]]) {

    
    val theta = gradientDesc(train, labels, 10)
   
    var errorCount = 0
    for( record <- testdata){
      
       val p = transform(dot(theta,  record))
       
       if(record(record.length - 1).toInt != p) {
         
    	   errorCount = errorCount + 1 
         
       }
       
       
    }
    
    println(errorCount / testdata.length)
    
  }
}
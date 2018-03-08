package silen.ml.regression
import scala.math

object LogisticRegression {

 

  def sigmoid(x: Double) = {
    1.0 / (1.0 + math.pow(math.E, -x))
  }

  /**
   * general random gradient desc
   *
   */
  def gradientDesc(train: Array[Array[Double]], labels: Array[Double], maxIter: Int) = {

    var iterOver = false
    var k = 0
    var alpha = 0.0
    val random = new util.Random()
    var theta = Array.fill(train.length)(1.0)

    while (!iterOver) {

      for (i <- 0 until train.length) {

        alpha = 4.0 / (1.0 + k + i) + 0.001
        val dataIndex = random.nextInt(train.length)

        val tmpdata = train(dataIndex)

        val p = sigmoid(dot(tmpdata, theta))
        val error = labels(i) - p

        //  val increament = alpha * (1.0 / train.length  )  * dot( error, train(dataIndex) ) 
        val increament = dot(alpha * (1.0 / train.length) * error, train(dataIndex))

        theta = selfMinus(theta, increament)
      }

    }

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
  
  

  def logisRegression() {

  }
}
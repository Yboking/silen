package silen.ml.math.func
import java.lang.Math
object Functions {

  def sigmoid(x: Double) = {
    1.0 / (1.0 + Math.pow(math.E, -x))
  }

  def log2(x: Double) = Math.log(x) / Math.log(2.0)
}


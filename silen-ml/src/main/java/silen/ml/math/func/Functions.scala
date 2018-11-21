package silen.ml.math.func

object Functions {

  def sigmoid(x: Double) = {
    1.0 / (1.0 + math.pow(math.E, -x))
  }
}

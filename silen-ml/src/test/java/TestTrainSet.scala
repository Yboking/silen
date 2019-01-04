import junit.framework.Assert
import org.scalatest.FunSuite
import silen.ml.data.TrainSet



class TestTrainSet  extends FunSuite{


    test("trainSet properties ") {

      val train = TrainSet.fromFile("data/testTrain.csv")
      assert(train.size == 17)
      assert(train.numOfAttrs == 4)
      assert(train.labels.length == 17)
      assert(train.labels.distinct.length == 2)

      assert(train.labels(3) == 0.0 )
      assert(java.util.Arrays.equals(Array(4.7,3.2,1.3,0.2),train.getRecord(2) ))

    }

  test("trainset method"){
    val train = TrainSet.fromFile("data/testTrain.csv")
    val train2 = train.selectData(0, Array(5.3, 5.1))
    assert(train2.size == 5)
    assert(train2.numOfAttrs == 4)
    assert(train2.labels.length == 5)
    assert(train.labels.distinct.length == 2)

    assert(train2.featureValues(1).length == 4)

    assert(train2.labels(4) == 1.0 )
    assert(train2.labels(2) == 0.0 )
    assert(java.util.Arrays.equals(Array(5.1,2.5,3,1.1),train2.getRecord(6) ))




  }

}
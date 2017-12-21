package youe.data.service.job.data

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.ml.PipelineModel
import youe.data.service.job.ResourceType

//case class DataRes(id: DataIdentity, data: Dataset[Row])

case class DataRes[+T](var id: DataIdentity, var dtype: String, data: T) {

  def setID(did: DataIdentity) = {

    this.id = did
  }

  def getResource() = {

    dtype match {

      case ResourceType.MODEL => {

        data.asInstanceOf[PipelineModel]
      }

      case ResourceType.DATA_RECORD => {

        data.asInstanceOf[Dataset[Row]]
      }

      case _ => {

        //TODO 

        throw new Exception("current not supported ! ")
      }

    }
  }
}


 
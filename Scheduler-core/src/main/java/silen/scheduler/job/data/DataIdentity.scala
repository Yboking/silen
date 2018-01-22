package silen.scheduler.job.data

case class DataIdentity(userId: String, jobId: String, producerId: Int, consumerId: Int, pathTag: String = "") {

  override def toString() = userId + "_" + jobId + "_" + producerId + "_" + consumerId

  override def hashCode = toString().hashCode()

  override def equals(other: Any) = {

    if (!other.isInstanceOf[DataIdentity]) false
    else {

      val ins = other.asInstanceOf[DataIdentity]

      userId == ins.userId && jobId == ins.jobId && producerId == ins.producerId && consumerId == ins.consumerId
    }

  }
}



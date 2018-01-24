package silen.scheduler.data.job

case class DataIdentity(private var userId: String = null, private var jobId: String= null, producerId: Int, consumerId: Int, pathTag: String = "") {

  override def toString() = userId + "_" + jobId + "_" + producerId + "_" + consumerId

  override def hashCode = toString().hashCode()

  override def equals(other: Any) = {

    if (!other.isInstanceOf[DataIdentity]) false
    else {

      val ins = other.asInstanceOf[DataIdentity]

      userId == ins.userId && jobId == ins.jobId && producerId == ins.producerId && consumerId == ins.consumerId
    }

  }

  def setUserId(s: String) {

    this.userId = s
  }
  def getUserId() = this.userId

  def setJobId(s: String) {
    this.jobId = s
  }
  def getJobId() = this.jobId

}



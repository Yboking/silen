package youe.data.service.job.data

case class NodeIdentity(userId: String, jobId: String, id: Int, var preNodes: Array[NodeIdentity] = null, var succNodes: Array[NodeIdentity] = null, cmd: Array[String] = null, pathTag: String = null) {

  override def toString() = userId + "-" + jobId + "-" + id

  override def hashCode = toString().hashCode()

  override def equals(other: Any) = {

    if (!other.isInstanceOf[NodeIdentity]) false
    else {

      val ins = other.asInstanceOf[NodeIdentity]

      userId == ins.userId && jobId == ins.jobId && id == ins.id
    }

  }
}
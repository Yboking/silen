package silen.scheduler.job.data

case class NodeIdentity(userId: String,
                        jobId: String,
                        id: Int,
                        var preNodes: Array[NodeIdentity] = null,
                        var succNodes: Array[NodeIdentity] = null,
                        cmd: Array[String] = null,
                        private var name: String = null) {

  override def toString() = userId + "_" + jobId + "_" + id

  override def hashCode = toString().hashCode()

  override def equals(other: Any) = {

    if (!other.isInstanceOf[NodeIdentity]) false
    else {

      val ins = other.asInstanceOf[NodeIdentity]

      userId == ins.userId && jobId == ins.jobId && id == ins.id
    }

  }

  def setName(s: String) {

    this.name = s
  }

  def getName() = this.name
}


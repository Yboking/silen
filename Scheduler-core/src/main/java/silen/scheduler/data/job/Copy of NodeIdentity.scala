//package silen.scheduler.data.job
//
//
//
//
//case class NodeIdentity2(private var userId: String = null,
//                        private var jobId: String = null,
//                        id: Int,
//                        var preNodes: Array[NodeIdentity] = null,
//                        var succNodes: Array[NodeIdentity] = null,
//                        cmd: Array[String] = null,
//                        actionParser: ActionParser = null,
//                        private var name: String = null) {
//
//  override def toString() = userId + "_" + jobId + "_" + id
//
//  override def hashCode = toString().hashCode()
//
//  override def equals(other: Any) = {
//
//    if (!other.isInstanceOf[NodeIdentity]) false
//    else {
//
//      val ins = other.asInstanceOf[NodeIdentity]
//
//      userId == ins.userId && jobId == ins.jobId && id == ins.id
//    }
//
//  }
//
//  def setName(s: String) {
//
//    this.name = s
//  }
//
//  def getName() = this.name
//
//  def setUserId(s: String) {
//
//    this.userId = s
//  }
//  def getUserId() = this.userId
//
//  def setJobId(s: String) {
//    this.jobId = s
//  }
//  def getJobId() = this.jobId
//
//  def getJobFullId() = this.userId + "_" + this.jobId
//
//}
//

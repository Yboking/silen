package silen.scheduler.service.job

import silen.scheduler.job.data.NodeIdentity

trait NodeManager {

  def handlePrestart(ndi: NodeIdentity)

  def handleRunning(ndi: NodeIdentity)

  def handleFinish(ndi: NodeIdentity)

  def handleFail(ndi: NodeIdentity)

}
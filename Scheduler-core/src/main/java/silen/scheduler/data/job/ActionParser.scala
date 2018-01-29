package silen.scheduler.data.job

abstract class ActionParser(actionType: String, args: Any) {

  def getOutputPaths(): Any

  def getActionType(): String

  def getActionArgs(): Any

  def getActionEntrance(): String
}
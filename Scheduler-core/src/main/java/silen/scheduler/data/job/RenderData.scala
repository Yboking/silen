package silen.scheduler.data.job

case class RenderData(private var eventName: String, private var data: String) {

  def getEventName() = this.eventName

  def setEventName(name: String) {

    this.eventName = name

  }

  def getData() = this.data

  def setData(d: String) {

    this.data = d
  }

  def toTuple = {

    (eventName, data)
  }

}
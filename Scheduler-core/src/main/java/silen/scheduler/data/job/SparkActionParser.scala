package silen.scheduler.data.job


class SparkActionParser(actionType: String = "SPARK", args: Array[String]) extends ActionParser(actionType, args) {

  //TODO must support the disorder args, 
  /*
   * now the default output is last arg, and it's single output
   */
  def getOutputPaths() = {

    args(args.length - 1)

  }

  def getActionType() = {

    this.actionType
  }

  def getActionArgs() = {

    this.args
  }

  def getActionEntrance(): String = {

    for (arg <- args) {

      if (arg.startsWith("class=")) {

        return arg.split("=")(1)
      }

    }
    throw new Exception("Spark action should contain the main class eg,  class=com.xxx.Analyser")

  }
}
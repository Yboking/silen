package silen.ml.data

abstract  class Opt{
  def merge(curOpt: Opt): Opt
  def equals(target :Opt) :Boolean

  def getOptionValues() :Any
}
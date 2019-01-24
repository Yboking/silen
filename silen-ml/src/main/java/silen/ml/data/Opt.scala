package silen.ml.data

import silen.ml.classification.FeatureValue

abstract  class Opt{
  def merge(curOpt: Opt): Opt
  def equals(target :Opt) :Boolean

  def getOptionValues() :Seq[FeatureValue]
}
package silen.ml.classification

class CartNode extends TNode {

  var featureIndex = -1;
  var left: CartNode = null;
  var right: CartNode = null;
}

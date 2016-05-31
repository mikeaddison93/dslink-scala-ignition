package org.dsa.iot.rx.block

import org.dsa.iot.{ listToValue, valueToAny }
import org.dsa.iot.dslink.node.value.Value

import rx.lang.scala.Observable

case class Zip() extends DSARxBlock {
  val inputs = PortList[Value]

  protected def combineAttributes = Observable.just(Nil)

  protected def combineInputs = inputs.ports map (_.in)

  protected def evaluator(attrs: Seq[Value]) = (inputs: Seq[ValueStream]) => Observable.zip(Observable.from(inputs)) map {
    _.map(valueToAny).toList
  }
}
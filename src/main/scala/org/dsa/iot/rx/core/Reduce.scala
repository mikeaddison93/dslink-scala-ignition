package org.dsa.iot.rx.core

import org.dsa.iot.rx.RxTransformer
import org.dsa.iot.scala.Having

/**
 * Repeatedly applies a function, where the first argument is the result obtained in the previous
 * application and the second argument is each element of the source sequence.
 * 
 * <img width="640" height="320" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/reduce.png" alt="" />
 */
class Reduce[T] extends RxTransformer[T, T] {
  
  def accumulator(func: (T, T) => T): Reduce[T] = this having (accumulator <~ func)
  
  val accumulator = Port[(T, T) => T]("accumulator")

  protected def compute = accumulator.in flatMap source.in.reduce
}

/**
 * Factory for [[Reduce]] instances.
 */
object Reduce {

  /**
   * Creates a new Reduce instance.
   */
  def apply[T]: Reduce[T] = new Reduce[T]

  /**
   * Creates a new Reduce instance for the accumulator function.
   */
  def apply[T](accumulator: (T, T) => T): Reduce[T] = {
    val block = new Reduce[T]
    block.accumulator <~ accumulator
    block
  }
}
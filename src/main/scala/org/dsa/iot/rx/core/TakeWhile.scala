package org.dsa.iot.rx.core

import org.dsa.iot.rx.RxTransformer

/**
 * Emits items from the source as long as the predicate condition is true.
 */
class TakeWhile[T] extends RxTransformer[T, T] {
  val predicate = Port[T => Boolean]("predicate")

  protected def compute = predicate.in flatMap source.in.takeWhile
}

/**
 * Factory for [[TakeWhile]] instances.
 */
object TakeWhile {

  /**
   * Creates a new TakeWhile instance.
   */
  def apply[T]: TakeWhile[T] = new TakeWhile[T]

  /**
   * Creates a new TakeWhile instance with the given predicate.
   */
  def apply[T](predicate: T => Boolean): TakeWhile[T] = {
    val block = new TakeWhile[T]
    block.predicate <~ predicate
    block
  }
}
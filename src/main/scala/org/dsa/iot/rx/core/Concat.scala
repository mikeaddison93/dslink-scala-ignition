package org.dsa.iot.rx.core

import org.dsa.iot.rx.RxMerger2

/**
 * First emits the items emitted by the first source, and then the items emitted by the second.
 * 
 * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/concat.png" alt="" />
 */
class Concat[T] extends RxMerger2[T, T, T] {
  protected def compute = source1.in ++ source2.in
}

/**
 * Factory for [[Concat]] instances.
 */
object Concat {
  
  /**
   * Creates a new Concat instance.
   */
  def apply[T]: Concat[T] = new Concat[T]
}
package org.dsa.iot.ignition.spark

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.{ StructField, StructType }
import org.dsa.iot.rx.AbstractRxBlock
import org.dsa.iot.scala.Having

import com.ignition.frame.SparkRuntime

import rx.lang.scala.Observable

/**
 * Reads a CSV file and generates a spark data frame.
 */
class CsvFileInput(implicit rt: SparkRuntime) extends AbstractRxBlock[DataFrame] {

  def path(str: String): CsvFileInput = this having (path <~ str)
  def separator(str: String): CsvFileInput = this having (separator <~ Some(str))
  def noSeparator(): CsvFileInput = this having (separator <~ None)
  def columns(cols: StructField*): CsvFileInput = this having (columns <~ cols)
  def schema(schema: StructType): CsvFileInput = this having (columns <~ schema)

  val path = Port[String]("path")
  val separator = Port[Option[String]]("separator")
  val columns = PortList[StructField]("columns")

  protected def compute = path.in combineLatest separator.in combineLatest columns.combinedIns flatMap {
    case ((p, sep), cols) =>
      val schema = if (cols.isEmpty) None else Some(StructType(cols))
      val cfi = com.ignition.frame.CsvFileInput(p, sep, schema)
      Observable.just(cfi.output)
  }
}

/**
 * Factory for [[CsvFileInput]] instances.
 */
object CsvFileInput {

  /**
   * Creates a new CsvFileInput instance with "," as the separator and no defined columns.
   */
  def apply()(implicit rt: SparkRuntime): CsvFileInput = new CsvFileInput separator "," columns ()
}
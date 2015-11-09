package com.sathish.datagen

import java.text.{SimpleDateFormat, DateFormat, DecimalFormat}
import java.util.Date

import scala.util.Random

abstract class DataField(val id: String) extends Random {
  def nextValue: Any

  def format(value: Any): String
}

abstract class RangeField(id: String, val min: Long, val max: Long) extends DataField(id) {
  def getRandom: Long = min + (nextDouble * (max - min)).toLong
}

class SeqNumberField(id: String, start: Long) extends DataField(id) {
  var current = start - 1

  override def nextValue: Long = {
    current += 1
    current
  }

  override def format(value: Any): String = current.toString
}

class NumericField(id: String, min: Long, max: Long) extends RangeField(id, min, max) {
  override def nextValue: Long = getRandom

  override def format(value: Any): String = value.toString
}

class DateTimeField(id: String, min: Long, max: Long, val formatter: DateFormat) extends RangeField(id, min, max) {
  override def nextValue: Date = {
    val date = new java.util.Date(getRandom)
    date
  }
  override def format(value: Any): String = formatter.format(value)
}

class DecimalField(id: String, min: Double, max: Double, formatter: DecimalFormat) extends DataField(id) {
  override def nextValue: Double = min + nextDouble * (max - min)

  override def format(value: Any): String = formatter.format(value)
}

class TextField(id: String, lenght: Int) extends DataField(id) {
  override def nextValue: String = {
    val text = new StringBuilder
    val textStream = Random.alphanumeric
    textStream.take(lenght).foreach(text.append(_))
    text.toString()
  }

  override def format(value: Any): String = value.toString
}

object SeqNumberField {
  def apply(id: String, start: Long) = {
    val field = new SeqNumberField(id, start)
    field
  }
}

object NumericField {
  def apply(id: String, min: Long, max: Long) = {
    val field = new NumericField(id, min, max)
    field
  }
}

object DateTimeField {
  var format = new StringBuilder

  def apply(id: String, min: String, max: String, format: String) = {
    val formatter = new SimpleDateFormat(format)
    val minDate = formatter.parse(min)
    val maxDate = formatter.parse(max)
    val field = new DateTimeField(id, minDate.getTime, maxDate.getTime, formatter)
    field
  }

  def MM = format append "MM"

  def DD = format append "dd"

  def YYYY = format append "yyyy"

  def / = format append "/"

  def - = format append "-"
}

object DecimalField {
  def apply(id: String, min: Double, max: Double, precision: Int, currency: String) = {
    var format = currency + "0."
    for (i <- 1 to precision) {
      format += "0"
    }
    val field = new DecimalField(id, min, max, new DecimalFormat(format))
    field
  }
}

object TextField {
  def apply(id: String, length: Int) = {
    val field = new TextField(id, length)
    field
  }
}
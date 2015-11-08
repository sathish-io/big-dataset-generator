package com.sathish.datagen

import java.text.{DateFormat, DecimalFormat, SimpleDateFormat}

import scala.util.Random

trait DataField extends Random {
  def nextValue: Any
}

class NumericField(val id: String, min: Long, max: Long) extends DataField {
  override def nextValue: Long = min + (nextDouble * (max - min)).toLong
}

class DecimalField(val id: String, min: Double, max: Double, formatter: DecimalFormat) extends DataField {
  override def nextValue: Any = formatter.format(min + nextDouble * (max - min))
}

class TextField(val id: String, lenght: Int) extends DataField {
  override def nextValue: String = {

    val text = new StringBuilder
    val textStream = Random.alphanumeric
    textStream.take(lenght).foreach(text.append(_))
    text.toString()
  }
}

class DateField(val id: String, min: Long, max: Long, formatter: DateFormat) extends DataField {
  override def nextValue: String = {
    val date = new java.util.Date(min + (Math.random() * (max - min)).toLong)
    formatter.format(date)
  }
}

object NumericField {
  def apply(id: String, min: Long, max: Long) = {
    val field = new NumericField(id, min, max)
    field
  }
}

object DecimalField {
  def apply(id: String, min: Double, max: Double, precision: Int, currency: String) = {
    var format = currency + "0."
    for (i <- 1 to precision) {
      format += "0"
    }
    //println(s"format: $format")
    val field = new DecimalField(id, min, max, new DecimalFormat(format))
    field
  }
}

object TextField {
  def apply(id: String, lenght: Int) = {
    val field = new TextField(id, lenght)
    field
  }
}

object DateField {
  var format = new StringBuilder

  def apply(id: String, min: String, max: String, format: String) = {
    val formatter = new SimpleDateFormat(format)
    val minDate = formatter.parse(min)
    val maxDate = formatter.parse(max)
    val field = new DateField(id, minDate.getTime, maxDate.getTime, formatter)
    field
  }

  def MM = format append "MM"
  def DD = format append "dd"
  def YYYY = format append "yyyy"

  def / = format append "/"
  def - = format append "-"

}
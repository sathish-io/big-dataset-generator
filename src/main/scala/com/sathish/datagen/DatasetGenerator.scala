package com.sathish.datagen

import java.io.{BufferedWriter, File, FileWriter}

import scala.annotation.tailrec


class DatasetGenerator(fields: List[DataField], rows: Int, filename: String) {

  val file = new File(filename)
  val writer = new BufferedWriter(new FileWriter(file))

  def generate: Unit = {
    for (i <- 1 to rows) {
      val line = generateRecord(fields)
      writer.write(line)
      writer.newLine()
    }
    writer.close()
  }

  private def generateRecord(fields: List[DataField]): String = {

    @tailrec
    def appendColumns(fields: List[DataField], record: StringBuilder): String = {
      fields match {
        case Nil => record.toString
        case x::tail => {
          record.append(x.nextValue)
          if(tail != Nil) {
            record.append(",")
          }
          appendColumns(tail, record)
        }
      }
    }
    appendColumns(fields, new StringBuilder())
  }

}

object DatasetGenerator {

  def apply(fields: List[DataField], rows: Int, filename: String) = {
    val generator = new DatasetGenerator(fields, rows, filename)
    generator
  }
}



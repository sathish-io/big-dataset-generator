package com.sathish.datagen

import org.scalatest._

import scala.io.Source

class DatasetGeneratorSpec extends FlatSpec with Matchers {

  "A DatasetGenerator" should "numeric field in the rage" in {
    val field1 = NumericField("txn_id", 10000, 20000).nextValue
    assert(field1 > 10000  && field1 < 20000)
  }

  it should "date field in the range" in {
    val randomDate = DateField("txn_date", "06-01-2015", "06-30-2015", "mm-dd-YYYY").nextValue
    //TODO:: check the results
  }

  it should "text field of correct length" in {
    val text = TextField("txn_desc", 10).nextValue
    assert(10 == text.length)
  }

  it should "seq number field" in {
    val counter = SeqNumberField("counter", 100)
    assert(100 == counter.nextValue)
    assert(101 == counter.nextValue)
    assert(102 == counter.nextValue)
    assert(103 == counter.nextValue)
    assert(104 == counter.nextValue)
  }

  it should "create new file" in {
    val file = "file1.txt"
    val rows = 10
    DatasetGenerator(
      List(
        NumericField("txn_id", 10000, 20000),
        DecimalField("amount", 1, 100, 2, "$"),
        TextField("desc", 10),
        DateField("txn_date", "01-01-2014", "12-31-2014", "MM-dd-yyyy")
      ),
      rows,
      file
    ) generate

    val lines = Source.fromFile(file).getLines()
    assert(lines.size === rows)
  }
}
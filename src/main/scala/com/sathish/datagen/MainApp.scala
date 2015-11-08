package com.sathish.datagen

object MainApp extends App {

  val file = "file1.txt"
  val rows = 1000000

  println(s"Staring to generate file $file with $rows records")

  DatasetGenerator(
    List(
      SeqNumberField("txn_id", 1),
      DateField("txn_date", "01-01-2014", "12-31-2014", "MM-dd-yyyy"),
      NumericField("acct_num", 50000, 60000),
      NumericField("merchand_code", 2000, 2500),
      NumericField("zipcode", 94000, 96000),
      DecimalField("amount", 1, 1000, 2, "$"),
      TextField("desc", 10)
    ),
    rows,
    file
  ) generate
}

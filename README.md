# big-dataset-generator
Scala based tool to generate sample datasets for big data applications

## Usage

### Example
To Generate a text file(CSV format) with name 'file1.txt' with 10 records with following columns,
txn_id - random number between 10000 and 20000
amount - $ amount between 1.00 and 100.00 with 2 decimal places
desc - random text of lenght 10
txn_date - random date(in MM-DD-YYYY format) between Jan-01-2014 and Dec-3102014

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

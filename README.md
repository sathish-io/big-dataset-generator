# big-dataset-generator
Scala based tool to generate sample datasets for big data applications

## Usage

Make sure to install SBT/Activator first. Edit file MainApp.scala based on data set that you wanted to generate, and run,

```
sbt run
```
If you are using activator then run 'activator run'


## Supported data types

```
1. SeqNumberField(id: String, start: Long)
2. NumericField(id: String, min: Long, max: Long)
3. DecimalField(id: String, min: Double, max: Double, precision: Int, currency: String)
4. TextField(id: String, lenght: Int)
5. DateField(id: String, min: String, max: String, format: String)
````

### Example
To Generate a text file(CSV format) with name 'file1.txt' with 10 records with following columns,
* txn_id - sequential number starting with 1
* acct_num - random number between 50000 and 60000
* amount - random amount between 1.00 and 100.00 with 2 decimal places, prefix with $
* desc - random text of length 10
* txn_date - random date(in MM-DD-YYYY format) between Jan-01-2014 and Dec-3102014

```
val file = "file1.txt"
val rows = 10
DatasetGenerator(
  List(
    SeqNumberField("txn_id", 1),
    NumericField("acct_num", 50000, 60000),
    DecimalField("amount", 1, 100, 2, "$"),
    TextField("desc", 10),
    DateField("txn_date", "01-01-2014", "12-31-2014", "MM-dd-yyyy")
  ),
  rows,
  file
) generate
```
import com.github.tototoshi.csv._

val parser = new CSVParser(defaultCSVFormat)

val input = "test\"\"string"
val result = parser.parseLine(input)

result.get.size

println(input)



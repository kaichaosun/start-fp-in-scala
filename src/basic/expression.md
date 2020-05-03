# Expression

Scala中几乎所有的语句都是表达式，就连`if else`、`patter match`也都是表达式。
expressions 总是返回结果，几乎不产生任何side effect；相反，statements 并不返回任何结果，仅仅为了获取side effect而执行。
EOP（expression-oriented programming）是函数式编程语言的共有特点，更多内容请参考[这里](https://alvinalexander.com/scala/best-practice-think-expression-oriented-programming-eop)。
通过EOP，代码变得：
* easy to reason about
* easy to test
* excute in parallel

```
scala> if (true) 1 else 0
res0: Int = 1

scala> "hp" match {
     |   case "hp" => "computer"
     |   case _ => "non-computer"
     | }
res1: String = computer
```
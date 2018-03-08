# Basics in Scala

## REPL
REPL (Read-Eval-Print Loop)是编程语言的交互执行环境，可以通过下面两种方式进入Scala的REPL：
* sbt console
* amm
其中amm是第三方REPL工具，支持语法高亮、代码复制等用户友好的操作。通过如下命令进行安装：
```
sudo curl -L -o /usr/local/bin/amm https://git.io/vdNv2 && sudo chmod +x /usr/local/bin/amm && amm
```

## Scala basics
本文包含一些基本的语法和概念，如常量、变量、表达式、函数等。可以通过REPL进行练习，增加对语法的熟悉。

### val
通过val, 可以将表达式的结果赋值给一个常量，常量值不能改变。
```
scala> val three = 1 + 2
two: Int = 3
``` 

### var
var定义的值为变量，可被修改。
```
scala> var name = "dasheng"
name: String = dasheng

scala> name = "kaichao"
name: String = kaichao
```

### expressions
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

### functions

[Functions vs Methods](http://jim-mcbeath.blogspot.com/2009/05/scala-functions-vs-methods.html)

### common data structure
**List:**
```
scala> List(1,2,3)
res0: List[Int] = List(1, 2, 3)

scala> 1 :: 2 :: 3 :: Nil
res1: List[Int] = List(1, 2, 3)
```
**Set:**
```
scala> Set(1, 1, 2)
res2: scala.collection.immutable.Set[Int] = Set(1, 2)
```
**Seq:**
```
scala> Seq(1,1,2)
res3: Seq[Int] = List(1, 1, 2)
```
**Map:**
```
scala> Map('a'->1, 'b'->2)
res4: res1: scala.collection.immutable.Map[Char,Int] = Map(a -> 1, b -> 2) 
```

**Stream:**


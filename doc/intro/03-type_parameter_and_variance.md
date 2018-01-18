# Scala类型系统：Parameterized Types 和 Variances

## Why variance
Scala中集合是covariance，我们可以使用子类集合替换父类集合。例如，List[Circle]可以用于任意需要一个List[Shape]的地方，因为Circle是Shape的子类。

## References
[1] https://blog.codecentric.de/en/2015/03/scala-type-system-parameterized-types-variances-part-1/



# Setup

## 安装Java 8 JDK
打开terminal，检查版本
```
java -version
```
如果没有安装，请在[这里下载](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## 安装sbt, 0.13.13或更高版本
Mac OSX 使用brew进行安装:
```
brew install sbt
```

Linux 安装:
TODO

## Hello world in Scala 
sbt 0.13.13 增加了`new`命令，可以根据模块创建Scala项目。
```
sbt new sbt/scala-seed.g8
```
根据提示信息，输入你的项目名，完成项目的创建:
```
A minimal Scala project.

name [Scala Seed Project]: scala-walk-through

Template applied in ./scala-walk-through
```

现在我们已经有了一个最基本的项目，查看目录结构：
```
ls -l scala-walk-through
total 8
-rw-r--r--  1 kcsun  staff  282 Jan  3 12:00 build.sbt
drwxr-xr-x  4 kcsun  staff  136 Jan  3 12:00 project
drwxr-xr-x  4 kcsun  staff  136 Jan  3 12:00 src

它们的职责如下：
```
* `build.sbt`, 项目的构建文件，包含了项目的基本信息。
* `project`, 本目录中包含了项目构建过程所依赖的第三方插件等。
* `src`, 代码包括测试代码的源文件目录。sbt代码管理风格和Maven一致。

修改源代码，将`src/main/scala/example/Hello.scala`，其中一行修改为:
```
lazy val greeting: String = "Hello, Functional Programming!"
```

通过sbt运行程序有两种方式，直接通过`sbt run`运行；或者执行`sbt`进入交互REPL模式，再执行`run`。输出：
```
Hello, Functional Programming!
```

Ctrl + d 或输入`exit`退出sbt。

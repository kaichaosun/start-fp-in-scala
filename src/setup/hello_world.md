# Hello world

Using sbt new command to create an simple project:
```shell
sbt new sbt/scala-seed.g8
```
Follow the prompt information, Input the project name to finish the setup:
```shell
A minimal Scala project.

name [Scala Seed Project]: scala-walk-through

Template applied in ./scala-walk-through
```

Now we have a basic project, check the directories structure:
```shell
ls -l scala-walk-through
total 8
-rw-r--r--  1 kcsun  staff  282 Jan  3 12:00 build.sbt
drwxr-xr-x  4 kcsun  staff  136 Jan  3 12:00 project
drwxr-xr-x  4 kcsun  staff  136 Jan  3 12:00 src
```
Let me explain the files:

* `build.sbt` - the project configuration file, it includes basic information for the project like project name, group name, scala version, dependencies, etc. 
* `project` - this folder contains the file
  * `build.properties` used to set sbt version
  * `plugins.sbt` used to define all the available plugins for the build
  * Other project settings you want to extract from the `build.sbt` to make the main file clean and tight.
* `src` - this folder maintains all the source code and test code. sbt has the same code structure style with Maven.  

Modify the source code in`src/main/scala/example/Hello.scala` to be:
```scala
lazy val greeting: String = "Hello, Functional Programming!"
```

Run your newest program with sbt command, you can use `sbt run` directly, or get into interactive mode with `sbt`, then execute `run`. You will get following output if you are lucky:

```shell
Hello, Functional Programming!
```

Exit sbt with ctrl + d or simply input `exit`.
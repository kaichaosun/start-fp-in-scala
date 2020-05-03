# What's FP?

As always, there is a tedious definition for functional programming in [wikipedia](https://en.wikipedia.org/wiki/Functional_programming). A more practical explaination is in [Why OO Sucks](http://www.cs.otago.ac.nz/staffpriv/ok/Joe-Hates-OO.htm) by Joe Armstrong which pointed out the wroing design in objected orientated programming. I highly recommend you read this article. 

Then it's time to summary whats fp in your own word. It hasn't to be one hundred percent right, just lets yourself think deeper when writing something.

I will share my understanding. In functional prgramming, program strongly depends on immutable variable and pure function to make the program easy to reason. There is nothing secrets in immutable variables and pure function.

* Immutable variable: variables that can't be changed after instantiated
* Pure function: the function takes inputs, then do computation, finally return a value. The inputs is all the funtion needs to do computation, the returned value is the only way that the function can change the program "world".

That's fp, a enjoyable programming paradigm. 

But why it's so hard to learn for new comers.

From my learning experience, a few pain points are:

* Too many unintuitive termnology like functor, monad, etc. Actually you can start enjoy the convinence that FP brings without understanding all these words.
* Reading a lot of book, but practice too late. As you use more features in Scala, you will find more beauty in FP.

So in this book, I encourage you to practice as much as you can, it doesn't need to be formal, REPL expression is always a good start.
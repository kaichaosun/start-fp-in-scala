# circe

[Circe](https://circe.github.io/circe/) is a library which helps handle JSON format data. You can easily decode a JSON HTTP response to a case class. If you are working on API service, probably you will use it to encode your model with JSON format, then return to the API consumer.

#### Docode

Now, let's decode an http response, JSON data looks like:

```json
{
  "userId": 1,
  "postId": 1,
  "title": "Start FP in Scala",
  "body": "This book means to be the first place when you try to learn Scala."
}
```

The `Post` model:

```scala
case class Post(userId: Int, postId: Int, title: String, body: String)
```

The decoder need to be implicit as required by circe. It needs to implements the `Docoder` trait with `apply` function implemented. 

The input `HCursor` parameter for `apply` function is used to track the operation history of go to the field. 

`Decoder.Reuslt[A]` is a type alias of `Either[DecodingFailure, A]`

Let's see decoder:

```scala
implicit object PostDecoder extends Decoder[Post] {
  override def apply(c: HCursor): Decoder.Result[Post] =
    for {
      userId <- c.get[Int]("userId")
      postId <- c.get[Int]("postId")
      title <- c.get[String]("title")
      body <- c.get[String]("body")
    } yield Post(userId, postId, title, body)
}
```

Use the above decoder to decode the input string:

```scala
io.circe.parser.decode(postString)

// result is:
// Right(Post(1, 1, "Start FP in Scala", "This book means to be the first place when you try to learn Scala."))
```

#### Encode

Imagine  you already have the a `Post` instance, you want to present it through API in beautiful JSON format.

The encoder also needs to be implicit. Import the necessary circle syntax with `import io.circe.syntax._`.

```scala
implicit object PostEncoder extends Encoder[Post] {
    override def apply(a: Post): Json =
      Json.obj(
        "userId" -> a.userId.asJson,
        "postId" -> a.postId.asJson,
        "title" -> a.title.asJson,
        "content" -> a.body.asJson,
      )
  }
```

Check the ecoded result:

```scala
val post = Post(1, 1, "Start FP in Scala", "This book means to be the first place when you try to learn Scala.")

post.asJson.noSpaces

// result is:
// {"userId":1,"postId":1,"title":"Start FP in Scala","content":"This book means to be the first place when you try to learn Scala."}
```

Easy and cool, right?

Please write the decoder and encoder for the nested JSON data.
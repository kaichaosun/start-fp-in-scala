# Scala Web Server

* Setup a empty project with sbt.

  Resource: https://www.scala-sbt.org/1.0/docs/sbt-new-and-Templates.html

* Add http4s library and create a test API with response hello world.

  Resource: https://http4s.org/v0.18/service/

* Add model `Post(Int userId, Int id, String title, String body)` and create API to response a single Get post request as String. 

  Resource: https://docs.scala-lang.org/tour/case-classes.html

* Add circe library and make the previous response as JSON, example:

  ```json
  {
    "userId": 1,
    "postId": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
  }
  ```

  Resource: https://circe.github.io/circe/codecs/custom-codecs.html

* Add library `http4s-blaze-client`, and call the endpoint to get post information `https://jsonplaceholder.typicode.com/posts/1`

  Resource: https://http4s.org/v0.18/client/

* Decode the previous response to `Post` model and return as API response.

  Resource: https://circe.github.io/circe/codecs/custom-codecs.html
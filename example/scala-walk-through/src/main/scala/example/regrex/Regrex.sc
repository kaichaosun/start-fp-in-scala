val key = ".*api.key$".r

"api-key" match {
  case key() => "good"
  case _ => "bad"
}


"api_key" match {
  case key() => "good"
  case _ => "bad"
}

"x-api-key" match {
  case key() => "good"
  case _ => "bad"
}

"apikey" match {
  case key() => "good"
  case _ => "bad"
}


private def removeSensitiveDateFromString(text: String) = {
  val reg = """\w*api.key\s*(:|=)\s*[^,\.\s]*""".r
  reg.replaceAllIn(text, "api-key: redacted")
}

removeSensitiveDateFromString("This is a test key, api-key:123 and this is other things")

removeSensitiveDateFromString("This is a test key, x-api~key:123, and this is other things")

removeSensitiveDateFromString("This is a test key, x-api-key=123, and this is other things")

removeSensitiveDateFromString("This is a test key, x-apikey=123, and this is other things")

removeSensitiveDateFromString("This is a test key, x-api-key=JJlX:2RL|%)9, and this is other things")
removeSensitiveDateFromString("This is a test key, x-api-key=JJlX:2RL|%)9. and this is other things")
removeSensitiveDateFromString("This is a test key, x-api-key=JJlX:2RL|%)9. and this is other things")
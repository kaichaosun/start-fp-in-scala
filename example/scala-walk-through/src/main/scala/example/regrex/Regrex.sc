val key = "^api.key$".r

"api-key" match {
  case key() => "good"
  case _ => "bad"
}


"api_key" match {
  case key() => "good"
  case _ => "bad"
}

"apikey" match {
  case key() => "good"
  case _ => "bad"
}

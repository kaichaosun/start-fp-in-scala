package example

trait Cache[K, V] {
  def get(key: K)
  def put(key: K, value: V)
  def delete(key: K)
}

object Cache {
  Set[String]
}

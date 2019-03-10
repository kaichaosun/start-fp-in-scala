case class Order(id: String, money: Int, cId: String)
case class ExternalOrder(id: String, money: Int)

val orders = List(Order("A", 10, "101"), Order("B",20, "102"), Order("B", 5, "102"))

val groupedOrders = orders.groupBy(_.id)

(groupedOrders.map {
  case (id, orders) => (id, orders.foldRight(ExternalOrder(id, 0))((order, external) => ExternalOrder(external.id, external.money+order.money)))
}).values.toList

// enough liquidity
val successOrders = ExternalOrder("A", 10)

// distribute A

val partiallySuccessOrders = ExternalOrder("B", 15)

// 20/30*10
// 5/30*10
//
// -5 ok
// 5/30
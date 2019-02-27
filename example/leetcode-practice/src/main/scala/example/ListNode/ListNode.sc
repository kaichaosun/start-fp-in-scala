class ListNode(var _x: Int = 0) {
  var next: ListNode = null
  var x: Int = _x
}


def calculateListNode(l: ListNode, level: Int, result: Int): Int = {
  if (l.next == null) return result + l.x * Math.pow(10, level).toInt
  calculateListNode(l.next, level + 1, result + l.x * Math.pow(10, level).toInt)
}

def constructListNode(num: Int): ListNode = {
  val reversedDigits = num.toString.map(_.asDigit).reverse
  println(reversedDigits)
  reversedDigits.zipWithIndex.groupBy(_._2)

  reversedDigits.map(new ListNode(_)).reduceRight((node, res) => {
    node.next = res
    node
  })
}

def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
  val sum = calculateListNode(l1, 0, 0) + calculateListNode(l2, 0, 0)
  constructListNode(sum)
}

val l1 = constructListNode(9)
calculateListNode(l1, 0, 0)
println(s"l1 ${l1.x}, ${l1.next.x}")
val l2 = constructListNode(9999999991)
calculateListNode(l2, 0, 0)

val node = addTwoNumbers(l1, l2)
println(s"node ${node.x}, ${node.next.x}")

calculateListNode(node, 0, 0)
// calculateListNode(l1, 0, 0) + calculateListNode(l2, 0, 0)

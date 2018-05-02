val sl = List("Buy","Rural", "Other", "Land")

val slso = sl.sorted

val splitList = List(slso.slice(0, slso.length-1), slso.slice(slso.length-1, slso.length))

splitList.map(l => l.mkString(", ")).filter(!_.isEmpty).mkString(" and ")





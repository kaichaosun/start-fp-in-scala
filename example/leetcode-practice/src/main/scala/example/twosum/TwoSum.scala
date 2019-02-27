package example.twosum

object TwoSum {
  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    val valueAndIndexArray = nums.zipWithIndex
    val valueAndPairMap = valueAndIndexArray.groupBy(_._1)

    valueAndIndexArray.withFilter(pair => {
      val diff = target - pair._1
      valueAndPairMap.contains(diff) && valueAndPairMap(diff).exists(_._2 != pair._2)
    }).map(_._2)
  }
}

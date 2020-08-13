import kotlinx.coroutines.*

// = = = Key points = = =
fun main()  {

// = = = Key points = = =

// = = = = =  Yield and YieldAll = = = = = =

  val sequenceAll = yieldAllExample()
  // 2
  sequenceAll.forEach {
    println(it)
  }
}
// 3
fun yieldAllExample() = sequence {
  // 4
  yieldAll(listOf("Apple", "Orange", "Banana"))
}






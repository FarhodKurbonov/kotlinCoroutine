import kotlinx.coroutines.*

// = = = Key points = = =
fun main()  {

// = = = Key points = = =
  // = = = = = Explation of Itarable = = = = = =
//  // 1
//  val list = listOf(1, 2, 3)
//  // 2
//  list.filter {
//    // 3
//    print("filter, ")
//    // 4
//    it > 0
//    // 5
//  }.map {
//    // 6
//    print("map, ")
//    // 7
//  }.forEach {
//    // 8
//    print("forEach, ")
//  }

  // = = = = = Explation of Sequence = = = = = =

//  val list = listOf(1, 2, 3)
//  // 1
//  list.asSequence().filter {
//    print("filter, ")
//    it > 0
//  }.map {
//    print("map, ")
//  }.forEach {
//    print("forEach, ")
//  }

// = = = = =  Generators and Sequences = = = = = =
/*  // 1
  val sequence = generatorFib().take(8)
  // 2
  sequence.forEach {
    println("$it")
  }
  // 3
  fun generatorFib() = sequence {
    // 4
    print("Suspending...")

    // 5
    yield(0)
    var cur = 0
    var next = 1
    while (true) {
      // 6
      print("Suspending...")
      // 7
      yield(next)
      val tmp = cur + next
      cur = next
      next = tmp
    }
  }*/
// = = = = =  Yield and YieldAll = = = = = =
  // 1
   val sequence = singleValueExample()

  // 2
  sequence.forEach {
    println(it)
  }



}
// 3
fun singleValueExample() = sequence {
  // 4
  println("Printing first value")
  // 5
  yield("Apple")

  // 6
  println("Printing second value")
  // 7
  yield("Orange")

  // 8
  println("Printing third value")
  // 9
  yield("Banana")
}





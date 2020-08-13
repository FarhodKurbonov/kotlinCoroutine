import kotlinx.coroutines.*

// = = = Key points = = =
//Collection are eagerly evaluated; i.e., all items are operated upon completely before passing the result to the next operator.

//Sequence handles the collection of items in a lazy-evaluated manner; i.e., the items in it are not evaluated until you access them.

//Sequences are great at representing collection where the size isn’t known in advance, like reading lines from a file.

//asSequence() can be used to convert a list to a sequence.

//It is recommended to use simple Iterables in most of the cases, the benefit of using a sequence is only when there is a huge/infinite collection of elements with multiple operations.

//Generators is a special kind of function that can return values and then can be resumed when they’re called again.

//Using Coroutines with Sequence it is possible to implement Generators.

//SequenceScope is defined for yielding values of a Sequence or an Iterator using suspending functions or Coroutines.

//SequenceScope provides yield() and yieldAll() suspending functions to enable Generator function behavior.
fun main()  {

// = = = Key points = = =

// = = = = =  YieldAll - Passing Sequence instead of Iterator= = = = = =

  // 1
  val sequence = sequenceExample().take(100)

  // 2
  sequence.forEach {
    print("$it ")
  }
}
// 3
fun sequenceExample() = sequence {
  // 4
  yieldAll( generateSequence( 2 ) { it*2 } )
}






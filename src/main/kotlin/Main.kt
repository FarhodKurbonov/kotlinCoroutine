import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

// = = = = = = Key points = = = = = =
//  Channels provide the functionality for sending and receiving streams of values.

//  Channel implements both SendChannel and ReceiveChannel interfaces; therefore, it could be used for sending and receiving streams of values.

//  A Channel can be closed. When that happens, you can’t send or receive an element from it.

//  The send() method either adds the value to a channel or suspends the coroutine until there is space in the channel.

//  The receive() method returns a value from a channel if it is available, or it suspends the coroutine until some value is available otherwise.

//  The offer() method can be used as an alternative to send(). Unlike the send() method, offer() doesn’t suspend the coroutine, it returns false instead.
// It returns true in case of a successful operation.

//  poll() similarly to offer() doesn’t suspend the running, but returns null if a channel is empty.

//  Java BlockingQueue has a similar to Kotlin Channel behavior, the main difference is that the current thread gets
// blocked if the operation of inserting or retrieving is unavailable at the moment.
//=====================================================================================================================


// While loop can cause exception due to race condition best approach to create Channel, produce and consume data from channel
//is to use @produe and cousumeEeach functions
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun main()  {
  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")

  fun produceFruits() = GlobalScope.produce<String> {
    for (fruit in fruitArray) {
      send(fruit)

      // Conditional close
      if (fruit == "Pear") {
        // Signal that closure of channel
        close()
      }
    }
  }

  runBlocking {
    val fruits = produceFruits()
    fruits.consumeEach { println(it) }
    println("Done!")
  }



}







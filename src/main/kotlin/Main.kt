import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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


fun main() {
  // 1 A string array of fruit names is created, named fruitArray.
  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes",
      "Strawberry")
  // 2
  val kotlinChannel = Channel<String>()

  // 3
  runBlocking {

    // 4 Producer
    GlobalScope.launch {
      // Send data in channel
      kotlinChannel.send(fruitArray[0])
    }

    // 5 Consumers
    GlobalScope.launch {
      kotlinChannel.consumeEach { value ->
        println("Consumer 1: $value")
      }
    }
    GlobalScope.launch {
      kotlinChannel.consumeEach { value ->
        println("Consumer 2: $value")
      }
    }

    // 6
    println("Press a key to exit...")
    readLine()

    // 7
    kotlinChannel.close()
  }

}








import kotlinx.coroutines.channels.Channel
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

  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")

  val kotlinChannel = Channel<String>()
  // ----- Closing channel on producer site while consumer tries to get value -----
/*
  runBlocking {
    launch {
      for (fruit in fruitArray) {
        // Conditional close
        if (fruit == "Grapes") {
          // Signal that closure of channel
          //Once close is called, all values retrieved after that raise the ClosedReceiveChannelException.
          kotlinChannel.close()
        }

        kotlinChannel.send(fruit)
      }
    }

    repeat(fruitArray.size) {
      try {
        val fruit = kotlinChannel.receive()
        println(fruit)
      } catch (e: Exception) {
        println("Exception raised: ${e.javaClass.simpleName}")
      }
    }
    println("Done!")
  }
*/

  // ----- Closing channel on consumer site while producer tries to send value -----
  runBlocking {
    launch {
      for (fruit in fruitArray) {
        try {
          kotlinChannel.send(fruit)
        } catch (e: Exception) {
          println("Exception raised: ${e.javaClass.simpleName}")
        }
      }

      println("Done!")
    }

    repeat(fruitArray.size - 1) {
      val fruit = kotlinChannel.receive()
      // Conditional close
      if (fruit == "Grapes") {
        // Signal that closure of channel
        kotlinChannel.close()
      }
      println(fruit)
    }
  }

}










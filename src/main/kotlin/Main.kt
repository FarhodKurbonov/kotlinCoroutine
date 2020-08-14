import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach

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
  //The capacity of the channel is 0 (RENDEZVOUS).
  val kotlinChannel = Channel<String>()

  runBlocking {
    launch {
      for (fruit in fruitArray) {
        //Using offer() is similar to send().
        //As soon as the first value("Apple") is sent, the channel is full.
        //Once the channel is full, calls to offer() doesn’t send anything.
        //Instead, it returns false, which is denoted by print statements Banana wasn’t sent and similar statements.
        val wasSent = kotlinChannel.offer(fruit) //<----offer method of Channel
        if (wasSent) {
          println("Sent: $fruit")
        } else {
          println("$fruit wasn’t sent")
        }
      }
      kotlinChannel.close()
    }

    for (fruit in kotlinChannel) {
      println("Received: $fruit")
    }
    println("Done!")
  }

}










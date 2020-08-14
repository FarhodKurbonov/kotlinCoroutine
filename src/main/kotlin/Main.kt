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
  //1 The capacity of the channel is 0 (RENDEZVOUS).
  val kotlinChannel = Channel<String>()

  runBlocking {

    launch {
      for (fruit in fruitArray) {
        if (fruit == "Pear") {
          break
        }
        kotlinChannel.send(fruit)
        println("Sent: $fruit")
      }
    }

    launch {
      repeat(fruitArray.size) {
        //2 “Using poll() is similar to receive().
        //As soon as the first value("Apple") is sent, the channel is full. The consumer then receives the value, after which time the channel is empty again.
        //Another cycle of the above process runs with the second value "Banana".
        //For the third value, "Pear", because of the if check in the for loop, no more items are sent in the channel, i.e., the channel is empty.

        val fruit = kotlinChannel.poll()
        //3 Once the channel is empty, calls to poll() returns null, which is denoted by print statements "Channel is empty".
        if (fruit != null) {
          println("Received: $fruit")
        } else {
          println("Channel is empty")
        }

        delay(500)
      }
      println("Done!")
    }


  }

  // Offer and Poll are non suspendable function. Hence they can be uses without coroutine
  for (fruit in fruitArray) {
    if (fruit == "Pear") {
      break
    }
    kotlinChannel.offer(fruit) //<-- Offer
    println("Sent: $fruit")
  }

  repeat(fruitArray.size) {
    val fruit = kotlinChannel.poll() //<--poll
    if (fruit != null) {
      println("Received: $fruit")
    } else {
      println("Channel is empty")
    }

  }
  println("Done!")


}










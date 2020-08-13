import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel

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

fun main()  {
  //1
  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")
  // 2 Init Producer
  val kotlinChannel = Channel<String>()
  runBlocking() {
    //3 Send data into Channel
    GlobalScope.launch {
      for (fruit in fruitArray) {
        //4
        kotlinChannel.send(fruit)

        //5
        if (fruit == "Pear") {
          //6
          kotlinChannel.close()
        }
      }
    }
    //7 Consumer via If operator <- use this operator because it understands Iterator and sequence under the hood and does it in proficient way
//    for (fruit in kotlinChannel) {
//      println(fruit)
//    }
    //7 Consumer via while operator
    while (!kotlinChannel.isClosedForReceive) {
      val value = kotlinChannel.receive()
      println(value)
    }

    //8
    println("Done")
  }
}







import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

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

data class Fruit(override val name: String, override val color: String) : Item
data class Vegetable(override val name: String, override val color: String) : Item

interface Item {
  val color: String
  val name: String
}

@ExperimentalCoroutinesApi
fun main() {
  // ------------ Helper Methods ------------
  fun isFruit(item: Item): Boolean = (item is Fruit)
  fun isRed(item: Item): Boolean = (item.color == "Red")


  // ------------ Pipeline ------------
  // 1
  fun produceItems() = GlobalScope.produce {
    val itemsArray = ArrayList<Item>()
    itemsArray.add(Fruit("Apple", "Red"))
    itemsArray.add(Vegetable("Zucchini", "Green"))
    itemsArray.add(Fruit("Grapes", "Green"))
    itemsArray.add(Vegetable("Radishes", "Red"))
    itemsArray.add(Fruit("Banana", "Yellow"))
    itemsArray.add(Fruit("Cherries", "Red"))
    itemsArray.add(Vegetable("Broccoli ", "Green"))
    itemsArray.add(Fruit("Strawberry", "Red"))

    // Send each item in the channel
    itemsArray.forEach {
      send(it) //<-- First Channel
    }
  }

  // 2
  fun isFruit(items: ReceiveChannel<Item>) = GlobalScope.produce {
    for (item in items) {
      // Send each item in the channel only if it is a fruit
      if (isFruit(item)) {
        send(item) //<-- Second Channel
      }
    }
  }

  // 3
  fun isRed(items: ReceiveChannel<Item>) = GlobalScope.produce {
    for (item in items) {
      // Send each item in the channel only if it is red in color
      if (isRed(item)) {
        send(item) //<-- Second Channel
      }
    }
  }

  runBlocking {
    // 4 First Channel
    val itemsChannel = produceItems()
    // 5 Passed vegetables and fruits. Returning only fruits as channel
    val fruitsChannel = isFruit(itemsChannel)
    // 6 Passing Fruits channel. Returning red type fruits
    val redChannel = isRed(fruitsChannel)
    // 7
    for (item in redChannel) {
      print("${item.name}, ")
    }
    // 8 It is recommended to cancel all the coroutines for good measure but not neccessary
    redChannel.cancel()
    fruitsChannel.cancel()
    itemsChannel.cancel()

    // 9
    println("Done!")
  }
}








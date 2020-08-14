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
interface Item {
  val name: String
  val color: String
}


// @Predicate is any function with a parameter of generic type E which can return either true or false.
// @Rule is a name for a Pair of a Channel and a Predicate.
// @Pair represents a generic pair of two values.
//The idea is to allow a coroutine to send a value to a specific channel only if its predicate returns true if evaluated for the value itself.
typealias Predicate<E> = (E) -> Boolean
typealias Rule<E> = Pair<Channel<E>, Predicate<E>>

class Demultiplexer<E>(vararg val rules: Rule<E>) {
  suspend fun consume(recv: ReceiveChannel<E>) {
    for (item in recv) {
      //1
      for (rule in rules) {
        //2
        if (rule.second(item)) {
          rule.first.send(item)
        }
      }
    }
    //4
    closeAll()
  }

  private fun closeAll() {
    rules.forEach { it.first.close() }
  }
}

@ExperimentalCoroutinesApi
fun main() {

  data class Fruit(override val name: String, override val color: String) : Item
  data class Vegetable(override val name: String, override val color: String) : Item

  // ------------ Helper Methods ------------
  fun isFruit(item: Item) = item is Fruit

  fun isVegetable(item: Item) = item is Vegetable


  // 1 Create a produceItems function for producing a finite number of items, which are either a fruit or vegetable.
  fun produceItems(): ArrayList<Item> {
    val itemsArray = ArrayList<Item>()
    itemsArray.add(Fruit("Apple", "Red"))
    itemsArray.add(Vegetable("Zucchini", "Green"))
    itemsArray.add(Fruit("Grapes", "Green"))
    itemsArray.add(Vegetable("Radishes", "Red"))
    itemsArray.add(Fruit("Banana", "Yellow"))
    itemsArray.add(Fruit("Cherries", "Red"))
    itemsArray.add(Vegetable("Broccoli", "Green"))
    itemsArray.add(Fruit("Strawberry", "Red"))
    itemsArray.add(Vegetable("Red bell pepper", "Red"))
    return itemsArray
  }


  runBlocking {

    // 2 Initialize the destination channel.
    val destinationChannel = Channel<Item>()

    // 3 Create the fruitsChannel channel for items that are fruits and vegetablesChannel channel for items that are vegetables.
    val fruitsChannel = Channel<Item>()
    val vegetablesChannel = Channel<Item>()

    // 4 Launch the coroutine that inserts the items that are fruits into the fruitsChannel channel.
    launch {
      produceItems().forEach {
        if (isFruit(it)) {
          delay(500)
          fruitsChannel.send(it)
        }
      }
    }

    // 5 Launch the coroutine that inserts the items that are vegetables into the vegetablesChannel channel.
    launch {
      produceItems().forEach {
        if (isVegetable(it)) {
          delay(500)
          vegetablesChannel.send(it)
        }
      }
    }


    // 6 Here is where the multiplexing is happening for items that are fruits that are sent into the destination channel.
    launch {
      for (item in fruitsChannel) {
        destinationChannel.send(item)
      }
    }

    // 7 Here is where the multiplexing is happening for items that are vegetables that are sent into the destination channel.
    launch {
      for (item in vegetablesChannel) {
        destinationChannel.send(item)
      }
    }

    // 8 You consume the destination channel and print a label depending on the type of item.
    destinationChannel.consumeEach {
      if (isFruit(it)) {
        println("${it.name} is a fruit")
      } else if (isVegetable(it)) {
        println("${it.name} is a vegetable")
      }
    }

    // 9 You cancel all the coroutines when there’s nothing more to consume.
    coroutineContext.cancelChildren()
  }

}










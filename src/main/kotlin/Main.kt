import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach

// = = = = = = Key points = = = = = =
//ConflatedBroadcastChannel only emits the most recently sent item while the older items are lost.
//Also, any future subscribers to this channel will receive the item that was most recently emitted.
//=====================================================================================================================


@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun main() {
  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")
  //1
  val kotlinChannel = ConflatedBroadcastChannel<String>()
  runBlocking {
    kotlinChannel.apply {
      send(fruitArray[0])
      send(fruitArray[1])
      send(fruitArray[2])
    }
    // 3
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

    // 4
    kotlinChannel.apply {
      send(fruitArray[3])
      send(fruitArray[4])
    }

    // 5
    println("Press a key to exit...")
    readLine()

    // 6
    kotlinChannel.close()
  }
}








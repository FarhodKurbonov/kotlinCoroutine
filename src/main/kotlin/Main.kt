import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.consumeEach

// = = = = = = Key points = = = = = =
//With channels, if you have many receivers waiting to receive items from the channel, the emitted item will be consumed by the first receiver and all other receivers will not get the item individually.
//BroadcastChannel enables many subscribed receivers to consume all items sent in the channel.
//ConflatedBroadcastChannel enables many subscribed receivers to consume the most recently sent item provided the receiver consumes items slower.
//Subject from RxJava is the dual of the BroadcastChannel in behavior.
//BehaviorSubject from RxJava is the dual of ConflatedBroadcastChannel in behavior
//=====================================================================================================================

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
fun main() {
  // 1 A string array of fruit names is created, named fruitArray.
  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")
  val kotlinChannel = BroadcastChannel<String>(3)

  runBlocking {
    //2 Feed channel
    kotlinChannel.apply {
      send(fruitArray[0])
      send(fruitArray[1])
      send(fruitArray[2])
    }
    //3 “Start consuming items from the channel. Consumer 1
    GlobalScope.launch {
      //4 “Here, a subscription is opened on the kotlinChannel using the openSubscription() function — i.e.,
      //start listening to values being sent in the kotlinChannel.
      kotlinChannel.consumeEach{ value  ->
        //5 “Iterate over all the values in the channel and print them out
          println("Consumer 1: $value")

      }
      //6 When finished iterating over the values in the channel, the subscription is closed —
      //i.e., stop listening to values being sent in the kotlinChannel.
    }
    // Consumer 2
    GlobalScope.launch {
      kotlinChannel.consumeEach { value ->
          println("Consumer 2: $value")
      }
    }
    //7 Now that the subscription has been obtained on the kotlinChannel, send two more values in the kotlinChannel.
    kotlinChannel.apply {
      send(fruitArray[3])
      send(fruitArray[4])
    }
    //8
    println("Press a key to exit...")
    readLine()

    //9
    kotlinChannel.close()
  }

}








import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

// = = = = = = Key points = = = = = =
//Multi processing communication using PRODUCER-CONSUMER approach to broadcast message
//=====================================================================================================================


@ExperimentalCoroutinesApi
fun main() {
  val receivedChannel = GlobalScope.produce(capacity = 10) {
    while (isActive) {
      val number = Random.nextInt(0, 20)
      send(number)
      println("$number send")

    }
  }

  GlobalScope.launch {
    receivedChannel.consumeEach { println("Consumer 1: $it received") }
  }

  GlobalScope.launch {
    receivedChannel.consumeEach { println("Consumer 2: $it received") }
  }

  Thread.sleep(30L)
}









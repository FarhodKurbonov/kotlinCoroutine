import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.awt.event.FocusEvent
import kotlin.random.Random

// = = = = = = Key points = = = = = =

//=====================================================================================================================
//You create a simple implementation of the CompletionHandler, which just prints a Completed!
//message when the actor is complete. This is happening when close() is invoked on its SendChannel
object completionHandler: CompletionHandler {
  override fun invoke(cause: Throwable?) {
    println("Completed!")
  }
}

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
fun main() {
//2 Сreate the actor passing a capacity of 10 and the reference to the CompletionHandler.
  val actor = GlobalScope.actor<String>(
      onCompletion = completionHandler
      ) {
    // consumer logic
    channel.consumeEach {
      println("$it has been received")
    }
  }
  //4 Simple loop, which offers 10 values into the channel for the actor
  GlobalScope.launch {

    (1..10).forEach{
      println("$it send")
      actor.send( it.toString())
    }
    //5 “sent all your values and you can close the actor.
    actor.close()
  }

  //6
  Thread.sleep(1500)
}









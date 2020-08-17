import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.awt.event.FocusEvent
import kotlin.random.Random

// = = = = = = Key points = = = = = =


//=====================================================================================================================


@FlowPreview
fun main() {
 val flowOfString = flow {
   for (number in 0..100) {
     emit("Emitting: $number")
   }
 }
 GlobalScope.launch{
   flowOfString
       .map { it.split(" ") }
       .map { it.last() }
       .debounce(100)
       .collect { value ->
          println(value)
        }
 }
  Thread.sleep(1000)
}












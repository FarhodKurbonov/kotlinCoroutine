import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.awt.event.FocusEvent
import kotlin.random.Random

// = = = = = = Key points = = = = = =
//Produce-consumer pattern and the actor model are tried and tested mechanisms for multi-threading.
//Producer-consumer relationships are one-to-many, where you can consume the events from multiple places.
//The actor model is a way to share data in a multithread environment using a dedicated queue.
//The actor model allows you to offload large amounts of work to many smaller constructs.
//Actors have a many-to-one relationship, since you can send events from multiple places, but they all end up in one actor.
//Each actor can create new actors, delegating and offloading work.
//Building actors using threads can be expensive, which is where coroutines come in handy.
//Actors can be arranged to run in sequential order, or to run in parallel

//---Scenario----
//The actor model, however, relies on delegating excess work to others. So, for example, if you’re building a robot-powered-storage system,
//where everything is organized by robots, you have to find a way to optimize the workload.
//If a certain robot has too much to carry around, it can pass some of its work on to a different robot.
//And if that second robot has too much work, it can pass it to a third one, and so forth.
//=====================================================================================================================

data class Package(public val id: Int, public val name: String)

fun main() {
  val items = listOf(
      Package(1, "coffee"),
      Package(2, "chair"),
      Package(3, "sugar"),
      Package(4, "t-shirts"),
      Package(5, "pillowcases"),
      Package(6, "cellphones"),
      Package(7, "skateboard"),
      Package(8, "cactus plants"),
      Package(9, "lamps"),
      Package(10, "ice cream"),
      Package(11, "rubber duckies"),
      Package(12, "blankets"),
      Package(13, "glass")
  )

  val initialRobot = WarehouseRobot(1, items)

  initialRobot.organizeItems()
  Thread.sleep(5000)
}

class WarehouseRobot(private val id: Int, private var packages: List<Package>) {
  companion object{
    private const val ROBOT_CAPACITY = 3
  }

  @ObsoleteCoroutinesApi
  fun organizeItems() {
    val itemsToProcess = packages.take(ROBOT_CAPACITY)
    val leftoverItems = packages.drop(ROBOT_CAPACITY)

    packages = itemsToProcess
    val packageIds = packages.map { it.id }.fold("") {acc, item -> "$acc $item"}
    processItems(itemsToProcess)

    if (leftoverItems.isNotEmpty()) {
      GlobalScope.launch {
        val helperRobot = WarehouseRobot(id.inc(), leftoverItems)
        helperRobot.organizeItems()
      }

    }
    processItems(itemsToProcess)
    println("Robot #$id processed following packages:$packageIds")
  }

  @ObsoleteCoroutinesApi
  private fun processItems(items: List<Package>) {
     val actor = GlobalScope.actor<Package> (capacity = ROBOT_CAPACITY){
       var hasProcessedItems = false

       while (packages.isNotEmpty()){
         val currentPackage = poll()

         currentPackage?.run {
           organize(this)
           packages -= currentPackage
           hasProcessedItems = true
         }
         if (hasProcessedItems && currentPackage == null) {
           cancel()
         }
       }

     }
    items.forEach {
      actor.offer(it)
    }
  }

}

private fun organize(warehousePackage: Package) {
  println("Organized package ${warehousePackage.id}: ${warehousePackage.name}")
}










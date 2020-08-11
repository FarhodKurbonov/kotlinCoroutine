import kotlinx.coroutines.*
import main.CoroutineContextProviderImpl
import main.*
import scopeProvider.CustomScope
import java.util.concurrent.Executors

// = = = Key points = = =
//• important concepts in computing, when using execution algorithms, is scheduling and context switching.
//• Scheduling takes care of resource management by coordinating threading and the lifecycle of processes.
//• To communicate thread and process states in computing and task execution, the system uses context switching and dispatching.
//• Context switching helps the system store thread and process state, so that it can switch between tasks which need execution.
//• Dispatching handles which tasks get resources at which point in time.
//• ContinuationInterceptors, which take care of the input/output of threading, and the main and background threads are provided through the Dispatchers class.
//• Dispatchers can be confined and unconfined, where being confined or not relates to using a fixed threading system.
//• There are four main dispatchers: Default, IO, Main and Unconfined.
//• Using the Executors class you can create new thread pools to use for your
//coroutine work.

fun main() {

  //= = = Unconfined Dispatcher = = =
  GlobalScope.launch(context = Dispatchers.Unconfined) {
    println(Thread.currentThread().name) }
  Thread.sleep(50)

  //= = = stealing Executor = = =
//  val executorDispatcher = Executors
//      .newWorkStealingPool()
//      .asCoroutineDispatcher()
//

//    GlobalScope.launch(context = executorDispatcher) { println(Thread.currentThread().name) }
//    Thread.sleep(50)


}


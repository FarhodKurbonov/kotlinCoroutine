import kotlinx.coroutines.*

// = = = Key points = = =
//• When the parent coroutine is canceled, all of its children are recursively canceled, too.
//• CancellationException is not printed to the console/log by the default uncaught exception handler.
//• Using the withTimeout function, you can terminate a long-running coroutine after a set time has elapsed.

fun main() = runBlocking {

  //=============== Cancel Coroutine ===================
/*  val job = launch {
    repeat(1000) { i ->
      println("$i. Crunching numbers [Beep.Boop.Beep]...")
      delay(500L)
    }
  }
  delay(1300L) // delay a bit
  println("main: I am tired of waiting!")
  job.cancel() //cancels the job
  job.join() // waits for job’s completion

  println("main: Now I can quit.")*/

  //============ CancellationException ============
/*  val handler = CoroutineExceptionHandler{_, exception ->
    println("Caught original $exception")
  }

  val parentJob = GlobalScope.launch (handler) {
    val childJob = launch {
      //Sub-child job
      launch {
        //Sub-child job
        launch {
          throw IOException()
        }
      }
    }
    try {
      childJob.join()
    } catch (e: CancellationException) {
      println("Rethrowing CancellationException" + " with original cause")
      throw e
    }
  }

  parentJob.join()*/

//======= “Join” =============

/*  val job = launch {
  println("Crunching numbers [Beep.Boop.Beep]...")
  delay(500L)
}

  // waits for job’s completion
  job.join()
  println("main: Now I can quit.")*/

  //======= “Join all Coroutine Example” =============
  /*val jobOne = launch {
  println("Job 1: Crunching numbers [Beep.Boop.Beep]...")
  delay(500L)
}

  val jobTwo = launch {
    println("Job 2: Crunching numbers [Beep.Boop.Beep]...")
    delay(500L)
  }

  // waits for both the jobs to complete
  joinAll(jobOne, jobTwo)
  println("main: Now I can quit.")*/

  //======= “cancelAndJoin “cancels the job and waits for job’s completion” ========
/*  val job = launch {
    while (this.coroutineContext.isActive) {
      println("Crunching numbers [Beep.Boop.Beep]...")
      delay(500)
    }
  }

  job.invokeOnCompletion { exception ->
    println("Job exception has been caught: ${exception?.message}")
  }
  delay(1300L) // delay a bit
  println("main: I am tired of waiting!")

  // cancels the job and waits for job’s completion
  job.cancelAndJoin() //<-- Main thread waits until job is completed
  //job.cancel() //<-- Main thread does not wait until job is completed

  println("job isActive: ${job.isActive}")
  println("main: Now I can quit.")*/

//======= Multiple child coroutines cancelling========
/*  val parentJob = launch {
  val childOne = launch {
    repeat(1000) { i ->
      println("Child Coroutine 1: $i. Crunching numbers [Beep.Boop.Beep]...")
      delay(500L)
    }
  }

  // Handle the exception thrown from `launch`
  // coroutine builder
  childOne.invokeOnCompletion { exception ->
    println("Child One: ${exception?.message}")
  }

  val childTwo = launch {
    repeat(1000) { i ->
      println("Child Coroutine 2: $i. Crunching numbers [Beep.Boop.Beep]...")
      delay(500L)
    }
  }

  // Handle the exception thrown from `launch`
  // coroutine builder
  childTwo.invokeOnCompletion { exception ->
    println("Child Two: ${exception?.message}")
  }
}
  delay(1200L)

  println("Calling cancelChildren() on the parentJob")
  parentJob.cancelChildren()

  println("parentJob isActive: ${parentJob.isActive}")*/

//= = = = = = = Wait Timeout = = = = = = = =
/*//  try {
    // ---- Returns an exception once timeout is exceed ----
//    val job = withTimeout(1500L) {
//      repeat(1000) { i ->
//        println("$i. Crunching numbers [Beep.Boop.Beep]...")
//        delay(500L)
//      }
//    }
    // ---- Does not returns an exception once timeout is exceed ----
//    val result = withTimeoutOrNull(1500L) {
//      repeat(1000) { i ->
//        println("$i. Crunching numbers [Beep.Boop.Beep]...")
//        delay(500L)
//      }
//    }
//    "Done"
//    // Result will be `null`
//    println("Result is $result")
//
//  } catch (e: TimeoutCancellationException) {
//    println("Caught ${e.javaClass.simpleName}")
//  }*/


}
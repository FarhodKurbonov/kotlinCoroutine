import kotlinx.coroutines.runBlocking
import java.io.IOException
import kotlin.coroutines.suspendCoroutine

// = = = Key points = = =
//• If an exception is thrown during an asynchronous block, it is not actually thrown immediately. Instead, it will be
  //thrown at the time you call await on the Deferred object that is returned.
//• To ignore any exceptions, launch the parent coroutine with the async function; however, if required to handle,
  //the exception uses a try-catch block on the await() call on the Deferred object returned from async coroutine builder.
//• When using launch builder the exception will be stored in a Job object. To retrieve it, you can use the invokeOnCompletion helper function.
//• Add a CoroutineExceptionHandler to the parent coroutine context to catch unhandled exceptions and handle them.
//• CoroutineExceptionHandler is invoked only on exceptions that are not expected to be handled by the user; registering
//it in an async coroutine builder or the like has no effect.
//• When multiple children of a coroutine throw an exception, the general rule is the first exception wins.
//• Coroutines provide a way to wrap callbacks to hide the complexity of the asynchronous code handling away from the caller
  //via a suspendCoroutine suspending function, which is included in the coroutine library.

fun main() = runBlocking {
//  Handling exceptions
  val asyncJob = GlobalScope.launch {
    println("1. Exception created via launch coroutine")
    throw IndexOutOfBoundsException()
  }
  asyncJob.join()
  println("2. Joined failed Job")
  val deferred = GlobalScope.async {
    println("3. Exception created via sync coroutine")

    //Noting is printed, relying on user to call await
    throw ArithmeticException()
  }

  try {
    deferred.await()
    println("4. Unreachable this statement is never executed")
  } catch (e: Exception) {
    println("5. Caught ${e.javaClass.simpleName}")
  }

//  ======= CorouitineExceptionHandler Example =======
//  val job = GlobalScope.launch {
//    println("1. Exception created via launch coroutine")
//    // Will NOT be handled by
//    // Thread.defaultUncaughtExceptionHandler
//    // since it is being handled later by `invokeOnCompletion`
//    delay(200)
//    throw IndexOutOfBoundsException()
//  }
//
//    //Handle the exception thrown from `launch` coroutine builder
//    job.invokeOnCompletion { exception ->
//      println("2. Caught $exception") }
//  //This suspends coroutine until this job is complete.
//  job.join()

//  ======= GlobalExceptionHandler Example =======

//  // 1
//  val exceptionHandler = CoroutineExceptionHandler { _, exception: Throwable ->
//    println("Caught $exception")
//  }
//  // 2
//  val job = GlobalScope.launch(exceptionHandler) {
//    throw AssertionError("My Custom Assertion Error!")
//  }
//  // 3
//  val deferred = GlobalScope.async(exceptionHandler) { // Nothing will be printed,
//    // relying on user to call deferred.await()
//    throw ArithmeticException()
//  }
//  // 4
//  // This suspends current coroutine until all given jobs are complete.
//  joinAll(job, deferred)

//  ======= Handling exceptions for a specific coroutine ==========
  //KEY POINT If you do not wait deffered coroutine it just swallow exception!!

  // Set this to ’true’ to call await on the deferred variable
//  val callAwaitOnDeferred = true
//  val deferred = GlobalScope.async {
//    // This statement will be printed with or without a call to await()
//    println("Throwing exception from async")
//    throw ArithmeticException("Something Crashed")
//  }
//  if (callAwaitOnDeferred) {
//    try {
//      deferred.await() //<--Nothing is printed, relying on a call to await()
//    } catch (e: ArithmeticException) {
//      println("Caught ArithmeticException")
//    }
//  }
// ======= Handling mul+ple child corou+ne excep+ons =====
  // If you set a GLOBAL CoroutineExceptionHandler, it will manage only the first exception, suppressing all the others
//  val handler = CoroutineExceptionHandler { _, exception ->
//    println("Caught $exception with suppressed " + "${exception.suppressed?.contentToString()}")
//  }
//
//  // Parent Job
//  val parentJob = GlobalScope.launch(handler) { // Child Job 1
//    launch {
//      try {
//        delay(Long.MAX_VALUE)
//      } catch (e: Exception) {
//        println("${e.javaClass.simpleName} in Child Job 1")
//      } finally {
//        throw ArithmeticException()
//      }
//    }
//    // Child Job 2
//    launch {
//      delay(100)
//      throw IllegalStateException()
//    }
//    // Delaying the parentJob
//    delay(Long.MAX_VALUE)
//  }
//  // Wait until parentJob completes
//  parentJob.join()

  // ======= Catching error via callback wrapping - onSuccess and onFailure   =====
//  try {
//    val data = getDataAsync()
//    println("Data received: $data")
//  } catch (e: Exception) {
//    println("Caught ${e.javaClass.simpleName}")
//  }
}

// Method to simulate a long running task
//fun getData(asyncCallback: AsyncCallback) {
//  // Flag used to trigger an exception
//  val triggerError = true
//  try {
//    // Delaying the thread for 3 seconds Thread.sleep(3000)
//    if (triggerError) {
//      throw IOException()
//    } else {
//      // Send success
//      asyncCallback.onSuccess("[Beep.Boop.Beep]")
//    }
//  } catch (e: Exception) {
//    // send error
//    asyncCallback.onError(e)
//  }
//}
//
//suspend fun getDataAsync(): String {
//  return suspendCoroutine { continuation ->
//    getData(object : AsyncCallback {
//      override fun onSuccess(result: String) {
//        continuation.resumeWith(Result.success(result))
//      }
//
//      override fun onError(e: Exception) {
//        continuation.resumeWith(Result.failure(e))
//      }
//    })
//  }
//}
//
//// Callback
//interface AsyncCallback {
//  fun onSuccess(result: String)
//  fun onError(e: Exception)
//}
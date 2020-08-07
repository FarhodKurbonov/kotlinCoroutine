package scopeProvider

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class CustomScope (private var backgroundContextProvider: CoroutineContext, private var exceptionHdl: CoroutineExceptionHandler): CoroutineScope {

  private  var emptyParentJob = Job()

  override val coroutineContext: CoroutineContext get() = backgroundContextProvider + emptyParentJob + exceptionHdl

  fun onStart() {
    emptyParentJob = Job()
    //todo: additinial initilization stuff
  }

  fun onStop(){
    emptyParentJob.cancel()
  }

}
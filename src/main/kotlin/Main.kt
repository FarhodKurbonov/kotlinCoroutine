import kotlinx.coroutines.*
import main.CoroutineContextProviderImpl
import main.*
import scopeProvider.CustomScope

fun main() {
  //Define your background process
  val backgroundContextProvider = CoroutineContextProviderImpl(Dispatchers.Default).context()
  //Create your fully controllable coroutine scope by providing context and exception handler
  val mainScope = CustomScope(backgroundContextProvider, coroutineErrorHandler)
  //Launch your async job in your scope and manage it
  multipleAwaitExample(mainScope)

  Thread.sleep(5000)
//No need to send request to API so cancel Job
  //mainScope.onStop()




}

fun multipleAwaitExample( mainScope: CoroutineScope) {
  val userId = 992 // the ID of the user we want
  val job = mainScope.launch {
    val userDeferred = getUserByIdFromNetwork(userId, this)
    val usersFromFileDeferred = readUsersFromFile("users.txt", this)

    //todo: try to cause exception
    //val usersFromFileDeferred = readUsersFromFile("user2s.txt", this)

    if (isActive) {
      println("Finding user")
      val userStoredInFile = checkUserExists(
          userDeferred.await(),
          usersFromFileDeferred.await()
      )

      if (userStoredInFile) {
        println("Found user in file!")
      }
    }
  }

}
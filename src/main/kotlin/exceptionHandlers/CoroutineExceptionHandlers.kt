package main

import kotlinx.coroutines.CoroutineExceptionHandler

val coroutineErrorHandler = CoroutineExceptionHandler {
  coroutineContext,
  throwable -> println("Sorry the have been issue try to check file path") // we just print the error here
}

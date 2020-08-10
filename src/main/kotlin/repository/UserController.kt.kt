package main

import kotlinx.coroutines.*
import scopeProvider.CustomScope
import java.io.File


  public fun checkUserExists(user: User, users: List<User>): Boolean {
    return user in users
  }

  //Simulate API Call
  public fun getUserByIdFromNetwork(
      userId: Int,
      scope: CoroutineScope) =
      scope.async {
        if (!scope.isActive) {
          return@async User(0, "", "")
        }
        println("Retrieving user from network")
        delay(3000)
        println("Still in the coroutine")

        User(userId, "Filip", "Babic") // we simulate the network call
      }

  //Simulate DB Call
  public fun readUsersFromFile(
      filePath: String,
      scope: CoroutineScope) =
      scope.async {
        println("Reading the file of users")
        delay(1000)

        if (!scope.isActive) {
          return@async emptyList<User>()
        }

        File(filePath)
            .readLines()
            .asSequence()
            .filter { it.isNotEmpty() }
            .map {
              val data = it.split(" ") // [id, name, lastName]

              if (data.size == 3) data else emptyList()
            }
            .filter {
              it.isNotEmpty()
            }
            .map {
              val userId = it[0].toInt()
              val name = it[1]
              val lastName = it[2]

              User(userId, name, lastName)
            }
            .toList()
      }

  data class User(val id: Int, val name: String, val lastName: String)

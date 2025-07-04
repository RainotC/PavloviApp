package com.example.pavlovapp.feature_todolist.data.local.repo

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.pavlovapp.feature_todolist.data.di.IoDispatcher
import com.example.pavlovapp.feature_todolist.domain.model.ToDoTask
import com.example.pavlovapp.feature_todolist.domain.repo.ToDoTaskListRepo
import kotlinx.coroutines.CoroutineDispatcher
import com.example.pavlovapp.feature_todolist.data.local.ToDoTaskDao
import com.example.pavlovapp.feature_todolist.data.local.mapper.toLocalToDoTask
import com.example.pavlovapp.feature_todolist.data.local.mapper.toLocalToDoTaskListFromRemote
import com.example.pavlovapp.feature_todolist.data.local.mapper.toRemoteToDoTask
import com.example.pavlovapp.feature_todolist.data.local.mapper.toToDoTask
import com.example.pavlovapp.feature_todolist.data.local.mapper.toToDoTaskListFromLocal
import com.example.pavlovapp.feature_todolist.data.remote.ToDoTaskApi
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.UnknownHostException

class ToDoTaskListRepoImpl(
        private val dao: ToDoTaskDao,
        private val api: ToDoTaskApi,
        @IoDispatcher private val dispatcher: CoroutineDispatcher
        ):ToDoTaskListRepo {
    override suspend fun getAllToDoTasks(): List<ToDoTask> {
        getAllToDoTasksFromRemote()
        return dao.getAllTasks().toToDoTaskListFromLocal()
    }

    override suspend fun getAllToDoTasksFromLocal(): List<ToDoTask> {
        return dao.getAllTasks().toToDoTaskListFromLocal()
    }
    override suspend fun getAllToDoTasksFromRemote() {
        return withContext(dispatcher) {
           try{
               refreshRoomCache()
           }catch( e: Exception) {
               when(e){
                   is UnknownHostException, is ConnectException, is HttpException  -> {
                       Log.e("HTTP", "Error: No data from Remote")
                       if(isCacheEmpty()) {
                           Log.e("Cache", "Error: No data from local Room cache")
                           throw Exception("Error: Device offlice and\n no data in local Room cache")
                       }
                   } else -> throw e
               }
           }
        }

    }


    private suspend fun refreshRoomCache() {
        val remoteTasks = api.getToDoTasks()?.filterNotNull() ?: emptyList()
        dao.addAllTasks(remoteTasks.toLocalToDoTaskListFromRemote())
    }


    private fun isCacheEmpty(): Boolean {
        var empty = true
        if(dao.getAllTasks().isNotEmpty()) empty = false
        return empty
    }

    override suspend fun getToDoTaskById(id: Int): ToDoTask? {
        return dao.getTaskById(id)?.toToDoTask()
    }

    override suspend fun addToDoTask(toDoTask: ToDoTask) {
        val newId = dao.addTask(toDoTask.toLocalToDoTask())
        val id = newId.toInt()
        val url = "toDoTasks/$id.json"
        api.addToDoTask(url, toDoTask.toRemoteToDoTask().copy(id = id))
    }

    override suspend fun updateToDoTask(toDoTask: ToDoTask) {
        dao.addTask(toDoTask.toLocalToDoTask())
        api.updateToDoTask(toDoTask.id, toDoTask.toRemoteToDoTask())
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun deleteToDoTask(toDoTask: ToDoTask) {
        try{
            // Delete from local database first
            dao.deleteTask(toDoTask.toLocalToDoTask())
            
            // Then delete from remote API
            val response = api.deleteToDoTask(toDoTask.id)
            if(response.isSuccessful) {
                Log.i("API_DELETE", "Response successful")
            } else {
                Log.e("API_DELETE", "Response unsuccessful: ${response.message()}")
                // If remote delete fails, we could consider restoring the local task
                // For now, we'll just log the error
            }
        }catch( e: Exception) {
            when(e){
                is UnknownHostException, is ConnectException, is HttpException  -> {
                    Log.e("HTTP", "Error: Could not delete task from remote")
                    // Task was already deleted from local database
                } else -> {
                    Log.e("DELETE", "Unexpected error: ${e.message}")
                    throw e
                }
            }
        }
    }
}
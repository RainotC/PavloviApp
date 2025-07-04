package com.example.pavlovapp.feature_todolist.domain.repo

import com.example.pavlovapp.feature_todolist.domain.model.ToDoTask

interface ToDoTaskListRepo {
    suspend fun getAllToDoTasks(): List<ToDoTask>
    suspend fun getAllToDoTasksFromLocal(): List<ToDoTask>
    suspend fun getAllToDoTasksFromRemote()
    suspend fun getToDoTaskById(id: Int): ToDoTask?
    suspend fun addToDoTask(toDoTask: ToDoTask)
    suspend fun updateToDoTask(toDoTask: ToDoTask)
    suspend fun deleteToDoTask(toDoTask: ToDoTask)
}
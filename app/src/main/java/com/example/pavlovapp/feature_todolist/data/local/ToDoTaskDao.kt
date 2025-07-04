package com.example.pavlovapp.feature_todolist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pavlovapp.feature_todolist.data.local.dto.LocalToDoTask

@Dao
interface ToDoTaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<LocalToDoTask>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): LocalToDoTask?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addTask(task: LocalToDoTask):Long

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addAllTasks(tasks: List<LocalToDoTask>): List<Long>

    @Delete
    suspend fun deleteTask(task: LocalToDoTask)
}
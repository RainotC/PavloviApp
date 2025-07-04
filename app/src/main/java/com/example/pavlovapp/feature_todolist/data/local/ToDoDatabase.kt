package com.example.pavlovapp.feature_todolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pavlovapp.feature_todolist.data.local.dto.LocalToDoTask

@Database(
    entities = [LocalToDoTask::class],
    version = 1,
    exportSchema = false
)
abstract class ToDoDatabase: RoomDatabase() {
    abstract val todoDao: ToDoTaskDao

    companion object {
        const val DATABASE_NAME = "todo_db"
    }
}
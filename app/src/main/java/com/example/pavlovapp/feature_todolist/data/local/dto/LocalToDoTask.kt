package com.example.pavlovapp.feature_todolist.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class LocalToDoTask(
    val title: String,
    val timestamp: Long,
    val deadline:Long,
    var completed: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int?
)
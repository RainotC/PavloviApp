package com.example.pavlovapp.feature_todolist.domain.model

data class ToDoTask(
    val title: String,
    val timestamp: Long,
    val deadline:Long,
    var completed: Boolean,
    val id: Int?
)
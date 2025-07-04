package com.example.pavlovapp.feature_todolist.presentation.todolist
import com.example.pavlovapp.feature_todolist.domain.util.SortingDirection
import com.example.pavlovapp.feature_todolist.domain.util.ToDoTaskOrder
data class ToDoListState(
    val toDoTasks: List<com.example.pavlovapp.feature_todolist.domain.model.ToDoTask> = emptyList(),
    val isLoading: Boolean = false,
    val toDoTaskOrder: ToDoTaskOrder = ToDoTaskOrder.Deadline(SortingDirection.Up),
    val error: String? = null
)

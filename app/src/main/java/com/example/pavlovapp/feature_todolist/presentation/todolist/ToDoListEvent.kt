package com.example.pavlovapp.feature_todolist.presentation.todolist

import com.example.pavlovapp.feature_todolist.domain.model.ToDoTask
import com.example.pavlovapp.feature_todolist.domain.util.ToDoTaskOrder

sealed class ToDoListEvent{
    data class SortTasks(val toDoTaskOrder: ToDoTaskOrder): ToDoListEvent()
    data class DeleteTask(val todo: ToDoTask): ToDoListEvent()
    data class ToggleTaskCompleted(val todo: ToDoTask): ToDoListEvent()
    object RestoreTask: ToDoListEvent()
}

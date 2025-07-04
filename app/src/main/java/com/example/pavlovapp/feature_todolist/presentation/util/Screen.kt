package com.example.pavlovapp.feature_todolist.presentation.util

sealed class Screen(val route: String) {
    object ToDoListScreen: Screen("todo_list_screen")
    object ToDoUpdateScreen: Screen("todo_update_screen")

}

package com.example.pavlovapp.feature_todolist.domain.util

sealed class ToDoTaskOrder(
    val sortingDirection: SortingDirection
){
    class Title(sortingDirection: SortingDirection) : ToDoTaskOrder(sortingDirection)
    class Time(sortingDirection: SortingDirection) : ToDoTaskOrder(sortingDirection)
    class Deadline(sortingDirection: SortingDirection) : ToDoTaskOrder(sortingDirection)
    class Completed(sortingDirection: SortingDirection) : ToDoTaskOrder(sortingDirection)

    fun copy(sortingDirection: SortingDirection): ToDoTaskOrder {
        return when(this) {
            is Title -> Title(sortingDirection)
            is Time -> Time(sortingDirection)
            is Deadline -> Deadline(sortingDirection)
            is Completed -> Completed(sortingDirection)
        }
    }
}
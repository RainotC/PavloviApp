package com.example.pavlovapp.feature_todolist.domain.util

sealed class SortingDirection{
    object Up : SortingDirection()
    object Down : SortingDirection()
}
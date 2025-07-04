package com.example.pavlovapp.feature_todolist.data.remote

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
data class RemoteToDoTask(
    @SerializedName("title")
    val title: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("deadline")
    val deadline:Long,
    @SerializedName("completed")
    var completed: Boolean,
    @SerializedName("id")
    val id: Int?
)
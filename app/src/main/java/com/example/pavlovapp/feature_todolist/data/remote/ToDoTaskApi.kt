package com.example.pavlovapp.feature_todolist.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Url
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ToDoTaskApi {
    @GET("todo.json")
    suspend fun getToDoTasks(): List<RemoteToDoTask>?

    @GET("todo.json?orderBy=\"id\"")
    suspend fun getToDoTasksOrderedById(@Query("equalTo") id: Int?): Map<String, RemoteToDoTask>?

    @PUT
    suspend fun addToDoTask(@Url url: String, @Body updatedToDoTask: RemoteToDoTask): retrofit2.Response<Unit>

    @DELETE("todo/{id}.json")
    suspend fun deleteToDoTask(@Path("id") id: Int?): retrofit2.Response <Unit>

    @PUT("todo/{id}.json")
    suspend fun updateToDoTask(@Path("id") id: Int?, @Body updatedToTask: RemoteToDoTask): retrofit2.Response<Unit>
}
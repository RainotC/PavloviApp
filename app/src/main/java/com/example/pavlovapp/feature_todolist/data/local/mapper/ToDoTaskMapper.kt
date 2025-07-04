package com.example.pavlovapp.feature_todolist.data.local.mapper

import com.example.pavlovapp.feature_todolist.data.local.dto.LocalToDoTask
import com.example.pavlovapp.feature_todolist.domain.model.ToDoTask
import com.example.pavlovapp.feature_todolist.data.remote.RemoteToDoTask

fun ToDoTask.toLocalToDoTask(): LocalToDoTask {
    return LocalToDoTask(
        title = this.title,
        timestamp = this.timestamp,
        deadline = this.deadline,
        completed = this.completed,
        id = this.id
    )
}

fun ToDoTask.toRemoteToDoTask(): RemoteToDoTask {
    return RemoteToDoTask(
        title = this.title,
        timestamp = this.timestamp,
        deadline = this.deadline,
        completed = this.completed,
        id = this.id
    )
}

fun LocalToDoTask.toToDoTask(): ToDoTask {
    return ToDoTask(
        title = this.title,
        timestamp = this.timestamp,
        deadline = this.deadline,
        completed = this.completed,
        id = this.id
    )
}

fun LocalToDoTask.toRemoteToDoTask(): RemoteToDoTask {
    return RemoteToDoTask(
        title = this.title,
        timestamp = this.timestamp,
        deadline = this.deadline,
        completed = this.completed,
        id = this.id
    )
}

fun RemoteToDoTask.toToDoTask(): ToDoTask {
    return ToDoTask(
        title = this.title,
        timestamp = this.timestamp,
        deadline = this.deadline,
        completed = this.completed,
        id = this.id
    )
}

fun RemoteToDoTask.toLocalToDoTask(): LocalToDoTask {
    return LocalToDoTask(
        title = this.title,
        timestamp = this.timestamp,
        deadline = this.deadline,
        completed = this.completed,
        id = this.id
    )
}


fun List<ToDoTask>.toLocalToDoTaskList(): List<LocalToDoTask> {
    return this.map { it.toLocalToDoTask() }
}

fun List<ToDoTask>.toRemoteToDoTaskList(): List<RemoteToDoTask> {
    return this.map { it.toRemoteToDoTask() }
}

fun List<LocalToDoTask>.toToDoTaskListFromLocal(): List<ToDoTask> {
    return this.map { it.toToDoTask() }
}

fun List<LocalToDoTask>.toRemoteToDoTaskListFromLocal(): List<RemoteToDoTask> {
    return this.map { it.toRemoteToDoTask() }
}

fun List<RemoteToDoTask>.toToDoTaskListFromRemote(): List<ToDoTask> {
    return this.map { it.toToDoTask() }
}
fun List<RemoteToDoTask>.toLocalToDoTaskListFromRemote(): List<LocalToDoTask> {
    return this.map { it.toLocalToDoTask() }
}
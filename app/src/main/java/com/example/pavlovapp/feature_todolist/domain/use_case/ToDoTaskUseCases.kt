package com.example.pavlovapp.feature_todolist.domain.use_case
import com.example.pavlovapp.feature_todolist.domain.model.ToDoTask
import com.example.pavlovapp.feature_todolist.domain.repo.ToDoTaskListRepo
import com.example.pavlovapp.feature_todolist.domain.use_case.ToDoUseCaseResult.*
import com.example.pavlovapp.feature_todolist.domain.util.InvalidToDoTaskException
import com.example.pavlovapp.feature_todolist.domain.util.SortingDirection
import com.example.pavlovapp.feature_todolist.domain.util.ToDoTaskOrder
import javax.inject.Inject

class ToDoTaskUseCases @Inject constructor(
private val repo: ToDoTaskListRepo
) {
    suspend fun addToDoTask(todo: ToDoTask) {
        if (todo.title.isBlank()) {
            throw InvalidToDoTaskException("The title of the task can't be empty")
        }
        repo.addToDoTask(todo)
    }

    suspend fun updateToDoTask(todo: ToDoTask) {
        if (todo.title.isBlank()) {
            throw InvalidToDoTaskException("The title of the task can't be empty")
        }
        repo.updateToDoTask(todo)
    }

    suspend fun deleteToDoTask(todo: ToDoTask) {
        repo.deleteToDoTask(todo)
    }

    suspend fun toggleCompleteToDoTask(todo: ToDoTask) {
        val updatedTodo = todo.copy(completed = !todo.completed)
        repo.updateToDoTask(updatedTodo)
    }

    suspend fun getToDoTaskById(id: Int): ToDoTask? {
        return repo.getToDoTaskById(id)
    }

    suspend fun getAllToDoTasks(
        toDoTaskOrder: ToDoTaskOrder = ToDoTaskOrder.Deadline(SortingDirection.Down)
        ): ToDoUseCaseResult {
        var tasks = repo.getAllToDoTasksFromLocal()

        if (tasks.isEmpty()) {
            tasks = repo.getAllToDoTasks()
        }

        return when(toDoTaskOrder.sortingDirection) {
            is SortingDirection.Up -> {
                when (toDoTaskOrder) {
                    is ToDoTaskOrder.Deadline -> Success(
                        tasks.sortedBy{ it.deadline }
                    )
                    is ToDoTaskOrder.Time-> Success(
                        tasks.sortedBy{ it.timestamp }
                    )
                    is ToDoTaskOrder.Title -> Success(
                        tasks.sortedBy { it.title.lowercase() }
                    )

                    is ToDoTaskOrder.Completed -> Success(
                        tasks.sortedBy { it.completed }
                    )
                }
            }
            is SortingDirection.Down -> {
                when (toDoTaskOrder) {
                    is ToDoTaskOrder.Deadline -> ToDoUseCaseResult.Success(
                        tasks.sortedByDescending { it.deadline }
                    )
                    is ToDoTaskOrder.Time-> ToDoUseCaseResult.Success(
                        tasks.sortedByDescending { it.timestamp }
                    )
                    is ToDoTaskOrder.Title -> ToDoUseCaseResult.Success(
                        tasks.sortedByDescending { it.title.lowercase() }
                    )
                    is ToDoTaskOrder.Completed -> Success(
                        tasks.sortedByDescending { it.completed }
                    )
                }
            }
        }
    }

}

sealed class ToDoUseCaseResult {
    data class Success(val tasks: List<ToDoTask>) : ToDoUseCaseResult()
    data class Error(val message: String) : ToDoUseCaseResult()
}
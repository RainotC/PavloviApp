package com.example.pavlovapp.feature_todolist.presentation.todolist
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pavlovapp.feature_todolist.data.di.IoDispatcher
import com.example.pavlovapp.feature_todolist.domain.model.ToDoTask
import com.example.pavlovapp.feature_todolist.domain.use_case.ToDoTaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.pavlovapp.feature_todolist.domain.use_case.ToDoUseCaseResult

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val toDoTaskUseCases: ToDoTaskUseCases,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(ToDoListState())
    //Just read access to private
    val state = _state

    private var getToDoTasksJob: Job? = null

    private var undoToDoTask: ToDoTask? = null
    private val errorHandler = CoroutineExceptionHandler{_, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    fun onEvent(event: ToDoListEvent) {
        when (event) {
            is ToDoListEvent.DeleteTask -> {
                viewModelScope.launch (dispatcher+errorHandler) {
                    toDoTaskUseCases.deleteToDoTask(event.todo)
                    getToDoTasks()
                    undoToDoTask = event.todo
                }
            }
            ToDoListEvent.RestoreTask -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    toDoTaskUseCases.addToDoTask(undoToDoTask ?: return@launch)
                    undoToDoTask = null
                    getToDoTasks()
                }

            }
            is ToDoListEvent.SortTasks -> {
                //is the current order the same as the new order?
                if(
                    _state.value.toDoTaskOrder::class == event.toDoTaskOrder::class &&
                    _state.value.toDoTaskOrder.sortingDirection == event.toDoTaskOrder.sortingDirection
                ){
                    return
                }
                _state.value = _state.value.copy(
                    toDoTaskOrder = event.toDoTaskOrder
                )
                getToDoTasks()

            }
            is ToDoListEvent.ToggleTaskCompleted -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    toDoTaskUseCases.toggleCompleteToDoTask(event.todo)
                    getToDoTasks()
                }
            }

        }



    }
    fun getToDoTasks(){
        getToDoTasksJob?.cancel()

        getToDoTasksJob = viewModelScope.launch(dispatcher + errorHandler) {
            val result = toDoTaskUseCases.getAllToDoTasks(_state.value.toDoTaskOrder)
            when(result){
                is  ToDoUseCaseResult.Success -> {
                    _state.value = _state.value.copy(
                        toDoTasks = result.tasks,
                        toDoTaskOrder = _state.value.toDoTaskOrder,
                        isLoading = false,
                        error = null
                    )
                }
                is ToDoUseCaseResult.Error -> {
                    _state.value = _state.value.copy(
                        error = "Error: Could not retrive ToDo items",
                        isLoading = false
                    )
                }
            }
        }

    }
}
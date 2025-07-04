package com.example.pavlovapp.feature_todolist.presentation.todolist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pavlovapp.feature_todolist.presentation.todolist.components.ToDoTaskCard
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(    navController: NavController,
                       viewModel: ToDoListViewModel = hiltViewModel())
{
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current

    LaunchedEffect(key1 = true) {
        viewModel.getToDoTasks()
    }

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {

            },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "To-Do List",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {},
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ){
        padding->
        Box(contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxSize())
        {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 12.dp)
                        .padding(top = 64.dp)
                ) {
                    items(state.toDoTasks) { todo ->
                        ToDoTaskCard(
                            todo = todo,
                            modifier = Modifier.padding(vertical = 8.dp),
                            onDeleteClick = {
                                viewModel.onEvent(ToDoListEvent.DeleteTask(todo))
                                scope.launch {
                                    val undo = snackbarHostState.showSnackbar(
                                        message = "Task deleted",
                                        actionLabel = "Undo"
                                    )
                                    if (undo == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(ToDoListEvent.RestoreTask)
                                    }
                                }
                            },
                            onCompleteClick = {
                                viewModel.onEvent(ToDoListEvent.ToggleTaskCompleted(todo))
                            },
                            onCardClick = {
                                // Navigate to task details
                                // navController.navigate("task_details/${todo.id}")

                                ///TODO
                            }
                        )

                    }
                }
            }
            if(state.isLoading){
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        modifier = Modifier.semantics{
                            contentDescription = "Loading tasks"
                        }
                    )
                }
            }
            if(state.error != null){
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Error: ${state.error}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

    }
}



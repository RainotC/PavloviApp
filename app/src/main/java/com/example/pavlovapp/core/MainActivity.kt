package com.example.pavlovapp.core


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pavlovapp.feature_todolist.presentation.todolist.ToDoListScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import com.example.pavlovapp.feature_todolist.presentation.todolist.ToDoListViewModel
import com.example.pavlovapp.ui.theme.PavlovappTheme
import com.example.pavlovapp.feature_todolist.presentation.util.Screen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PavlovappTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) {
                    val navController: NavController = rememberNavController()
                    val listViewModel: ToDoListViewModel = hiltViewModel()

                    NavHost(
                        navController = navController as NavHostController,
                        startDestination = Screen.ToDoListScreen.route
                    ) {
                        composable(route = Screen.ToDoListScreen.route) {
                            ToDoListScreen(
                                navController = navController,
                                viewModel = listViewModel
                            )
                        }

                        composable(route = Screen.ToDoUpdateScreen.route){
                            //TODO
                        }
                    }
                }
            }
        }
    }
}
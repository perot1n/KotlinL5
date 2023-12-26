package com.example.lab5


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab5.ui.theme.Lab5Theme

@Suppress("UNCHECKED_CAST")

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            EmployeeDatabase::class.java,
            "employees.db"
        ).build()
    }
    private val viewModel by viewModels<EmployeeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EmployeeViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5Theme {
                val state by viewModel._state.collectAsState()
                EmployeeScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}
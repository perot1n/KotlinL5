package com.example.lab5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmployeeViewModel(
    private val dao: EmployeeDao
): ViewModel() {
    val _state = MutableStateFlow(EmployeeState())

    init {
        viewModelScope.launch {
            dao.getAllEmployees().collect { employees ->
                _state.value = _state.value.copy(employee = employees)
            }
        }
    }

    fun onEvent(event: EmployeeEvent) {
        when (event) {
            is EmployeeEvent.DeleteEmployee -> {
                viewModelScope.launch {
                    dao.deleteEmployee(event.employee)
                }
            }
            EmployeeEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingEmployee = false
                    )
                }
            }
            EmployeeEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingEmployee = true
                    )
                }
            }
            EmployeeEvent.SaveEmployee -> {
                val name = _state.value.name
                val position = _state.value.position
                val experience = _state.value.experience.toIntOrNull() ?: 0
                val efficiency = _state.value.efficiency.toDoubleOrNull() ?: 0.0
                var salary = _state.value.salary.toIntOrNull() ?: 0
                if (experience > 5) {
                    salary = (salary * 1.2 + 500).toInt()
                } else if (experience > 2) {
                    salary += 200
                }
                if (position == "Designer" && efficiency != null) {
                    salary = (salary * efficiency).toInt()
                }
                if (name.isBlank()) {
                    return
                }
                val employee = Employee (
                    name = name,
                    salary = salary,
                    position = position
                )
                viewModelScope.launch {
                    dao.insertEmployee(employee)
                }
                _state.update {
                    it.copy(
                        isAddingEmployee = false,
                        name = "",
                        salary = "",
                        position = "Developer",
                        experience = "",
                        efficiency = ""
                    )
                }
            }
            is EmployeeEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is EmployeeEvent.SetSalary -> {
                _state.update {
                    it.copy(
                        salary = event.salary
                    )
                }
            }
            is EmployeeEvent.SetPosition -> {
                _state.update {
                    it.copy(
                        position = event.position
                    )
                }
            }
            is EmployeeEvent.SetExperience -> {
                _state.update {
                    it.copy(
                        experience = event.experience
                    )
                }
            }
            is EmployeeEvent.SetEfficiency -> {
                _state.update {
                    it.copy(
                        efficiency = event.efficiency ?: ""
                    )
                }
            }
        }
    }
}

package com.example.lab5

data class EmployeeState(
    val employee: List<Employee> = emptyList(),
    val name: String = "",
    val salary: String = "",
    val position: String = "Developer",
    val experience: String = "",
    val efficiency: String = "",
    val isAddingEmployee: Boolean = false,
)

package com.example.lab5

sealed interface EmployeeEvent {
    data object SaveEmployee: EmployeeEvent
    data class SetName(val name: String): EmployeeEvent
    data class SetSalary(val salary: String): EmployeeEvent
    data class SetPosition(val position: String): EmployeeEvent
    data class SetExperience(val experience: String): EmployeeEvent
    data class SetEfficiency(val efficiency: String?): EmployeeEvent
    data object ShowDialog: EmployeeEvent
    data object HideDialog: EmployeeEvent
    data class DeleteEmployee(val employee: Employee): EmployeeEvent
}
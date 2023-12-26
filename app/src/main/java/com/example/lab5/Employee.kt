package com.example.lab5


import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String,
    val salary: Int,
    val position: String

    )



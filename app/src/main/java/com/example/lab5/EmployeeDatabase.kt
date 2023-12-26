package com.example.lab5

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Employee::class] , version = 1, exportSchema = true)
abstract class EmployeeDatabase: RoomDatabase() {
    abstract val dao: EmployeeDao
}

package com.example.lab5

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmployee (employee: Employee)
    @Delete
    suspend fun deleteEmployee (employee: Employee)
    @Query("SELECT * FROM employees")
    fun getAllEmployees(): Flow<List<Employee>>

}
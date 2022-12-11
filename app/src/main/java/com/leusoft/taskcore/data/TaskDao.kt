package com.leusoft.taskcore.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE taskState = 1")
    fun readFilteredDataStateOne(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE taskState = 2")
    fun readFilteredDataStateTwo(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE taskState = 3")
    fun readFilteredDataStateThree(): LiveData<List<Task>>

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    @Update
    suspend fun updateTask(task: Task)
}
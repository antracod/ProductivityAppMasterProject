package com.leusoft.taskcore.repository

import androidx.lifecycle.LiveData
import com.leusoft.taskcore.data.Task
import com.leusoft.taskcore.data.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.readAllData()
    val readFilteredDataStateOne: LiveData<List<Task>> = taskDao.readFilteredDataStateOne()
    val readFilteredDataStateTwo: LiveData<List<Task>> = taskDao.readFilteredDataStateTwo()
    val readFilteredDataStateThree: LiveData<List<Task>> = taskDao.readFilteredDataStateThree()


    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task)
    {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task)
    {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks()
    {
        taskDao.deleteAllTasks()
    }

}
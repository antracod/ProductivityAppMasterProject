package com.leusoft.taskcore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.leusoft.taskcore.data.GeneralDatabase
import com.leusoft.taskcore.data.Task
import com.leusoft.taskcore.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Task>>
    val readFilteredDataStateOne: LiveData<List<Task>>
    val readFilteredDataStateTwo: LiveData<List<Task>>
    val readFilteredDataStateThree: LiveData<List<Task>>

    private val repository: TaskRepository

    init {
        val taskDao = GeneralDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        readAllData = repository.readAllData
        readFilteredDataStateOne = repository.readFilteredDataStateOne
        readFilteredDataStateTwo = repository.readFilteredDataStateTwo
        readFilteredDataStateThree = repository.readFilteredDataStateThree
    }

    fun addTask(Task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(Task)
        }
    }

    fun updateTask(task: Task)
    {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task)
    {
        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteTask(task)
        }
    }

    fun deleteAllTasks()
    {
        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteAllTasks()
        }
    }


}
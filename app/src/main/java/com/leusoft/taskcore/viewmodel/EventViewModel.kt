package com.leusoft.taskcore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.leusoft.taskcore.data.Event
import com.leusoft.taskcore.data.GeneralDatabase

import com.leusoft.taskcore.data.Task
import com.leusoft.taskcore.repository.EventRepository

import com.leusoft.taskcore.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Event>>

    private val repository: EventRepository

    init {
        val eventDao = GeneralDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
        readAllData = repository.readAllData
    }

    fun addEvent(event: Event){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addEvent(event)
        }
    }

    fun updateEvent(event: Event)
    {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateEvent(event)
        }
    }

    fun deleteEvent(event: Event)
    {
        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteEvent(event)
        }
    }

    fun deleteAllEvents()
    {
        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteAllEvents()
        }
    }


}
package com.leusoft.taskcore.repository

import androidx.lifecycle.LiveData
import com.leusoft.taskcore.data.EventDao
import com.leusoft.taskcore.data.Event


class EventRepository(private val eventDao: EventDao) {

    val readAllData: LiveData<List<Event>> = eventDao.readAllData()



    suspend fun addEvent(Event: Event){
        eventDao.addEvent(Event)
    }

    suspend fun updateEvent(Event: Event)
    {
        eventDao.updateEvent(Event)
    }

    suspend fun deleteEvent(Event: Event)
    {
        eventDao.deleteEvent(Event)
    }

    suspend fun deleteAllEvents()
    {
        eventDao.deleteAllEvents()
    }

}
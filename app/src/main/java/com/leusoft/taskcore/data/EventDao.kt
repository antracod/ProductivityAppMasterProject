package com.leusoft.taskcore.data

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEvent(event: Event)

    @Query("SELECT * FROM event_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Event>>
    
    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("DELETE FROM event_table")
    suspend fun deleteAllEvents()

    @Update
    suspend fun updateEvent(event: Event)


}
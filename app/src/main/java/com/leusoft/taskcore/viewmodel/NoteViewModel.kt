package com.leusoft.taskcore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.leusoft.taskcore.data.Note
import com.leusoft.taskcore.data.GeneralDatabase
import com.leusoft.taskcore.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteViewModel(application: Application): AndroidViewModel(application) {

     val readAllData: LiveData<List<Note>>


    private val repository: NoteRepository

    init {
        val noteDao = GeneralDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllData
    }

    fun addNote(Note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(Note)
        }
    }

    fun updateNote(note: Note)
    {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note)
    {
        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteNote(note)
        }
    }

    fun deleteAllNotes()
    {
        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteAllNotes()
        }
    }


}
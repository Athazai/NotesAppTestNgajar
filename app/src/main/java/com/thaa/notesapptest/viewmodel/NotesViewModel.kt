package com.thaa.notesapptest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thaa.notesapptest.repository.NotesRepository
import com.thaa.notesapptest.data.entity.Notes
import com.thaa.notesapptest.data.room.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {

    //mengambil data dari notesDatabase
    private val notesDao = NotesDatabase.getDatabase(application).notesDao()
    private val repository: NotesRepository = NotesRepository(notesDao)

    fun getAllData(): LiveData<List<Notes>> = repository.getAllNotes

    fun insertData(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNotes(notes)
        }
    }

    fun updateData(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotes(notes)
        }
    }

    fun deleteData(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotes(notes)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchNoteByQuery(searchQuery: String): LiveData<List<Notes>> {
        return repository.searchNoteByQuery(searchQuery)
    }

    fun sortByHighPriority(): LiveData<List<Notes>> = repository.sortByHighPriority
    fun sortByLowPriority(): LiveData<List<Notes>> = repository.sortByLowPriority

}
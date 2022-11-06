package com.thaa.notesapptest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thaa.notesapptest.data.entity.Notes
import com.thaa.notesapptest.data.room.NotesDao
import com.thaa.notesapptest.data.room.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesRepository(private val notesDao: NotesDao) {

    val getAllNotes: LiveData<List<Notes>> = notesDao.getAllNotes()

    fun searchNoteByQuery(searchQuery: String): LiveData<List<Notes>> {
        return notesDao.searchNoteByQuery(searchQuery)
    }

    //menjalankan perintah dari notesDao / insertNotes
    suspend fun insertNotes(notes: Notes) {
        notesDao.insertNotes(notes)
    }

    suspend fun updateNotes(notes: Notes) {
        notesDao.updateNotes(notes)
    }

    suspend fun deleteAll() {
        notesDao.deleteAll()
    }

    suspend fun deleteNotes(notes: Notes) {
        notesDao.deleteNotes(notes)
    }

    val sortByHighPriority: LiveData<List<Notes>> = notesDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<Notes>> = notesDao.sortByLowPriority()
}
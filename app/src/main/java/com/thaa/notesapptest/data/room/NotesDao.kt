package com.thaa.notesapptest.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thaa.notesapptest.data.entity.Notes

//DAO / Data Object Acces = Berisi metode yang digunakan untuk mengakses database / CRUD.

//@Dao = Mempresentasikan bahwa kelas tersebut didalam project ini bersifat sebagai interface DAO.
@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(notes: Notes)

    //@Query = menjalankan intruksi atau perintah untuk mengeksekusi sebuah aksi
    @Query("SELECT * FROM notes_table ORDER BY id ASC")

    //fun untuk getAll data
    fun getAllNotes(): LiveData<List<Notes>>

    @Update
    suspend fun updateNotes(notes: Notes)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAll()

    //delete
    @Delete
    suspend fun deleteNotes(notes: Notes)

    //@Query = menjalankan intruksi atau perintah untuk mengeksekusi sebuah aksi
    @Query("SELECT * FROM notes_table WHERE title LIKE :querySearch")
    fun searchNoteByQuery(querySearch: String): LiveData<List<Notes>>

    //@Query = menjalankan intruksi atau perintah untuk mengeksekusi sebuah aksi
    @Query("SELECT * FROM notes_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<Notes>>

    //@Query = menjalankan intruksi atau perintah untuk mengeksekusi sebuah aksi
    @Query("SELECT * FROM notes_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<Notes>>
}
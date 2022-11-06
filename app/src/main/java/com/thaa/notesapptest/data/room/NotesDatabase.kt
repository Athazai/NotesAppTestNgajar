package com.thaa.notesapptest.data.room

import android.content.Context
import androidx.room.*
import com.thaa.notesapptest.data.entity.Notes

//entities = Mempresentasikan table yang ada pada database kita.
//@Database = Berfungsi sebagai titik akses utama untuk melakukan koneksi ke database dari aplikasi kita.
//exportschema = Adalah data dalam bentuk JSON yang berisi detail informasi database Anda, mulai dari nama table, version, column, dan tipe column.

@Database(entities = [Notes::class], version = 1, exportSchema = false)

//berfungsi mengkonversi, dan disini kita memasukan kelas converter yang sudah kita buat, yaitu Converter class.
@TypeConverters(Converter::class)


abstract class NotesDatabase : RoomDatabase() {

    //fun yang mengakses interface DAO kita
    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var instance: NotesDatabase? = null

        //fun yg berfungsi untuk membuat Database pd aplikasi dengan nama note_database
        @JvmStatic
        fun getDatabase(context: Context): NotesDatabase {
            if (instance == null) {
                synchronized(NotesDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context,
                        NotesDatabase::class.java,
                        "notes.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance as NotesDatabase
        }
    }
}
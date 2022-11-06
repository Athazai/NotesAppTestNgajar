package com.thaa.notesapptest.data.room

import androidx.room.TypeConverter
import com.thaa.notesapptest.data.entity.Priority

class Converter {

    //TypeConverter = Berfungsi untuk mengkonversi
    @TypeConverter

    //Fun ini memiliki nilai pengembalian berupa String (karena kita convert dari tipe data dalam class Priority menuju String).
    fun fromPriority(priority: Priority):String {
        return priority.name
    }

    //kebalikannya
    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}
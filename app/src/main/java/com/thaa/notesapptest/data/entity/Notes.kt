package com.thaa.notesapptest.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//sebuah anotation yang mempresentasikan sebuah nama table dari database kita.
@Entity(tableName = "notes_table")

//Anotation yang berfungsi untuk membuat value atau data didalam data class ini berfungsi sebagai object agar mudah
//di passing/ dipanggil di class lain.
@Parcelize

// data class = class yg berfungsi menyimpan data saja
data class Notes (

    //id inilah yang akan menjadi primary key(kunci utama) dalam database kita
    @PrimaryKey(autoGenerate = true)

    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String,
    var date: String,
): Parcelable
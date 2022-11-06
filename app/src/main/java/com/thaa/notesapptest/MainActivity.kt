package com.thaa.notesapptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //NavController adalah kelas yang akan dipakai untuk melakukan eksekusi navigasi yang sudah dibuat.

    //fun untuk menyambungkan fragment yang kita buat dengan main activity kita dengan memanggil findNavController kita dan memanggil id dari Xml nav host fragment kita.
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(androidx.navigation.fragment.R.id.nav_host_fragment_container)
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}
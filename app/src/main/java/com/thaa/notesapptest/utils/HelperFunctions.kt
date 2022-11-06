package com.thaa.notesapptest.utils

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.thaa.notesapptest.R
import com.thaa.notesapptest.data.entity.Priority
import com.thaa.notesapptest.data.entity.Notes
import java.text.SimpleDateFormat
import java.util.*

object HelperFunctions {

    //fun yang berfungsi untuk memverifikasi / cek data note dari user, apakah inputan(editTextnya) kosong atau tidak.
    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    //fun yang berfungsi untuk mengubah data inputan priority kita yang awalnya berupa data string yang akan diconvert kedalam class Priority kita.
    fun parseToPriority(context: Context?, priority: String): Priority {
        val expectedPriority = context?.resources?.getStringArray(R.array.priorities)
        return when (priority) {
            expectedPriority?.get(0) -> Priority.High
            expectedPriority?.get(1) -> Priority.Medium
            expectedPriority?.get(2) -> Priority.Low
            else -> Priority.Low
        }
    }

    //ketika item berada di posisi 0 maka backgroundColornya akan diubah menjadi pink , jika 1 menjadi kuning dan jika 2 menjadi hijau
    fun spinnerListener(context: Context?, priorityIndicator: CardView): AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                context?.let {
                    when (position) {
                        0 -> {
                            val red = ContextCompat.getColor(it, R.color.pink)
                            priorityIndicator.setCardBackgroundColor(red)
                        }
                        1 -> {
                            val yellow = ContextCompat.getColor(it, R.color.yello)
                            priorityIndicator.setCardBackgroundColor(yellow)
                        }
                        2 -> {
                            val green = ContextCompat.getColor(it, R.color.green)
                            priorityIndicator.setCardBackgroundColor(green)
                        }
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

    //fun yg bersifat mutable Livedata dan berfungsi untuk memeriksa apakah database kita kosong atau tidak.
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkIfDatabaseEmpty(notes: List<Notes>) {
        emptyDatabase.value = notes.isEmpty()
    }

    //menampilkan tanggal & hari
    fun dateTodaySimpleFormat(): String {
        val date = Date()
        val simpleFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return simpleFormat.format(date.time)
    }

}
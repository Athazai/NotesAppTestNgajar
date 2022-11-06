package com.thaa.notesapptest.utils

import android.os.Build
import android.view.View
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thaa.notesapptest.R
import com.thaa.notesapptest.data.entity.Priority
import com.thaa.notesapptest.data.entity.Notes
import com.thaa.notesapptest.fragment.HomeFragmentDirections


object BindingAdapters {

    //membuat fun, kemudian menjalankan apa yg ada di fun tersebut
    @BindingAdapter("android:navigateToAddFragment")
    @JvmStatic
    fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
        view.setOnClickListener {
            if (navigate) {
                view.findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            }
        }
    }

    //membuat fun, kemudian menjalankan apa yg ada di fun tersebut
    @BindingAdapter("android:emptyDatabase")
    @JvmStatic
    //berfungsi untuk memeriksa apakah ada data dalam database atau kosong, nah didalam terdapat percabangan (when) yang mana jika database nya kosong
    //maka tampilan view box kosong akan visible (ditampilkan) , tapi jika false (tidak kosong)
    //maka akan disembunyikan (invisible).
    fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
        when (emptyDatabase.value) {
            true -> view.visibility = View.VISIBLE
            else -> view.visibility = View.INVISIBLE
        }
    }

    @BindingAdapter("android:parsePriorityToInt")
    @JvmStatic
    fun parsePriorityToInt(view: Spinner, priority: Priority) {
        when (priority) {
            Priority.High -> {
                view.setSelection(0)
            }
            Priority.Medium -> {
                view.setSelection(1)
            }
            Priority.Low -> {
                view.setSelection(2)
            }
        }
    }

    //minimum versi yang bisa digunakan (M = Marshmellow)
    @RequiresApi(Build.VERSION_CODES.M)

    //membuat fun, kemudian menjalankan apa yg ada di fun tersebut
    @BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun parsePriorityColor(cardView: MaterialCardView, priority: Priority) {
        when (priority) {
            Priority.High -> {
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink))
            }
            Priority.Medium -> {
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yello))
            }
            Priority.Low -> {
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
            }
        }
    }

    @BindingAdapter("android:sendDataToDetail")
    @JvmStatic
    fun sendDataToDetail(view: ConstraintLayout, currentItem: Notes) {
        view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }
}
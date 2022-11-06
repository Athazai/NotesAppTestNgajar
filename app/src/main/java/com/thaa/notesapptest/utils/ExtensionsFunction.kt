package com.thaa.notesapptest.utils

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.thaa.notesapptest.MainActivity
import com.thaa.notesapptest.R
import com.thaa.notesapptest.fragment.AddFragment

object ExtensionsFunction {
    fun <T> LiveData <T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

//    fun Toolbar.setupActionBar(fragment: Fragment, destinationId: Int?) {
//
//        val navController = findNavController()
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        this.apply {
//            setupWithNavController(navController, appBarConfiguration)
//            (fragment.requireActivity() as MainActivity).setSupportActionBar(this)
//            (fragment.requireActivity() as MainActivity).setupActionBarWithNavController(
//                navController,
//                appBarConfiguration
//            )
//            navController.addOnDestinationChangedListener { _, destination, _ ->
//                when (destination.id) {
//                    destinationId -> {
//                        this.setNavigationIcon(R.drawable.ic_left_arrow)
//                    }
//                }
//            }
//        }
//    }
}
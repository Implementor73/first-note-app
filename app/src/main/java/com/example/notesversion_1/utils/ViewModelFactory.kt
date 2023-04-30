package com.example.notesversion_1.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

typealias ViewModelCreator<VM> = () -> VM

class ViewModelFactory<T>(
   private val viewModelCreator : ViewModelCreator<T>
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelCreator.invoke() as T
    }
}

inline fun<reified VM : ViewModel> Fragment.viewModelCreate(noinline vmCreator: ViewModelCreator<VM>) : Lazy<VM> {
   return viewModels { ViewModelFactory(vmCreator) }
}



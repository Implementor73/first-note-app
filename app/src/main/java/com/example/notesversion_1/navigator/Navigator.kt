package com.example.notesversion_1.navigator

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections

interface Navigator
{
    fun navigateTo(directions: NavDirections)
    fun goBack()
}

fun Fragment.navigator() = requireActivity() as Navigator

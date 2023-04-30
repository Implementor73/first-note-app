package com.example.notesversion_1.multichoice

interface MultiChoiceState<T> {

    //check selected item
    fun isChecked(item : T) : Boolean

    // show / hide checkBoxes
    val visibleMultiChoice : Boolean

    val totalCheckedCount : Int
}
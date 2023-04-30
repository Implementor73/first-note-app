package com.example.notesversion_1.utils

class SingleEvent<T>(private val data : T)
{
    private var isFirst : Boolean = true
    fun getValue() : T?{
        return if(isFirst){
            isFirst = false
            data
        }else{
            null
        }
    }
}

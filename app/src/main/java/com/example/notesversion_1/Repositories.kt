package com.example.notesversion_1

import android.content.Context
import androidx.room.Room
import com.example.notesversion_1.model.notes.room.RoomNotesRepository
import com.example.notesversion_1.model.room.AppDataBase
import kotlinx.coroutines.Dispatchers

object Repositories
{
    private lateinit var applicationContext : Context
    private val dispatcherIO = Dispatchers.IO

    private val dataBase : AppDataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "notes_database.db"
        ).build()
    }

    val notesRepository : RoomNotesRepository by lazy {
        RoomNotesRepository(dispatcherIO, dataBase.getNotesDao())
    }

    fun init(context: Context){
        applicationContext = context
    }

}
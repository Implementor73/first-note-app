package com.example.notesversion_1.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notesversion_1.model.notes.room.NotesDao
import com.example.notesversion_1.model.notes.room.RoomNotesConverter
import com.example.notesversion_1.model.notes.room.entity.NotesDb

@Database(
    version = 1,
    entities = [
        NotesDb::class
    ]
)
@TypeConverters(RoomNotesConverter::class)
abstract class AppDataBase : RoomDatabase()
{
    abstract fun getNotesDao() : NotesDao
}
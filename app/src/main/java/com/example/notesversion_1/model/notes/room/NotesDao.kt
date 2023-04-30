package com.example.notesversion_1.model.notes.room

import androidx.room.*
import com.example.notesversion_1.model.notes.entity.CategoryNotes
import com.example.notesversion_1.model.notes.room.entity.NotesDb
import kotlinx.coroutines.flow.Flow
import java.util.Objects


@Dao
interface NotesDao {

    @Insert
    suspend fun createNotes(notesDb: NotesDb) : Long

    @Query("SELECT * FROM notes where category_notes = :categoryNotes")
    fun getNotesDbList(categoryNotes: Int) : Flow<List<NotesDb>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNotesById(id : Long) :NotesDb

    @Update
    suspend fun updateNotes(notesDb: NotesDb)

    @Delete
    suspend fun deleteNotes(vararg notesDb: NotesDb)
}

/*
    добавить еще update для категории
 */
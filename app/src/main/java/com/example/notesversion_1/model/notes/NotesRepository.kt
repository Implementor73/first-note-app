package com.example.notesversion_1.model.notes

import com.example.notesversion_1.model.notes.entity.CategoryNotes
import com.example.notesversion_1.model.notes.entity.Notes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun createNotes(notes: Notes): Boolean
    suspend fun getNotesList(categoryNotes: CategoryNotes) : Flow<List<Notes>> // может сделать данные нулбл ?
    suspend fun getNotesById(id : Long) : Notes
    suspend fun updateNotes(notes: Notes)
    suspend fun deleteNotes(notes: Notes)
}
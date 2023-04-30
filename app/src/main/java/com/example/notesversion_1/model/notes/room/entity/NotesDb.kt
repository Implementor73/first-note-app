package com.example.notesversion_1.model.notes.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesversion_1.model.notes.entity.CategoryNotes
import com.example.notesversion_1.model.notes.entity.ContentTypeNotes
import com.example.notesversion_1.model.notes.entity.Notes


@Entity(
    tableName = "notes"
)
data class NotesDb(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val tittle : String?,
    val content : String?,
    @ColumnInfo (name = "type_notes") val typeNotes: ContentTypeNotes,
    @ColumnInfo (name = "category_notes") val categoryNotes : CategoryNotes
)
{
    fun toNotes() = Notes(
        id = id,
        tittle = tittle ?: "",
        content = content ?: "",
        typeNotes = typeNotes,
        categoryNotes = categoryNotes
    )
    companion object{
        fun toNotesDb(notes: Notes) = NotesDb(
            id = notes.id,
            tittle = notes.tittle,
            content = notes.content,
            typeNotes = notes.typeNotes,
            categoryNotes = notes.categoryNotes
        )
    }
}

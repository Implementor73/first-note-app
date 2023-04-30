package com.example.notesversion_1.model.notes.room

import androidx.room.TypeConverter
import com.example.notesversion_1.model.notes.entity.CategoryNotes
import com.example.notesversion_1.model.notes.entity.ContentTypeNotes

class RoomNotesConverter
{
    @TypeConverter
    fun typeContentToStringName(content : ContentTypeNotes) : String = content.name
    @TypeConverter
    fun fromStringName(typeName : String) = ContentTypeNotes.valueOf(typeName)


    @TypeConverter
    fun categoryToConst(categoryNotes: CategoryNotes) = categoryNotes.ordinal
    @TypeConverter
    fun fromConstCategory(ordinal : Int) = CategoryNotes.values().first { it.ordinal == ordinal }

}
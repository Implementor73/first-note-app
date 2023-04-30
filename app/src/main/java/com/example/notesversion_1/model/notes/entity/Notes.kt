package com.example.notesversion_1.model.notes.entity

data class Notes(
    val id : Long,
    val tittle : String,
    val content : String,
    val typeNotes: ContentTypeNotes,
    val categoryNotes: CategoryNotes
){
    fun isCreated() : Boolean{
        return (content + tittle).isNotBlank() //content.isNotBlank() || name.isNotBlank()
    }

    companion object{
        fun getEMPTY() : Notes = Notes(
            id = 0,
            tittle = "",
            content = "",
            typeNotes = ContentTypeNotes.NOTHING,
            categoryNotes = CategoryNotes.MAIN
        )
    }
}

enum class ContentTypeNotes{
    TEXT, LIST, NOTHING
}
enum class CategoryNotes{
    MAIN, BASKET, ARCHIVE
}

/*
Заметка будет содержать:
    1) заголовок (название)
    2) Содержимое - текст или пункты списка
    Буду хранить в строке, и проверять какой тип контента пришел.
    Если список то буду преобразовывать его в список с помощью функции строк по элемнту.
    В данном случае по символу перехода на новую строку.

    каким образом мне узнавать какой тип контента у заметки пришел ?
    и как мне их преобразовывать что бы по определенному значению я возвращал определенный тип?

   1) что хранить в классе Notes что бы общаться с базой
   2) где заниматься преобразованием
   3) как его сделать, что возвращать и что получать

 */
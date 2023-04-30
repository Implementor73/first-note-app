package com.example.notesversion_1.screens.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesversion_1.model.notes.NotesRepository
import com.example.notesversion_1.model.notes.entity.ContentTypeNotes
import com.example.notesversion_1.model.notes.entity.Notes
import kotlinx.coroutines.launch

class NoteViewModel(
    private val notesRepository: NotesRepository,
    private var id : Long,
    contentType : ContentTypeNotes
) : ViewModel() {

    private val _currentNote = MutableLiveData<Notes>()
    val currentNote : LiveData<Notes> = _currentNote

    init {
        viewModelScope.launch {
            _currentNote.value = notesRepository.getNotesById(id)
        }
        setContentType(contentType)
    }

    fun changeNotes(tittle : String, content : String){

       /* val note = _currentNote.value as Notes
        if(tittle == note.tittle && content == note.content) return
        */

        _currentNote.value = _currentNote.value?.copy(
            tittle = tittle,
            content = content
        )

        _currentNote.value?.let(::addOrUpdate)
    }
    fun addOrUpdate(notes: Notes) = viewModelScope.launch {
           _currentNote.value?.let {
               if(it.id <= 0){
                   notesRepository.createNotes(notes)
               }else{
                   Log.d("notesChange",notes.id.toString())
                   notesRepository.updateNotes(notes)
               }
           }
        }
    fun deleteNotes() = viewModelScope.launch{
        _currentNote.value?.let {
            notesRepository.deleteNotes(it)
        }
    }
/*
    fun createNotes(){
        viewModelScope.launch {
            _currentNote.value?.let { notesRepository.createNotes(it) }
        }
    }

 */
    //TODO("думаю потом добавить в отедельный интерфейс и сделать изменение макета из кода по кнопке")
    private fun setContentType(contentType: ContentTypeNotes){
        if(id >= 0) return
        _currentNote.value.let {
            it?.copy(
                typeNotes = contentType
            )
        }
    }
}
/*
    мне нужно сохранить созданную заметку в базу.
    при условии если у меня текущие поля не пусты, я ее создаю
 */
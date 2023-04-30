package com.example.notesversion_1.screens.tabs.notelist


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesversion_1.model.notes.NotesRepository
import com.example.notesversion_1.model.notes.entity.CategoryNotes
import com.example.notesversion_1.model.notes.entity.ContentTypeNotes
import com.example.notesversion_1.model.notes.entity.Notes
import com.example.notesversion_1.utils.SingleEvent
import kotlinx.coroutines.launch

class ListNotesViewModel(
    private val notesRepository: NotesRepository,
    private val categoryNotes: CategoryNotes
) : ViewModel(),ActionNotesList
{

    private val _notesList = MutableLiveData<List<Notes>>()
    private val _isEmptyList = MutableLiveData<Boolean>()

    private val _event = MutableLiveData<SingleEvent<Notes>>()

    val event : LiveData<SingleEvent<Notes>> = _event
    val notesList : LiveData<List<Notes>> = _notesList
    val isEmptyList : LiveData<Boolean> = _isEmptyList

    init {
        viewModelScope.launch {
            notesRepository.getNotesList(categoryNotes).collect{ notes->
                _notesList.postValue(notes)
                Log.d("notesChange","checkList")
            }
            _isEmptyList.value = _notesList.value?.isEmpty()
        }
        Log.d("countList",_notesList.value?.size.toString())
    }

    override fun onOpenDetail(notes: Notes) {
        _event.value = SingleEvent(notes)
    }

}


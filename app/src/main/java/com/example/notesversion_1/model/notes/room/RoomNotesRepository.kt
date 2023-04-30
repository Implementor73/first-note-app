package com.example.notesversion_1.model.notes.room

import android.util.Log
import com.example.notesversion_1.model.notes.NotesRepository
import com.example.notesversion_1.model.notes.entity.CategoryNotes
import com.example.notesversion_1.model.notes.entity.Notes
import com.example.notesversion_1.model.notes.room.entity.NotesDb
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomNotesRepository(
    val dispatcherIO: CoroutineDispatcher,
    val notesDao: NotesDao
) : NotesRepository
{
    override suspend fun createNotes(notes: Notes) : Boolean {
        return withContext(dispatcherIO) {
            if (notes.isCreated()) {
                val notesDb = NotesDb.toNotesDb(notes)
                val result = notesDao.createNotes(notesDb)
                Log.d("listResult", result.toString())
                return@withContext result >= 0
            } else {
                false
            }
        }
    } //или добавить exception что не создано ниче

    override suspend fun getNotesList(categoryNotes: CategoryNotes): Flow<List<Notes>> {
         return notesDao.getNotesDbList(categoryNotes.ordinal).map {
             it.map {notesDb->
                 notesDb.toNotes()
             }
         }.flowOn(dispatcherIO)
    }


    override suspend fun getNotesById(id: Long): Notes = withContext(dispatcherIO){
        return@withContext if( id >= 0){
            notesDao.getNotesById(id).toNotes()
        }else{
            Notes.getEMPTY()
        }
    }

    override suspend fun updateNotes(notes: Notes) = withContext(dispatcherIO){
        notesDao.updateNotes(NotesDb.toNotesDb(notes))
    }

    override suspend fun deleteNotes(notes: Notes) = withContext(dispatcherIO){
        notesDao.deleteNotes(
            notesDb = arrayOf(NotesDb.toNotesDb(notes)) // работает и хорошо )
        )
    }
}

/*
    1) Создание заметки:
    Общая идея{
        *заметка создана будет когда хоть какое-то поле будет не пустым.
        (мы можем первые N символов контента записывать в заголовок, тогда и проблем с пустыми полями не будет)
        *сделать возможность хранения хранения пустых строк то бишь NULL,
         только не допускать что бы обе были NULL уже в коде
         *добавить реакцию на добавление заметки в UI. (Можно вызывать SnackBar с этим сообщением)
         *сделать поля с датой NULL свойство для указания напоминаний.
    }
    Реализация (примерные наброски для кода){
        *добавить функцию в класс Notes, которая будет проверять на заполненые поля
        и добавлять в базу в случае если одно поле точно заполнено и не иначе
        на основе функции с добавлением, возвращать true/false (добавилось или нет)
    }

    2) Категории заметок (в основе , в архиве, в корзине)

 */
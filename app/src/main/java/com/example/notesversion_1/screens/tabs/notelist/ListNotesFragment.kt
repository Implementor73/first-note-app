package com.example.notesversion_1.screens.tabs.notelist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesversion_1.R
import com.example.notesversion_1.Repositories
import com.example.notesversion_1.databinding.FragmentListNotesBinding
import com.example.notesversion_1.model.notes.entity.CategoryNotes
import com.example.notesversion_1.model.notes.entity.ContentTypeNotes
import com.example.notesversion_1.model.notes.entity.Notes
import com.example.notesversion_1.navigator.navigator
import com.example.notesversion_1.utils.viewModelCreate

class ListNotesFragment : Fragment() {

    companion object {
        fun newInstance() = ListNotesFragment()
    }

    private val listViewModel: ListNotesViewModel by viewModelCreate {
        ListNotesViewModel(Repositories.notesRepository, CategoryNotes.MAIN)
    }

    private lateinit var binding : FragmentListNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListNotesBinding.inflate(inflater,container,false)
        val notesAdapter = NoteListAdapter(listViewModel)

        listViewModel.notesList.observe(viewLifecycleOwner){
            notesAdapter.submitList(it)
        }
        with(binding.recyclerNotesView){
            layoutManager = GridLayoutManager(requireContext(),3)
            adapter = notesAdapter
        }

        listViewModel.event.observe(viewLifecycleOwner){
            it.getValue()?.let {notes->
                val directions = ListNotesFragmentDirections.actionListNotesFragmentToNoteFragment(notes.typeNotes,notes.id)
                navigator().navigateTo(directions)
            }
        }

        binding.buttonCreateNotes.setOnClickListener {
            val directions = ListNotesFragmentDirections.actionListNotesFragmentToNoteFragment(ContentTypeNotes.TEXT)
            findNavController().navigate(directions)
        }
        return binding.root
    }

}

/*
    1. кнопка создания будет принимать аргумент ID - сделать NULLABLE или просто пробросить -1

    2. сделать dialogFragment, который будет возрващать тип для заметок (список или текст)
     и сразу переходить на другой фрагмент с этими данными.

     3. От куда брать(генерировать) категорию заметок?
     Ответ: проверять по currentDestination и его ID назначать категорию
     Один и тот же фрагмент для всех видов списка? - думаю да
     Где хранить?


     Дальше она будет передаваться в viewModel и в Adapter
     Отображаем один и тот же адаптер (без выебонов)

     Изменяем actionMode по категории или currentDestionation - как ?

     ****** Посмотреть как сделать множественный выбор и уже от этого отталкиваться ******



 */
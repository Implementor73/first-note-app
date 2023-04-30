package com.example.notesversion_1.screens.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.notesversion_1.R
import com.example.notesversion_1.Repositories
import com.example.notesversion_1.actions.OnActionNoteListener
import com.example.notesversion_1.databinding.FragmentNoteTypeListBinding
import com.example.notesversion_1.databinding.FragmentNoteTypeTextBinding
import com.example.notesversion_1.model.notes.entity.Notes
import com.example.notesversion_1.navigator.navigator
import com.example.notesversion_1.toolbar.ICustomToolbar
import com.example.notesversion_1.utils.viewModelCreate

class NoteFragment : Fragment(), OnActionNoteListener, ICustomToolbar
{
    private val notesArgs : NoteFragmentArgs by navArgs()

    private val viewModel: NoteViewModel by viewModelCreate {
        NoteViewModel(Repositories.notesRepository,notesArgs.id,notesArgs.contentType)
    }
    private var textNoteBinding : FragmentNoteTypeTextBinding? = null
    private var listNoteBinding : FragmentNoteTypeListBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        textNoteBinding = FragmentNoteTypeTextBinding.inflate(inflater,container,false)

        viewModel.currentNote.observe(viewLifecycleOwner){
            updateUI(it)
        }

        viewModel.currentNote.observe(viewLifecycleOwner){}
        return textNoteBinding?.root
    }

    private fun updateUI(notes: Notes){
        textNoteBinding?.let {bind->
            bind.notesNameTextView.setText(notes.tittle)
            bind.notesContentTextView.setText(notes.content)
        }
    }

    companion object {
        fun newInstance() = NoteFragment()
    }

    override fun updateNotes() {
        textNoteBinding?.let{
            viewModel.changeNotes(
                tittle = it.notesNameTextView.text.toString(),
                content = it.notesContentTextView.text.toString()
            )
        }
    }

    override fun deleteNotes() {
        viewModel.deleteNotes()
        navigator().goBack()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(menuRes,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        getCustomActionToolbar()[menuItem.itemId]?.invoke()
        return true
    }

    override val menuRes: Int
        get() = R.menu.menu_item_notes

    override fun getCustomActionToolbar() = mapOf(
        android.R.id.home to navigator()::goBack,
        R.id.deleteMenuItem to ::deleteNotes
    )
}

/*
    класть данные в аргументы по изменению объекта

    Вопрос когда вызывать метод добавление в базу ? делегат ??? (когда вызываем метод в активити он вызывается на фрагменте)

 */
/*
    1 .Если есть аргумент ID, то достаем из базы по ID, иначе создаем новую заметку.
    (просто кидаем в зависимость viewModel ID текущий или -1)

    2. Будет приходить аргумент типа заметки и на основе этого подбираем layout + viewBinding

//////////
   Вопрос как будем все содержать в одном фрагменте.
   Для одного мне нужен List элементов. Соответсвенно adapter и функции связные с ним
   Для другого текстовое поле
   Вариант_1
   для viewModel(конструктор как зависимость) тип заметки передавать не нужно (мне тип нужен в аргументах только для организации макета)
   этот момент будет только при создании и просто сделать функцию, которая будет назначать тип для пустой заметки, что бы не парится
   Вариант_2
   я создаю класс, аргументов для того что бы передавать его в viewModel.
   Смысл будет в том, что тип заметки будет nullable.
   То бишь при создании он будет иметь данные, а при получении нет
/////////




 */
package com.example.notesversion_1.screens.tabs.notelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesversion_1.databinding.ItemNotesBinding
import com.example.notesversion_1.model.notes.entity.Notes

class NoteListAdapter(
    private val actionListener : ActionNotesList
) : ListAdapter<Notes,NoteListAdapter.NotesHolder>(NotesDiffUtilCallback()),
        View.OnClickListener
{

   inner class NotesHolder(val bindHolder : ItemNotesBinding) : RecyclerView.ViewHolder(bindHolder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotesBinding.inflate(inflater,parent,false)
        binding.root.setOnClickListener(this)
        return NotesHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val notes = currentList[position]
        with(holder.bindHolder){
            this.root.tag = notes
            this.itemNotesListContent.text = notes.content
            this.itemNotesListName.text = notes.tittle
        }
    }

    override fun onClick(v: View?) {
        val notes = v?.tag as Notes

        actionListener.onOpenDetail(notes)
    }
}

interface ActionNotesList{
    fun onOpenDetail(notes: Notes)
}

private class NotesDiffUtilCallback : DiffUtil.ItemCallback<Notes>()
{
    override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem == newItem
    }

}
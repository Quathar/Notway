package com.iothar.android.recycler.note

import androidx.recyclerview.widget.RecyclerView
import com.iothar.android.databinding.ItemNoteBinding
import com.iothar.data.entity.Note

class NoteViewHolder(
    private val binding:           ItemNoteBinding,
    private val noteClickListener: NoteAdapter.NoteClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note) = with(binding) {
        noteName.text = note.title
        buttonEditNote.setOnClickListener   { noteClickListener.onNoteEdit(note.nid) }
        buttonDeleteNote.setOnClickListener { noteClickListener.onNoteDelete(note) }
    }

}
package com.iothar.android.recycler.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iothar.android.databinding.ItemNoteBinding
import com.iothar.data.entity.Note

class NoteAdapter(
    private val noteClickListener: NoteClickListener
) : ListAdapter<Note, NoteViewHolder>(NoteDiffUtil) {

    // <<-INTERFACE->>
    interface NoteClickListener {
        fun onNoteEdit(nid: Int)
        fun onNoteDelete(note: Note)
    }

    // <<-METHODS->>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, noteClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.bind(getItem(position))

}
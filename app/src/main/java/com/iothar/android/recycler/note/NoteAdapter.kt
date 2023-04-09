package com.iothar.android.recycler.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iothar.android.R
import com.iothar.db.entity.Note

class NoteAdapter(
    private val notes: List<Note>,
    private val noteClickListener: NoteClickListener
) : RecyclerView.Adapter<NoteViewHolder>() {

    // <<-INTERFACE->>
    interface NoteClickListener {
        fun onNoteEdit(position: Int)
        fun onNoteDelete(position: Int)
    }

    // <<-METHODS->>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view, noteClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) = holder.bind(notes[position])

    override fun getItemCount(): Int = notes.size

}
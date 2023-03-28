package com.iothar.android.recycler.note

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iothar.android.R
import com.iothar.db.entity.Note

class NoteViewHolder(
    view: View,
    private val noteClickListener: NoteAdapter.NoteClickListener
) : RecyclerView.ViewHolder(view) {

    private val _name         = view.findViewById<TextView>(R.id.note_name)
    private val _buttonEdit   = view.findViewById<ImageButton>(R.id.button_edit_note)
    private val _buttonDelete = view.findViewById<ImageButton>(R.id.button_delete_note)

    fun bind(note: Note) {
        _name.text = note.title
        _buttonEdit.setOnClickListener   { noteClickListener.onNoteEdit(adapterPosition) }
        _buttonDelete.setOnClickListener { noteClickListener.onNoteDelete(adapterPosition) }
    }

}
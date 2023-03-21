package com.iothar.android.recycler

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

    private val _nameTextView          = view.findViewById<TextView>(R.id.name)
    private val _firstSurnameTextView  = view.findViewById<TextView>(R.id.first_surname)
    private val _secondSurnameTextView = view.findViewById<TextView>(R.id.second_surname)
    private val _buttonDelete          = view.findViewById<ImageButton>(R.id.button_delete)
    private val _buttonEdit            = view.findViewById<ImageButton>(R.id.button_details)

    fun bind(note: Note) {
        _buttonDelete.setOnClickListener { noteClickListener.onNoteDelete(adapterPosition) }
        _buttonEdit.setOnClickListener { noteClickListener.onNoteEdit(adapterPosition) }
        _nameTextView.setText(note.noteId)
        _firstSurnameTextView.text = note.title
        _secondSurnameTextView.text = note.body
    }

}
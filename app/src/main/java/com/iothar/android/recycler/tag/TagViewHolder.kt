package com.iothar.android.recycler.tag

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iothar.android.R
import com.iothar.db.model.TagWithNotes

class TagViewHolder(
    view: View,
    private val tagClickListener: TagAdapter.TagClickListener
) : RecyclerView.ViewHolder(view) {

    private val _name         = view.findViewById<EditText>(R.id.tag_name)
    private val _ref          = view.findViewById<TextView>(R.id.tag_ref)
    private val _buttonSave   = view.findViewById<ImageButton>(R.id.button_save_tag)
    private val _buttonDelete = view.findViewById<ImageButton>(R.id.button_delete_tag)

    fun bind(tagWithNotes: TagWithNotes) {
        _name.setText(tagWithNotes.tag.tag)
        _ref.text = tagWithNotes.notes.size.toString()
        _buttonSave.setOnClickListener   { tagClickListener.onTagSave(adapterPosition) }
        _buttonDelete.setOnClickListener { tagClickListener.onTagDelete(adapterPosition) }
    }

}
package com.iothar.android.recycler.note

import androidx.recyclerview.widget.DiffUtil
import com.iothar.data.entity.Note

object NoteDiffUtil : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note) =
        oldItem.nid == newItem.nid

    override fun areContentsTheSame(oldItem: Note, newItem: Note) =
        oldItem == newItem

}
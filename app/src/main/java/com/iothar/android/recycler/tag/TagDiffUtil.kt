package com.iothar.android.recycler.tag

import androidx.recyclerview.widget.DiffUtil
import com.iothar.data.model.TagWithNotes

object TagDiffUtil : DiffUtil.ItemCallback<TagWithNotes>() {

    override fun areItemsTheSame(oldItem: TagWithNotes, newItem: TagWithNotes) =
        oldItem.tag.tid == newItem.tag.tid

    override fun areContentsTheSame(oldItem: TagWithNotes, newItem: TagWithNotes) =
        oldItem == newItem

}
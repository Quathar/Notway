package com.iothar.android.recycler.tag

import androidx.recyclerview.widget.RecyclerView
import com.iothar.android.databinding.ItemTagBinding
import com.iothar.data.model.TagWithNotes

class TagViewHolder(
    private val binding:          ItemTagBinding,
    private val tagClickListener: TagAdapter.TagClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tagWithNotes: TagWithNotes) = with(binding) {
        tagName.setText(tagWithNotes.tag.tag)
        tagRef.text = tagWithNotes.notes.size.toString()
        buttonSaveTag.setOnClickListener   { tagClickListener.onTagSave(tagWithNotes.tag, tagName.text.toString()) }
        buttonDeleteTag.setOnClickListener { tagClickListener.onTagDelete(tagWithNotes.tag) }
    }

}
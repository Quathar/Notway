package com.iothar.android.recycler.tag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iothar.android.databinding.ItemTagBinding
import com.iothar.data.entity.Tag
import com.iothar.data.model.TagWithNotes

class TagAdapter(
    private val tagClickListener: TagClickListener
) : ListAdapter<TagWithNotes, TagViewHolder>(TagDiffUtil) {

    // <<-INTERFACE->>
    interface TagClickListener {
        fun onTagSave(tag: Tag, name: String)
        fun onTagDelete(tag: Tag)
    }

    // <<-METHODS->>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding, tagClickListener)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun addBlankItem() {
        val list = currentList.toMutableList()
        list.add(TagWithNotes.empty())
        submitList(list)
    }

}
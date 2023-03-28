package com.iothar.android.recycler.tag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iothar.android.R
import com.iothar.db.model.TagWithNotes

class TagAdapter(
    private val tags: List<TagWithNotes>,
    private val tagClickListener: TagClickListener
) : RecyclerView.Adapter<TagViewHolder>() {

    interface TagClickListener {
        fun onTagSave(position: Int)
        fun onTagDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return TagViewHolder(view, tagClickListener)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) = holder.bind(tags[position])

    override fun getItemCount(): Int = tags.size

}
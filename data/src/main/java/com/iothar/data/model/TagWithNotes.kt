package com.iothar.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.iothar.data.entity.Note
import com.iothar.data.entity.NoteTagCrossRef
import com.iothar.data.entity.Tag

data class TagWithNotes(
    @Embedded
    val tag:   Tag,
    @Relation(
        parentColumn = "tid",
        entityColumn = "nid",
        associateBy  = Junction(NoteTagCrossRef::class)
    )
    val notes: List<Note>,
) {
    companion object {
        fun empty() = TagWithNotes(Tag.empty(), emptyList())
    }
}
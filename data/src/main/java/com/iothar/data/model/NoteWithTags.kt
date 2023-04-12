package com.iothar.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.iothar.data.entity.Note
import com.iothar.data.entity.NoteTagCrossRef
import com.iothar.data.entity.Tag

data class NoteWithTags(
    @Embedded
    val note: Note,
    @Relation(
        parentColumn = "nid",
        entityColumn = "tid",
        associateBy  = Junction(NoteTagCrossRef::class)
    )
    var tags: List<Tag>,
) {
    companion object {
        fun empty() = NoteWithTags(Note.empty(), emptyList())
    }
}
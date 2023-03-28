package com.iothar.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.iothar.db.entity.Note
import com.iothar.db.entity.NoteTagCrossRef
import com.iothar.db.entity.Tag

data class NoteWithTags(
    @Embedded
    val note: Note,
    @Relation(
        parentColumn = "nid",
        entityColumn = "tid",
        associateBy  = Junction(NoteTagCrossRef::class)
    )
    var tags: List<Tag>
) {
    companion object {
        fun empty() = NoteWithTags(Note(-1, "", ""), emptyList())

    }
}
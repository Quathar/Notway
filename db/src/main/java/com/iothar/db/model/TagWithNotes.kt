package com.iothar.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.iothar.db.entity.Note
import com.iothar.db.entity.NoteTagCrossRef
import com.iothar.db.entity.Tag

data class TagWithNotes(
    @Embedded
    val tag: Tag,
    @Relation(
        parentColumn = "tid",
        entityColumn = "nid",
        associateBy  = Junction(NoteTagCrossRef::class)
    )
    val notes: List<Note>
) {
    companion object {
        fun empty() = TagWithNotes(Tag(-1, ""), emptyList())

    }
}
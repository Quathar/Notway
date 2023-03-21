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
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy  = Junction(NoteTagCrossRef::class)
    )
    val tags: List<Tag>
)
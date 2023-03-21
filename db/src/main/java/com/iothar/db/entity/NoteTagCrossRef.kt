package com.iothar.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["noteId", "tagId"])
data class NoteTagCrossRef(
    val noteId: Int,
    @ColumnInfo(index = true)
    val  tagId: Int
)
package com.iothar.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["noteId", "tagId"])
data class NoteTagCrossRef(
    var noteId: Int,
    @ColumnInfo(index = true)
    var tagId: Int
)
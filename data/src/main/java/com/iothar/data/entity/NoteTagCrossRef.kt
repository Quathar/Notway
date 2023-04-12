package com.iothar.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName   = "note_tag_ref",
    primaryKeys = ["nid", "tid"],
)
data class NoteTagCrossRef(
    var nid: Int,
    @ColumnInfo(index = true)
    var tid: Int,
) {
    companion object {
        fun empty() = NoteTagCrossRef(0, 0)
    }
}
package com.iothar.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class Tag(
    @PrimaryKey(autoGenerate = true)
    var tid: Int,
    var tag: String,
) {
    companion object {
        fun empty() = Tag(0, "")
    }
}
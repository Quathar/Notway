package com.iothar.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var nid:   Int,
    var title: String,
    var body:  String,
) {
    companion object {
        fun empty() = Note(0, "", "")
    }
}
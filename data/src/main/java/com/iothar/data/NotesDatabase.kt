package com.iothar.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iothar.data.dao.NotesDao
import com.iothar.data.dao.NotesWithTagsDao
import com.iothar.data.dao.TagsDao
import com.iothar.data.entity.Note
import com.iothar.data.entity.NoteTagCrossRef
import com.iothar.data.entity.Tag

@Database(
    entities     = [Note::class, Tag::class, NoteTagCrossRef::class],
    version      = 1,
    exportSchema = false,
)
abstract class NotesDatabase : RoomDatabase() {

    // <<-METHODS->>
    abstract fun notesDao(): NotesDao
    abstract fun tagsDao(): TagsDao
    abstract fun notesWithTagsDao(): NotesWithTagsDao

}
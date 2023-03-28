package com.iothar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iothar.db.dao.NotesDao
import com.iothar.db.dao.NotesWithTagsDao
import com.iothar.db.dao.TagsDao
import com.iothar.db.entity.Note
import com.iothar.db.entity.NoteTagCrossRef
import com.iothar.db.entity.Tag

@Database(
    entities     = [Note::class, Tag::class, NoteTagCrossRef::class],
    version      = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao
    abstract fun tagsDao(): TagsDao
    abstract fun notesWithTagsDao(): NotesWithTagsDao

    companion object {

        private const val DATABASE_NAME = "notes-database"

        fun getInstance(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    }

}
package com.iothar.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.iothar.db.entity.Note
import com.iothar.db.model.NoteWithTags

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM notes WHERE nid = :nid")
    suspend fun find(nid: Int): Note

    @Transaction
    @Query("SELECT * FROM notes")
    suspend fun getNotesWithTags(): List<NoteWithTags>

    @Transaction
    @Query("SELECT * FROM notes WHERE nid = :nid")
    suspend fun findWithNotes(nid: Int): NoteWithTags

    @Insert
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}
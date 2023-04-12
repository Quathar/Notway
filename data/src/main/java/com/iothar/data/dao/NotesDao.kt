package com.iothar.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.iothar.data.entity.Note
import com.iothar.data.model.NoteWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE nid = :nid")
    suspend fun find(nid: Int): Note

    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotesWithTags(): Flow<List<NoteWithTags>>

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
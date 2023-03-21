package com.iothar.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.iothar.db.entity.Note
import com.iothar.db.model.NoteWithTags
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getAll(): Single<List<Note>>

    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    fun find(noteId: Int): Single<Note>
//
    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotesWithTags(): Single<List<NoteWithTags>>

    @Transaction
    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    fun findWithNotes(noteId: Int): Single<NoteWithTags>

    @Insert
    fun insertNote(note: Note): Single<Long>

    @Update
    fun updateNote(note: Note): Completable

    @Delete
    fun deleteNote(note: Note): Completable

}
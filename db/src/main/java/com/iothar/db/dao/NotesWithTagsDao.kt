package com.iothar.db.dao

import androidx.room.*
import com.iothar.db.entity.Note
import com.iothar.db.entity.NoteTagCrossRef
import com.iothar.db.entity.Tag
import com.iothar.db.model.NoteWithTags
import io.reactivex.rxjava3.core.Completable
import java.util.function.Function
import java.util.stream.Collectors

@Dao
abstract class NotesWithTagsDao {

    @Insert
    protected abstract fun insertNote(note: Note): Long

    @Insert
    protected abstract fun insertTags(tags: List<Tag>): List<Long>

    @Insert
    protected abstract fun insertCrossRefs(noteTagCrossRefs: List<NoteTagCrossRef>)

    @Query("DELETE FROM NoteTagCrossRef WHERE noteId = :noteId")
    protected abstract fun deleteCrossRefsByNoteId(noteId: Int)

    fun insertNoteWithTags(noteWithTags: NoteWithTags): Completable {
        return Completable.fromAction {
            //First insert the note and get its Id
            val noteId = insertNote(noteWithTags.note).toInt()

            //Then insert *only* the newly created tags
            val newTags = noteWithTags.tags
                .stream()
                .filter { tag -> tag.tagId == 0 }
                .collect(Collectors.toList())
            val newTagIds = insertTags(newTags)
            val existingTags = noteWithTags.tags
                .stream()
                .filter { tag -> tag.tagId != 0 }
                .collect(Collectors.toList())

            //Finally, create the note/tag cross references.
            insertCrossRefs(newTagIds, existingTags, noteId)
        }
    }

    @Update
    protected abstract fun updateNote(note: Note)

    @Update
    protected abstract fun updateTags(tags: List<Tag>)

    fun updateNoteWithTags(noteWithTags: NoteWithTags): Completable {
        return Completable.fromAction {
            //First update the note
            updateNote(noteWithTags.note)
            //Then insert *only* the newly created tags
            val newTags = noteWithTags.tags
                .stream()
                .filter { tag -> tag.tagId == 0 }
                .collect(Collectors.toList())
            val newTagIds = insertTags(newTags)
            //And update the existingTags in case they've somehow changed. To keep things simple, we update them without further checks
            val existingTags = noteWithTags.tags
                .stream()
                .filter { tag -> tag.tagId != 0 }
                .collect(Collectors.toList())
            updateTags(existingTags)

            //Finally, create the note/tag cross references.
            //Again, to keep things simple, we delete any existing cross reference
            deleteCrossRefsByNoteId(noteWithTags.note.noteId)
            //and then we insert them all
            insertCrossRefs(newTagIds, existingTags, noteWithTags.note.noteId)
        }
    }

    private fun insertCrossRefs(newTagIds: List<Long>, existingTags: List<Tag>, noteId: Int) {
        val tagIdsToCrossReference = ArrayList<Long>()
        tagIdsToCrossReference.addAll(newTagIds)
        tagIdsToCrossReference.addAll(
            existingTags
                .stream()
                .map { t -> t.tagId.toLong() }
                .collect(Collectors.toList()))
        val notesTagCrossRefs = ArrayList<NoteTagCrossRef>()
//        for (tagId in tagIdsToCrossReference) {
//            val noteTagCrossRef = NoteTagCrossRef()
//            noteTagCrossRef.noteId = noteId
//            noteTagCrossRef.tagId = tagId.toInt()
//            notesTagCrossRefs.add(noteTagCrossRef)
//        }
        insertCrossRefs(notesTagCrossRefs)
    }

    @Query("DELETE FROM NoteTagCrossRef WHERE tagId = :tagId")
    protected abstract fun deleteCrossRefsByTagId(tagId: Int)

    @Delete
    protected abstract fun deleteTag(tag: Tag)

    fun deleteTagAndCrossReferences(tag: Tag): Completable {
        return Completable.fromAction {
            //First delete all the crossreferences
            deleteCrossRefsByTagId(tag.tagId)
            //Then delete the tag proper
            deleteTag(tag)
        }
    }

    @Delete
    protected abstract fun deleteNote(note: Note)

    fun deleteNoteAndCrossReferences(note: Note): Completable {
        return Completable.fromAction {
            //First delete all the crossreferences
            deleteCrossRefsByNoteId(note.noteId)
            //Then delete the note proper
            deleteNote(note)
        }
    }
}

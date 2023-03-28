package com.iothar.db.dao

import androidx.room.*
import com.iothar.db.entity.Note
import com.iothar.db.entity.NoteTagCrossRef
import com.iothar.db.entity.Tag
import com.iothar.db.model.NoteWithTags

@Dao
abstract class NotesWithTagsDao {

    @Insert
    protected abstract suspend fun insertNote(note: Note): Long

    @Insert
    protected abstract suspend fun insertTags(tags: List<Tag>): List<Long>

    @Insert
    protected abstract suspend fun insertCrossRefs(noteTagCrossRefs: List<NoteTagCrossRef>)

    @Query("DELETE FROM note_tag_ref WHERE nid = :nid")
    protected abstract suspend fun deleteCrossRefsByNoteId(nid: Int)

    suspend fun insertNoteWithTags(noteWithTags: NoteWithTags) {
        //First insert the note and get its Id
        val noteId = insertNote(noteWithTags.note).toInt()

        //Then insert *only* the newly created tags
        val newTags = noteWithTags.tags.filter { tag -> tag.tid == 0 }
        val newTagIds = insertTags(newTags)
        val existingTags = noteWithTags.tags.filter { tag -> tag.tid != 0 }

        //Finally, create the note/tag cross references.
        insertCrossRefs(newTagIds, existingTags, noteId)
    }

    @Update
    protected abstract suspend fun updateNote(note: Note)

    @Update
    protected abstract suspend fun updateTags(tags: List<Tag>)

    suspend fun updateNoteWithTags(noteWithTags: NoteWithTags) {
        //First update the note
        updateNote(noteWithTags.note)
        //Then insert *only* the newly created tags
        val newTags = noteWithTags.tags.filter { tag -> tag.tid == 0 }
        val newTagIds = insertTags(newTags)
        //And update the existingTags in case they've somehow changed. To keep things simple, we update them without further checks
        val existingTags = noteWithTags.tags.filter { tag -> tag.tid != 0 }
        updateTags(existingTags)

        //Finally, create the note/tag cross references.
        //Again, to keep things simple, we delete any existing cross reference
        deleteCrossRefsByNoteId(noteWithTags.note.nid)
        //and then we insert them all
        insertCrossRefs(newTagIds, existingTags, noteWithTags.note.nid)
    }

    private suspend fun insertCrossRefs(newTagIds: List<Long>, existingTags: List<Tag>, nid: Int) {
        val tagIdsToCrossReference = ArrayList<Long>()
        tagIdsToCrossReference.addAll(newTagIds)
        tagIdsToCrossReference.addAll(existingTags.map { t -> t.tid.toLong() })
        val notesTagCrossRefs = ArrayList<NoteTagCrossRef>()
        for (tagId in tagIdsToCrossReference) {
            val noteTagCrossRef = NoteTagCrossRef.empty()
            noteTagCrossRef.nid = nid
            noteTagCrossRef.tid = tagId.toInt()
            notesTagCrossRefs.add(noteTagCrossRef)
        }
        insertCrossRefs(notesTagCrossRefs)
    }

    @Query("DELETE FROM note_tag_ref WHERE tid = :tid")
    protected abstract suspend fun deleteCrossRefsByTagId(tid: Int)

    @Delete
    protected abstract suspend fun deleteTag(tag: Tag)

    suspend fun deleteTagAndCrossReferences(tag: Tag) {
        //First delete all the crossreferences
        deleteCrossRefsByTagId(tag.tid)
        //Then delete the tag proper
        deleteTag(tag)
    }

    @Delete
    protected abstract suspend fun deleteNote(note: Note)

    suspend fun deleteNoteAndCrossReferences(note: Note) {
        //First delete all the crossreferences
        deleteCrossRefsByNoteId(note.nid)
        //Then delete the note proper
        deleteNote(note)
    }
}

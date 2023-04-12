package com.iothar.android.ui.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iothar.data.dao.NotesDao
import com.iothar.data.dao.NotesWithTagsDao
import com.iothar.data.dao.TagsDao
import com.iothar.data.entity.Tag
import com.iothar.data.model.NoteWithTags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val tagsDao:          TagsDao,
    private val notesDao:         NotesDao,
    private val notesWithTagsDao: NotesWithTagsDao,
) : ViewModel() {

    val noteId       = MutableStateFlow(0)
    val noteWithTags = MutableStateFlow(NoteWithTags.empty())
    val tags         = MutableStateFlow(emptyList<Tag>())

    init {
        viewModelScope.launch {
            tags.update    { tagsDao.getAll() }
            noteId.collect { nid ->
                if (nid > 0)
                    noteWithTags.update { notesDao.findWithNotes(nid) }
            }
        }
    }

    fun notifySaveNote(noteWithTags: NoteWithTags) =
        viewModelScope.launch {
            if (noteWithTags.note.nid > 0)
                notesWithTagsDao.updateNoteWithTags(noteWithTags)
            else
                notesWithTagsDao.insertNoteWithTags(noteWithTags)
        }

}
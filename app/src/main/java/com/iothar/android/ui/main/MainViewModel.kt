package com.iothar.android.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iothar.data.dao.NotesDao
import com.iothar.data.dao.NotesWithTagsDao
import com.iothar.data.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notesDao:         NotesDao,
    private val notesWithTagsDao: NotesWithTagsDao,
) : ViewModel() {

    val notes = notesDao
        .getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun notifyDeleteNote(note: Note) =
        viewModelScope.launch {
            notesWithTagsDao.deleteNoteAndCrossReferences(note)
        }

}
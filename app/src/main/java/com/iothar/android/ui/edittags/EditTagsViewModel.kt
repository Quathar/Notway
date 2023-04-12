package com.iothar.android.ui.edittags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iothar.data.dao.NotesWithTagsDao
import com.iothar.data.dao.TagsDao
import com.iothar.data.entity.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTagsViewModel @Inject constructor(
    private val tagsDao:          TagsDao,
    private val notesWithTagsDao: NotesWithTagsDao,
) : ViewModel() {

    val tags = tagsDao
        .getTagsWithNotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun notifySaveTag(tag: Tag) =
        viewModelScope.launch {
            if (tag.tid > 0)
                tagsDao.updateTag(tag)
            else
                tagsDao.insertTag(tag)
        }

    fun notifyDeleteTag(tag: Tag) =
        viewModelScope.launch {
            notesWithTagsDao.deleteTagAndCrossReferences(tag)
        }

}
package com.iothar.android.ui.editnote

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.iothar.android.R
import com.iothar.android.databinding.ActivityEditNoteBinding
import com.iothar.android.databinding.ItemChipBinding
import com.iothar.android.dialog.NewTagDialog
import com.iothar.android.ui.main.MainActivity
import com.iothar.data.entity.Tag
import com.iothar.data.model.NoteWithTags
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class EditNoteActivity : AppCompatActivity(), NewTagDialog.NewTagDialogListener {

    // <<-CONSTANTS->>
    companion object {
        const val NOTE_ID_KEY = "NOTE_ID"
    }

    // <<-FIELDS->>
    private val _vm     by viewModels<EditNoteViewModel>()
    private var _noteId by Delegates.notNull<Int>()
    private lateinit var _binding: ActivityEditNoteBinding

    // <<-METHODS->>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _noteId  = intent.getIntExtra(NOTE_ID_KEY, 0)
        _binding = ActivityEditNoteBinding.inflate(layoutInflater)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = if (_noteId == 0)
            getString(R.string.title_add_note) else
            getString(R.string.title_edit_note)

        _vm.noteId.update { _noteId }

        _binding.apply {
            setContentView(root)

            lifecycleScope.launch {
                _vm.tags.combine(_vm.noteWithTags) { tags, noteWithTags ->
                    Pair(tags, noteWithTags)
                }.collect { (tags, noteWithTags) ->

                    noteTags.removeAllViews()

                    tags.forEach { tag ->
                        val chip       = ItemChipBinding.inflate(layoutInflater, noteTags, false).root
                        chip.text      = tag.tag
                        chip.id        = tag.tid
                        chip.isChecked = noteWithTags.tags.map { it.tid }.contains(chip.id)
                        noteTags.addView(chip)
                    }

                    noteTitle.setText(noteWithTags.note.title)
                    noteBody.setText(noteWithTags.note.body)

                }
            }
        }
    }

    private fun saveNote() = with(_binding) {
        val noteWithTags        = NoteWithTags.empty()
        noteWithTags.note.nid   = _noteId
        noteWithTags.note.title = noteTitle.text.toString()
        noteWithTags.note.body  = noteBody.text.toString()
        noteWithTags.tags       = noteTags.children.mapNotNull {
            val chip = it as Chip
            if (chip.isChecked) Tag(chip.id, chip.text.toString()) else null
        }.toList()

        _vm.notifySaveNote(noteWithTags)

        startActivity(
            Intent(this@EditNoteActivity, MainActivity::class.java)
        )
    }

    override fun onDialogOkClick(newTag: String) = with(_binding) {
        if (newTag.isBlank()) return

        val tag = Tag.empty()
        tag.tag = newTag

        val chip       = ItemChipBinding.inflate(layoutInflater, noteTags, false).root
        chip.text      = tag.tag
        chip.id        = tag.tid
        chip.isChecked = true
        noteTags.addView(chip)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.add_tag   -> {
                NewTagDialog().show(supportFragmentManager, "dialog")
                true
            }

            R.id.save_note -> {
                saveNote()
                true
            }

            else           -> super.onOptionsItemSelected(item)
        }

}
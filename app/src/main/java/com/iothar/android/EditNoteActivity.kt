package com.iothar.android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.iothar.android.dialog.NewTagDialog
import com.iothar.db.AppDatabase
import com.iothar.db.entity.Tag
import com.iothar.db.model.NoteWithTags
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class EditNoteActivity : AppCompatActivity(), NewTagDialog.NewTagDialogListener {

    // <<-CONSTANTS->>
    companion object {
        private val TAG         = EditNoteActivity::class.java.name
        const   val NOTE_ID_KEY = "NOTE_ID"
    }

    // <<-FIELD->>
    private lateinit var _appDatabase: AppDatabase
    private lateinit var _note:        NoteWithTags
    private lateinit var _noteTags:    ChipGroup
    private lateinit var _noteTitle:   EditText
    private lateinit var _noteBody:    TextInputEditText
    private lateinit var _allTags:     MutableList<Tag>
    private          var _noteId by    Delegates.notNull<Int>()

    // <<-METHODS->>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        _appDatabase = (application as RoomApplication).appDatabase
        _noteId      = intent.getIntExtra(NOTE_ID_KEY, 0)
        _noteTags    = findViewById(R.id.note_tags)
        _noteTitle   = findViewById(R.id.note_title)
        _noteBody    = findViewById(R.id.note_body)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = if (_noteId == 0)
            getString(R.string.title_add_note) else
            getString(R.string.title_edit_note)

        loadAllTags()
    }

    private fun loadAllTags() {
        lifecycleScope.launch {
            _allTags = _appDatabase
                .tagsDao()
                .getAll()
                .toMutableList()

            _allTags.forEach { tag ->
                val chip = layoutInflater.inflate(R.layout.chip, _noteTags, false) as Chip
                chip.text = tag.tag
                chip.id = tag.tid
                _noteTags.addView(chip)
            }

            loadNote()
        }
    }

    private fun loadNote() {
        if (_noteId > 0) {
            lifecycleScope.launch {
                _note = _appDatabase
                    .notesDao()
                    .findWithNotes(_noteId)

                _noteTitle.setText(_note.note.title)
                _noteBody.setText(_note.note.body)
                _note.tags.forEach { tag -> _noteTags.findViewById<Chip>(tag.tid).isChecked = true }
            }
        }
    }

    private fun saveNote() {
        _note            = NoteWithTags.empty()
        _note.note.nid   = _noteId
        _note.note.title = _noteTitle.text.toString()
        _note.note.body  = _noteBody.text.toString()
        _note.tags       = _allTags.filterIndexed { index, _ -> (_noteTags.getChildAt(index) as Chip).isChecked }

        lifecycleScope.launch {
            if (_noteId > 0)
                _appDatabase
                    .notesWithTagsDao()
                    .updateNoteWithTags(_note)
            else
                _appDatabase
                    .notesWithTagsDao()
                    .insertNoteWithTags(_note)

            startActivity(
                Intent(this@EditNoteActivity, MainActivity::class.java))
        }
    }

    override fun onDialogOkClick(newTag: String) {
        if (newTag.isBlank()) return

        val tag = Tag.empty()
        tag.tag = newTag
        _allTags.add(tag)

        val chip = layoutInflater.inflate(R.layout.chip, _noteTags, false) as Chip
        chip.text = tag.tag
        chip.id = tag.tid
        chip.isChecked = true
        _noteTags.addView(chip)
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
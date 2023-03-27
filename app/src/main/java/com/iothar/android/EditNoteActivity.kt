package com.iothar.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.iothar.android.dialog.TagDialog
import com.iothar.db.AppDatabase
import com.iothar.db.entity.Note
import kotlinx.coroutines.launch

class EditNoteActivity : AppCompatActivity() {

    // <<-CONSTANTS->>
    companion object {
        private val TAG: String = EditNoteActivity::class.java.name
        const val NOTE_ID_KEY = "NOTE_ID"
    }

    // <<-FIELD->>
    private lateinit var _appDatabase: AppDatabase
    private lateinit var _note: Note

    // <<-METHODS->>
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.title_add_note)

        _appDatabase = (application as RoomApplication).appDatabase
    }

    private fun buildNote() {
        val title = findViewById<EditText>(R.id.note_title)
        val body  = findViewById<TextInputEditText>(R.id.note_body)

        _note = Note.empty()
        _note.nid = intent.getIntExtra(NOTE_ID_KEY, 0)
        _note.title = title.toString()
        _note.body = body.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_edit_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.manage_tags -> {
                TagDialog().show(supportFragmentManager, "New Tag")

                true
            }
            R.id.save_icon -> {
                buildNote()
                lifecycleScope.launch {
                    if (_note.nid > 0)
                        _appDatabase.notesDao().updateNote(_note)
                    else _appDatabase.notesDao().insertNote(_note)
                    startActivity(Intent(this@EditNoteActivity, MainActivity::class.java))
                    println(_appDatabase.notesDao().getAll())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

}
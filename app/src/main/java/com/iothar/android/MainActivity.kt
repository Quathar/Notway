package com.iothar.android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iothar.android.recycler.note.NoteAdapter
import com.iothar.db.AppDatabase
import com.iothar.db.entity.Note
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // <<-FIELDS->>
    private lateinit var _appDatabase:   AppDatabase
    private lateinit var _recyclerNotes: RecyclerView
    private lateinit var _noteAdapter:   NoteAdapter
    private lateinit var _notes:         MutableList<Note>

    // <<-METHODS->>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _appDatabase = (application as RoomApplication).appDatabase

        lifecycleScope.launch {
            _notes = _appDatabase
                .notesDao()
                .getAll()
                .toMutableList()

            initRecyclerNotes()
            loadNotes()
        }
    }

    private fun initRecyclerNotes() {
        _recyclerNotes = findViewById(R.id.notes_recycler)
        _noteAdapter   = NoteAdapter(_notes, object : NoteAdapter.NoteClickListener {
            override fun onNoteEdit(position: Int) {
                startActivity(
                    Intent(this@MainActivity, EditNoteActivity::class.java)
                        .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, _notes[position].nid) })
            }

            override fun onNoteDelete(position: Int) {
                lifecycleScope.launch {
                    _appDatabase
                        .notesWithTagsDao()
                        .deleteNoteAndCrossReferences(_appDatabase.notesDao().find(position))
                    _notes.removeAt(position)
                    _noteAdapter.notifyItemRemoved(position)
                }
            }
        })
        _recyclerNotes.layoutManager = LinearLayoutManager(this@MainActivity)
        _recyclerNotes.adapter       = _noteAdapter
    }

    private fun loadNotes() {
        if (_notes.isEmpty()) return
        _noteAdapter.notifyItemRangeInserted(0, _notes.size)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.add_note    -> {
                startActivity(
                    Intent(this@MainActivity, EditNoteActivity::class.java)
                        .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, 0) })
                true
            }
            R.id.manage_tags -> {
                startActivity(
                    Intent(this@MainActivity, EditTagsActivity::class.java))
                true
            } else -> super.onOptionsItemSelected(item)
        }

}
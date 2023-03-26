package com.iothar.android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.iothar.db.entity.Note

class MainActivity : AppCompatActivity() {

    // <<-FIELD->>
    private lateinit var _notes: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

//        bar!!.

//        val appDatabase = (application as RoomApplication).appDatabase
//        appDatabase.notesDao().getAll()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { notes ->
//                _notes = notes.toMutableList()
//                val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//
//                recyclerView.adapter = NoteAdapter(_notes, object : NoteAdapter.NoteClickListener {
//                    override fun onNoteEdit(position: Int) {
//                        startActivity(
//                            Intent(this@MainActivity, EditNoteActivity::class.java)
//                                .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, _notes[position].noteId) }
//                        )
//                    }
//
//                    override fun onNoteDelete(position: Int) {
//                        appDatabase.notesDao().deleteNote(_notes[position])
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe {
//                                _notes.removeAt(position)
//                                recyclerView.adapter?.notifyItemRemoved(position)
//                            }
//                    }
//
//                })
//
//                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
//            }
//
//        findViewById<ImageButton>(R.id.add_student).setOnClickListener {
//            startActivity(
//                Intent(this@MainActivity, EditNoteActivity::class.java)
//                    .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, 0) }
//            )
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.add_note -> {
                startActivity(
                    Intent(this@MainActivity, EditNoteActivity::class.java)
                        .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, 0) }
                             )
                true
            }

            R.id.manage_tags -> {
                startActivity(
                    Intent(this@MainActivity, EditTagsActivity::class.java)
                             )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

}
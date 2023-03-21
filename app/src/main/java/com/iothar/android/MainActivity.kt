package com.iothar.android

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iothar.android.recycler.NoteAdapter
import com.iothar.db.entity.Note
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var _notes: MutableList<Note>

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appDatabase = (application as RoomApplication).appDatabase
        appDatabase.notesDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { notes ->
                _notes = notes.toMutableList()
                val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

                recyclerView.adapter = NoteAdapter(_notes, object : NoteAdapter.NoteClickListener {
                    override fun onNoteEdit(position: Int) {
                        startActivity(
                            Intent(this@MainActivity, EditNoteActivity::class.java)
                                .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, _notes[position].noteId) }
                        )
                    }

                    override fun onNoteDelete(position: Int) {
                        appDatabase.notesDao().deleteNote(_notes[position])
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                _notes.removeAt(position)
                                recyclerView.adapter?.notifyItemRemoved(position)
                            }
                    }

                })

                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            }

        findViewById<ImageButton>(R.id.add_student).setOnClickListener {
            startActivity(
                Intent(this@MainActivity, EditNoteActivity::class.java)
                    .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, 0) }
            )
        }
    }

}
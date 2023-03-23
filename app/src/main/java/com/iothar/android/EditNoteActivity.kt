package com.iothar.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.iothar.db.entity.Note
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class EditNoteActivity : AppCompatActivity() {

    companion object {
        private val TAG: String = EditNoteActivity::class.java.name
        const val NOTE_ID_KEY = "NOTE_ID"
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        setSupportActionBar(findViewById(R.id.custom_bar))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.title_add_note)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        binding.myToolbar.setupWithNavController(navController, appBarConfiguration)

//        val appDatabase = (application as RoomApplication).appDatabase

//        val noteConsumer = Consumer { note: Note ->
//            val editStudentNameText          = findViewById<EditText>(R.id.edit_student_name)
//            val editStudentFirstSurnameText  = findViewById<EditText>(R.id.edit_student_first_surname)
//            val editStudentSecondSurnameText = findViewById<EditText>(R.id.edit_student_second_surname)
//            editStudentNameText.setText(note.noteId)
//            editStudentFirstSurnameText.setText(note.title)
//            editStudentSecondSurnameText.setText(note.body)
//
//            findViewById<Button>(R.id.save_student_button).setOnClickListener {
//                note.noteId = editStudentNameText.text.toString().toInt()
//                note.title  = editStudentFirstSurnameText.text.toString()
//                note.body   = editStudentSecondSurnameText.text.toString()
//
//                val navigateToMainActivityAction = Action {
//                    startActivity(
//                        Intent(this@EditNoteActivity, MainActivity::class.java)
//                    )
//                }
//
//                if (note.noteId > 0)
//                    appDatabase.notesDao()
//                        .updateNote(note)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(navigateToMainActivityAction)
//                else
//                    appDatabase.notesDao()
//                        .insertNote(note)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(navigateToMainActivityAction)
//            }
//        }
//        val noteId = intent.getIntExtra(NOTE_ID_KEY, 0)
//
//        if (noteId > 0)
//            appDatabase.notesDao()
//                .find(noteId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(noteConsumer)
//        else
//            noteConsumer.accept(Note(-1, "", ""))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_edit_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.manage_tags -> {
            //Dialog
//            startActivity(
//                Intent(this@EditNoteActivity, EditNoteActivity::class.java)
//                    .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, 0) }
//            )
            true
        }
        R.id.save_icon -> {
            // Que aparezca la ventana de administrar tags
//            startActivity(
//                Intent(this@MainActivity, EditNoteActivity::class.java)
//                    .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, 0) }
//            )
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
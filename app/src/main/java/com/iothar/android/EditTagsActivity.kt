package com.iothar.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class EditTagsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tags)
        setSupportActionBar(findViewById(R.id.custom_bar))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.edit_tags)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_edit_tags_menu, menu)
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
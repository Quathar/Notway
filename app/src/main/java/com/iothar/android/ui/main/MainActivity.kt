package com.iothar.android.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.iothar.android.ui.editnote.EditNoteActivity
import com.iothar.android.ui.edittags.EditTagsActivity
import com.iothar.android.R
import com.iothar.android.databinding.ActivityMainBinding
import com.iothar.android.databinding.ItemSwitchBinding
import com.iothar.android.recycler.note.NoteAdapter
import com.iothar.data.entity.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // <<-FIELDS->>
    private val _vm by viewModels<MainViewModel>()
    private lateinit var _noteAdapter: NoteAdapter

    // <<-METHODS->>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            _noteAdapter                = buildNoteAdapter()
            notesRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
            notesRecycler.adapter       = _noteAdapter

            lifecycleScope.launch {
                _vm.notes.collect { _noteAdapter.submitList(it) }
            }
        }
    }

    private fun buildNoteAdapter() =
        NoteAdapter(object : NoteAdapter.NoteClickListener {

            override fun onNoteEdit(nid: Int) {
                startActivity(
                    Intent(this@MainActivity, EditNoteActivity::class.java)
                        .apply { putExtra(EditNoteActivity.NOTE_ID_KEY, nid) })
            }

            override fun onNoteDelete(note: Note) {
                _vm.notifyDeleteNote(note)
            }

        })

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val menuSwitch = menu?.findItem(R.id.switch_theme)
        menuSwitch?.setActionView(R.layout.item_switch)

        ItemSwitchBinding.bind(menuSwitch?.actionView!!).apply {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                switchButton.isChecked = true
                switchIcon.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
            }

            switchButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    switchIcon.setImageResource(R.drawable.ic_baseline_nightlight_24)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    switchIcon.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
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
                    Intent(this@MainActivity, EditTagsActivity::class.java)
                )
                true
            }

            else             -> super.onOptionsItemSelected(item)
        }

}
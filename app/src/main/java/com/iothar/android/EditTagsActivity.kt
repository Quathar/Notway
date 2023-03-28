package com.iothar.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iothar.android.recycler.tag.TagAdapter
import com.iothar.db.AppDatabase
import com.iothar.db.model.TagWithNotes
import kotlinx.coroutines.launch

class EditTagsActivity : AppCompatActivity() {

    private lateinit var _appDatabase:  AppDatabase
    private lateinit var _recyclerTags: RecyclerView
    private lateinit var _tagAdapter:   TagAdapter
    private lateinit var _tags:         MutableList<TagWithNotes>
    private lateinit var _tag:          TagWithNotes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tags)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.edit_tags)

        _appDatabase = (application as RoomApplication).appDatabase

        lifecycleScope.launch {
            _tags = _appDatabase
                .tagsDao()
                .getTagsWithNotes()
                .toMutableList()

            initRecyclerTags()
            loadTags()
        }
    }

    private fun initRecyclerTags() {
        _recyclerTags = findViewById(R.id.tags_recycler)
        _tagAdapter   = TagAdapter(_tags, object : TagAdapter.TagClickListener {
            override fun onTagSave(position: Int, tagName: String) {
                _tag     = _tags[position]
                _tag.tag.tag = tagName

                lifecycleScope.launch {
                    if (_tag.tag.tid > 0)
                        _appDatabase
                            .tagsDao()
                            .updateTag(_tag.tag)
                    else
                        _appDatabase
                            .tagsDao()
                            .insertTag(_tag.tag)
                }
            }

            override fun onTagDelete(position: Int) {
                lifecycleScope.launch {
                    _appDatabase
                        .notesWithTagsDao()
                        .deleteTagAndCrossReferences(_tags[position].tag)

                    _tags.removeAt(position)
                    _tagAdapter.notifyItemRemoved(position)
                }
            }
        })
        _recyclerTags.layoutManager = LinearLayoutManager(this@EditTagsActivity)
        _recyclerTags.adapter       = _tagAdapter
    }

    private fun loadTags() {
        if (_tags.isEmpty()) return
        _tagAdapter.notifyItemRangeInserted(0, _tags.size)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_tags, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.new_tag -> {
                _tags.add(TagWithNotes.empty())
                _tagAdapter.notifyItemInserted(_tags.size)
                true
            }

            else             -> super.onOptionsItemSelected(item)
        }

}
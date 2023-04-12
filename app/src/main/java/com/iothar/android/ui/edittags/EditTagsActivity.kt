package com.iothar.android.ui.edittags

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.iothar.android.R
import com.iothar.android.databinding.ActivityEditTagsBinding
import com.iothar.android.recycler.tag.TagAdapter
import com.iothar.data.entity.Tag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTagsActivity : AppCompatActivity() {

    // <<-FIELDS->>
    private val _vm by viewModels<EditTagsViewModel>()
    private lateinit var _tagAdapter: TagAdapter

    // <<-METHODS->>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.edit_tags)

        ActivityEditTagsBinding.inflate(layoutInflater).apply {
            setContentView(root)
            _tagAdapter                = buildTagAdapter()
            tagsRecycler.layoutManager = LinearLayoutManager(this@EditTagsActivity)
            tagsRecycler.adapter       = _tagAdapter

            lifecycleScope.launch {
                _vm.tags.collect { _tagAdapter.submitList(it) }
            }
        }
    }

    private fun buildTagAdapter() =
        TagAdapter(object : TagAdapter.TagClickListener {

            override fun onTagSave(tag: Tag, name: String) {
                tag.tag = name
                _vm.notifySaveTag(tag)
            }

            override fun onTagDelete(tag: Tag) {
                _vm.notifyDeleteTag(tag)
            }

        })

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_tags, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.new_tag -> {
                _tagAdapter.addBlankItem()
                true
            }

            else         -> super.onOptionsItemSelected(item)
        }

}
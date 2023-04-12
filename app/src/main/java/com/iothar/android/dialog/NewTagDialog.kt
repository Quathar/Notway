package com.iothar.android.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.iothar.android.R
import com.iothar.android.databinding.DialogNewTagBinding

class NewTagDialog : DialogFragment() {

    // <<-INTERFACE->>
    interface NewTagDialogListener {
        fun onDialogOkClick(newTag: String)
    }

    // <<-FIELD->>
    private lateinit var _listener: NewTagDialogListener

    // <<-METHODS->>
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogNewTagBinding.inflate(layoutInflater)

        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.manage_tags)
            .setView(binding.root)
            .setNegativeButton(getString(R.string.cancel), null)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                _listener.onDialogOkClick(binding.tag.text.toString())
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _listener = context as NewTagDialogListener
    }

}
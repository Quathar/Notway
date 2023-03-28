package com.iothar.android.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.iothar.android.R

class NewTagDialog : DialogFragment() {

    // <<-INTERFACE->>
    interface NewTagDialogListener {
        fun onDialogOkClick(newTag: String)
    }

    // <<-FIELD->>
    private lateinit var _listener: NewTagDialogListener

    // <<-METHODS->>
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.manage_tags)
            .setView(activity?.layoutInflater!!.inflate(R.layout.dialog_new_tag, null))
            .setNegativeButton(getString(R.string.cancel), null)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val tagEditText = dialog!!.findViewById<EditText>(R.id.tag)
                _listener.onDialogOkClick(tagEditText.text.toString())
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _listener = context as NewTagDialogListener
    }

}
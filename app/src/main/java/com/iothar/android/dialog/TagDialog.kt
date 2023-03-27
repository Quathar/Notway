package com.iothar.android.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.iothar.android.R

class TagDialog : DialogFragment() {

    // <<-INTERFACE->>
    interface NewTagDialogListener {
        fun onDialogClick(newTag: String)
    }

    // <<-FIELD->>
    private lateinit var _listener: NewTagDialogListener

    // <<-METHODS->>
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setMessage(R.string.manage_tags)
                .setView(activity?.layoutInflater!!.inflate(R.layout.dialog_new_tag, null))
                .setPositiveButton(getString(R.string.ok)) { dialog, id ->
                    // Add Functionality. Enter the correct ID
                    val tagEditText = getDialog()!!.findViewById<EditText>(R.id.manage_tags)
                    _listener.onDialogClick(tagEditText.text.toString())
                }
                .setNegativeButton(getString(R.string.cancel), null)
            .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}
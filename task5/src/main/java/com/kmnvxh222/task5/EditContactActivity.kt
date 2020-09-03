package com.kmnvxh222.task5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_contact.*
import java.io.Serializable

class EditContactActivity : AppCompatActivity() {

    private var editContact: Contacts? = null
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        toolbar.setNavigationOnClickListener {saveEditData(editContact)}
        getData()
        removeButton.setOnClickListener {removeData()}
    }

    private fun getData() {
        editContact = intent.getSerializableExtra("EDIT_CONTACT") as? Contacts
        id = intent.getSerializableExtra("ID") as String
        if (editContact != null) {
            editTextName.setText(editContact!!.name)
            editTextInfo.setText(editContact!!.info)
        }
        editData(editContact!!)
    }

    private fun editData(editContact: Contacts) {
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                editContact.name = s.toString()
            }
        })

        editTextInfo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                editContact.info = s.toString()
            }
        })
    }

    private fun saveEditData(editContact: Contacts?) {
        if (editContact != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("EDITED_CONTACT", editContact as Serializable)
            intent.putExtra("ID", editContact.id)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun removeData() {
        id = intent.getSerializableExtra("ID") as String
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("REMOVE_CONTACT", id)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
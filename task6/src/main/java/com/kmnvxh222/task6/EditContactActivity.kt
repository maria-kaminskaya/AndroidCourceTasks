package com.kmnvxh222.task6

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.kmnvxh222.task6.Contacts
import com.kmnvxh222.task6.db.DBHelper
import kotlinx.android.synthetic.main.activity_edit_contact.editTextInfo
import kotlinx.android.synthetic.main.activity_edit_contact.editTextName
import kotlinx.android.synthetic.main.activity_edit_contact.removeButton
import kotlinx.android.synthetic.main.activity_edit_contact.toolbar

class EditContactActivity : AppCompatActivity() {

    private var editContact: Contacts? = null
    private lateinit var id: String
    private lateinit var db: SQLiteDatabase
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        dataBaseInitialization()

        getData()

        toolbar.setNavigationOnClickListener { saveEditData(editContact) }
        removeButton.setOnClickListener { removeData(id) }
    }

    private fun dataBaseInitialization() {
        dbHelper = DBHelper(this, null)
        db = dbHelper.writableDatabase
    }

    private fun getData() {
        id = intent.getSerializableExtra("ID") as String
        editContact = dbHelper.getContactByID(id, db)
        editTextName.setText(editContact?.name)
        editTextInfo.setText(editContact?.info)
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
            dbHelper.updateContact(editContact, db)
            finish()
        }
    }

    private fun removeData(id: String) {
        dbHelper.deleteContact(id, db)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}
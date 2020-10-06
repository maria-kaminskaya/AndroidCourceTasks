package com.kmnvxh222.task6

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.kmnvxh222.task6.db.DBHelper
import com.kmnvxh222.task6.db.DBInterface
import com.kmnvxh222.task6.async.TreadCompletableRepository
import com.kmnvxh222.task6.model.Contact
import com.kmnvxh222.task6.settings.SharedPreferencesSettings
import kotlinx.android.synthetic.main.activity_edit_contact.editTextInfo
import kotlinx.android.synthetic.main.activity_edit_contact.editTextName
import kotlinx.android.synthetic.main.activity_edit_contact.removeButton
import kotlinx.android.synthetic.main.activity_edit_contact.toolbar

class EditContactActivity : AppCompatActivity() {

    private var editContact: Contact? = null
    private lateinit var id: String
    private lateinit var dbInterface: DBInterface
    private val settingsSharedPreferences = SharedPreferencesSettings()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        dataBaseInitialization()

        getData()

        toolbar.setNavigationOnClickListener { saveEditData(editContact) }
        removeButton.setOnClickListener { removeData(id) }
    }

    private fun dataBaseInitialization() {
        val dbHelper = DBHelper(this)
        dbInterface = settingsSharedPreferences.asyncWork(applicationContext,dbHelper)!!
    }

    private fun getData() {
        id = intent.getSerializableExtra("ID") as String
        dbInterface.getContactByID(id) {
            for (i in it) {
                if (i.id == id) {
                    editContact = i
                    editTextName.setText(editContact?.name)
                    editTextInfo.setText(editContact?.info)
                    editData(editContact!!)
                }
            }

        }
    }

    private fun editData(editContact: Contact) {
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

    private fun saveEditData(editContact: Contact?) {
        if (editContact != null) {
            dbInterface.updateContact(editContact) {
                finish()
            }
        }
    }

    private fun removeData(id: String) {
        dbInterface.deleteContact(id) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbInterface.close()
    }
}
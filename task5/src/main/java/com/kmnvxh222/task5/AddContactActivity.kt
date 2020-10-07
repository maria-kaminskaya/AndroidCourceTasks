package com.kmnvxh222.task5

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kmnvxh222.task5.db.DBHelper
import kotlinx.android.synthetic.main.activity_add_contact.editTextInfo
import kotlinx.android.synthetic.main.activity_add_contact.editTextName
import kotlinx.android.synthetic.main.activity_add_contact.radioGroup
import kotlinx.android.synthetic.main.activity_add_contact.toolbar

class AddContactActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        dataBaseInitialization()

        toolbar.setNavigationOnClickListener { saveContact(getData(checkTypeInfo())) }
    }

    private fun dataBaseInitialization() {
        dbHelper = DBHelper(this, null)
        db = dbHelper.writableDatabase
    }

    private fun checkTypeInfo(): String {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.radioButtonPhone -> EnumTypeInfo.phone.toString()
            R.id.radioButtonEmail -> EnumTypeInfo.email.toString()
            else -> EnumTypeInfo.info.toString()
        }
    }

    private fun getData(typeInfo: String): Contacts {
        val name = editTextName.text.toString()
        val info = editTextInfo.text.toString()
        var id = "0"
        val symbols = "abcdefghijklmnopqrstuvwxyz"
        val randString = StringBuilder()
        val count = 5
        for (i in 0 until count) {
            id = randString.append(symbols[(Math.random() * symbols.length).toInt()]).toString()
        }
        return Contacts(id, name, checkTypeInfo(), info)
    }

    private fun saveContact(contact: Contacts?) {
        if (contact != null) {
            dbHelper.addContact(contact, db)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}
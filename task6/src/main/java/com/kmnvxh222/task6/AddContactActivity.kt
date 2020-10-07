package com.kmnvxh222.task6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kmnvxh222.task6.db.DBHelper
import com.kmnvxh222.task6.db.DBInterface
import com.kmnvxh222.task6.model.Contact
import com.kmnvxh222.task6.model.EnumTypeInfo
import com.kmnvxh222.task6.settings.SharedPreferencesSettings
import kotlinx.android.synthetic.main.activity_add_contact.editTextInfo
import kotlinx.android.synthetic.main.activity_add_contact.editTextName
import kotlinx.android.synthetic.main.activity_add_contact.radioGroup
import kotlinx.android.synthetic.main.activity_add_contact.toolbar

class AddContactActivity : AppCompatActivity() {

    private lateinit var dbInterface: DBInterface
    private val settingsSharedPreferences = SharedPreferencesSettings()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        dataBaseInitialization()

        toolbar.setNavigationOnClickListener { saveContact(getData(checkTypeInfo())) }

    }

    private fun dataBaseInitialization() {
        val dbHelper = DBHelper(this)
        dbInterface = settingsSharedPreferences.asyncWork(applicationContext, dbHelper)!!
    }

    private fun checkTypeInfo(): String {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.radioButtonPhone -> EnumTypeInfo.phone.toString()
            R.id.radioButtonEmail -> EnumTypeInfo.email.toString()
            else -> EnumTypeInfo.info.toString()
        }
    }

    private fun getData(typeInfo: String): Contact {
        val name = editTextName.text.toString()
        val info = editTextInfo.text.toString()
        var id = "0"
        val symbols = "abcdefghijklmnopqrstuvwxyz"
        val randString = StringBuilder()
        val count = 5
        for (i in 0 until count) {
            id = randString.append(symbols[(Math.random() * symbols.length).toInt()]).toString()
        }
        return Contact(id, name, typeInfo, info)
    }

    private fun saveContact(contact: Contact?) {
        if (contact != null) {
            dbInterface.addContact(contact) {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbInterface.close()
    }
}




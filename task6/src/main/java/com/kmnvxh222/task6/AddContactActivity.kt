package com.kmnvxh222.task6

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.kmnvxh222.task6.db.DBHelper
import com.kmnvxh222.task6.db.DBInterface
import com.kmnvxh222.task6.async.TreadCompletableRepository
import com.kmnvxh222.task6.model.Contact
import com.kmnvxh222.task6.model.EnumTypeInfo
import com.kmnvxh222.task6.settings.SharedPreferencesSettings
import kotlinx.android.synthetic.main.activity_add_contact.editTextInfo
import kotlinx.android.synthetic.main.activity_add_contact.editTextName
import kotlinx.android.synthetic.main.activity_add_contact.radioButtonEmail
import kotlinx.android.synthetic.main.activity_add_contact.radioButtonPhone
import kotlinx.android.synthetic.main.activity_add_contact.toolbar

class AddContactActivity : AppCompatActivity() {

    private lateinit var typeInfo: String
    private var contact: Contact? = null
    private lateinit var dbInterface: DBInterface
    private val settingsSharedPreferences = SharedPreferencesSettings()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        dataBaseInitialization()

        radioButtonPhone.setOnClickListener(radioButtonClickListener)
        radioButtonEmail.setOnClickListener(radioButtonClickListener)
        toolbar.setNavigationOnClickListener { saveContact(contact) }

    }

    private fun dataBaseInitialization() {
        val dbHelper = DBHelper(this)
        dbInterface = settingsSharedPreferences.asyncWork(applicationContext,dbHelper)!!
    }

    private val radioButtonClickListener = View.OnClickListener { v ->
        val radioButton = v as RadioButton
        typeInfo = when (radioButton.id) {
            R.id.radioButtonPhone -> EnumTypeInfo.phone.toString()
            R.id.radioButtonEmail -> EnumTypeInfo.email.toString()
            else -> EnumTypeInfo.info.toString()
        }
        getData(typeInfo)
    }

    private fun getData(typeInfo: String) {
        val name = editTextName.text.toString()
        val info = editTextInfo.text.toString()
        var id = "0"
        val symbols = "abcdefghijklmnopqrstuvwxyz"
        val randString = StringBuilder()
        val count = 5
        for (i in 0 until count) {
            id = randString.append(symbols[(Math.random() * symbols.length).toInt()]).toString()
        }
        contact = Contact(id, name, typeInfo, info)
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




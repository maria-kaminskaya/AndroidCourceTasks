package com.kmnvxh222.task5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContactActivity : AppCompatActivity() {

    private lateinit var typeInfo: String
    private var contact: Contacts? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        radioButtonPhone.setOnClickListener(radioButtonClickListener)
        radioButtonEmail.setOnClickListener(radioButtonClickListener)
        toolbar.setNavigationOnClickListener { saveContact(contact) }
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
        contact = Contacts(id, name, typeInfo, info)
    }

    private fun saveContact(contact: Contacts?) {
        if (contact != null) {
            val intent = Intent()
            intent.putExtra("NEW_CONTACT", contact)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
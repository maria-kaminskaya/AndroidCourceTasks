package com.kmnvxh222.task6

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kmnvxh222.task6.adapters.ContactsRecyclerAdapter
import com.kmnvxh222.task6.adapters.ContactsRecyclerAdapter.OnItemClickListener
import com.kmnvxh222.task6.db.DBHelper
import com.kmnvxh222.task6.db.DBInterface
import com.kmnvxh222.task6.model.Contact
import com.kmnvxh222.task6.settings.SettingsDialogFragment
import com.kmnvxh222.task6.settings.SharedPreferencesSettings
import kotlinx.android.synthetic.main.activity_main.buttonSetting
import kotlinx.android.synthetic.main.activity_main.floatingActionButton
import kotlinx.android.synthetic.main.activity_main.itemsNull
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.searchView

class MainActivity : AppCompatActivity() {

    private val contacts: MutableList<Contact> = ArrayList()
    private lateinit var adapter: ContactsRecyclerAdapter
    private val settingsDialogFragment = SettingsDialogFragment()
    private val settingsSharedPreferences = SharedPreferencesSettings()
    private lateinit var dbInterface: DBInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataBaseInitialization()

        adapter = ContactsRecyclerAdapter(contacts)
        adapter.setOnItemClickListener(adapterClickListener)

        getAllContacts()

        setContentResolver()

        recyclerView.let { it ->
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        adapter.setOnItemClickListener(adapterClickListener)

        floatingActionButton.setOnClickListener { addContact() }

        searchListener()
        settingsClick()
    }

    private fun setContentResolver(){
        val cursor: Cursor? = contentResolver.query(Uri.parse(URI_PATH), null, null, null, null)
        if (cursor != null) {
            val descriptionInd = cursor.getColumnIndex ("description")
            while (cursor.moveToNext()) {
                Log.d("MainActivity", cursor.getString(descriptionInd))
            }
            cursor.close()
        }
    }

    private fun dataBaseInitialization() {
        val dbHelper = DBHelper(this)
        dbInterface = settingsSharedPreferences.asyncWork(applicationContext, dbHelper)!!
    }

    private fun settingsClick() {
        buttonSetting.setOnClickListener {
            val manager = supportFragmentManager
            settingsDialogFragment.show(manager, "SettingsDialog")
        }
    }

    private val adapterClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val intent = Intent(this@MainActivity, EditContactActivity::class.java)
            intent.putExtra("ID", contacts[position].id)
            startActivity(intent)
        }
    }

    private fun addContact() {
        val intent = Intent(this, AddContactActivity::class.java)
        startActivity(intent)
    }

    private fun getAllContacts() {

        dbInterface.getAllContacts {
            contacts.clear()
            contacts.addAll(it)
            if (contacts.isEmpty()) {
                itemsNull.setText(R.string.no_contacts)
            } else {
                itemsNull.text = ""
            }
            adapter.updateList(contacts)
        }
    }

    private fun searchListener() {
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                search(s.toString())
            }
        })
    }

    fun search(text: String?) {
        val temp: MutableList<Contact> = ArrayList()
        for (c in contacts) {
            if (c.name.contains(text.toString())) {
                temp.add(c)
            }
        }
        adapter.updateList(temp)
    }

    override fun onDestroy() {
        super.onDestroy()
        dbInterface.close();
    }

    override fun onResume() {
        super.onResume()
        getAllContacts()
        adapter.notifyDataSetChanged()
    }

    companion object {
        private const val URI_PATH = "content://com.kmnvxh222.task9/data/contacts"
    }

}






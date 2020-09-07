package com.kmnvxh222.task5

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kmnvxh222.task5.ContactsRecyclerAdapter.OnItemClickListener
import com.kmnvxh222.task5.db.DBHelper
import kotlinx.android.synthetic.main.activity_main.floatingActionButton
import kotlinx.android.synthetic.main.activity_main.itemsNull
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.searchView

class MainActivity : AppCompatActivity() {

    private val contacts: MutableList<Contacts> = ArrayList()
    private lateinit var adapter: ContactsRecyclerAdapter
    private lateinit var db: SQLiteDatabase
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataBaseInitialization()

        adapter = ContactsRecyclerAdapter(contacts)

        getAllContacts()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter.setOnItemClickListener(adapterClickListener)

        floatingActionButton.setOnClickListener { addContact() }

        searchListener()
    }

    private fun dataBaseInitialization() {
        dbHelper = DBHelper(this, null)
        db = dbHelper.readableDatabase
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
        contacts.clear()
        contacts.addAll(dbHelper.getAllContacts(db))
        if (contacts.isEmpty()) {
            itemsNull.setText(R.string.no_contacts)
        } else {
            itemsNull.text = ""
        }
        adapter.notifyDataSetChanged()
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
        val temp: MutableList<Contacts> = ArrayList()
        for (c in contacts) {
            if (c.name.contains(text.toString())) {
                temp.add(c)
            }
        }
        adapter.updateList(temp)
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    override fun onResume() {
        super.onResume()
        getAllContacts()
        adapter.notifyDataSetChanged()
    }

}


package com.kmnvxh222.task5

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kmnvxh222.task5.ContactsRecyclerAdapter.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val contacts: MutableList<Contacts> = ArrayList()
    private lateinit var adapter: ContactsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ContactsRecyclerAdapter(contacts)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter.setOnItemClickListener(adapterClickListener)

        floatingActionButton.setOnClickListener{addContact()}

        searchListener()
        itemsNull.setText(R.string.no_contacts)
    }

    private val adapterClickListener = object :OnItemClickListener{
        override fun onItemClick(view: View, position: Int) {
            clickContact(position)
        }
    }

    private fun addContact(){
        val intent = Intent(this, AddContactActivity::class.java)
        startActivityForResult(intent, 2000)
    }

    private fun clickContact(position: Int) {
        val intent = Intent(this@MainActivity, EditContactActivity::class.java)
        intent.putExtra("EDIT_CONTACT", contacts[position])
        intent.putExtra("ID", contacts[position].id)
        startActivityForResult(intent, 1000)
    }

    private fun getData(data: Intent) {
        val newContact: Contacts = data.getSerializableExtra("NEW_CONTACT") as Contacts
        contacts.add(newContact)
        adapter.notifyDataSetChanged()
    }

    private fun editData(data: Intent) {
        val editContact = data.getSerializableExtra("EDITED_CONTACT") as? Contacts
        val id = data.getSerializableExtra("ID") as? String
        if (editContact != null) {
        for (i in contacts.indices) {
            if (contacts[i].id == id) {
                    contacts[i] = editContact
                }
            }
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
        val temp: MutableList<Contacts> = java.util.ArrayList<Contacts>()
        for (c in contacts) {
            if (c.name.contains(text.toString())) {
                temp.add(c)
            }
        }
        adapter.updateList(temp)
    }

    private fun removeContact(data: Intent) {
        val id = data.getSerializableExtra("REMOVE_CONTACT") as? String
        for (i in contacts) {
            if (i.id == id) {
                contacts.remove(i)
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                1000 -> {
                    removeContact(data)
                    editData(data)
                }
                2000 -> {
                    getData(data)
                    itemsNull.text = ""
                }
            }
        }
    }

}


package com.kmnvxh222.task9

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kmnvxh222.task9.adapters.ContactsRecyclerAdapter
import com.kmnvxh222.task9.db.DBHelper
import com.kmnvxh222.task9.model.Contact
import kotlinx.android.synthetic.main.activity_main.itemsNull
import kotlinx.android.synthetic.main.activity_main.recyclerView

class MainActivity : AppCompatActivity() {

    private val contacts: MutableList<Contact> = ArrayList()
    private lateinit var adapter: ContactsRecyclerAdapter
    private lateinit var db: SQLiteDatabase

    private lateinit var dbHelper:DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBaseInitialization()
        adapter = ContactsRecyclerAdapter(contacts)
        recyclerView.let { it ->
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        setContentResolver()
    }

    private fun setContentResolver(){
        val cursor: Cursor? = contentResolver.query(Uri.parse(URI_PATH), null, null, null, null)
            if (cursor!!.moveToFirst()) {
                while (cursor.moveToNext()) {
                    val cursorId = cursor.getString(0)
                    val cursorName = cursor.getString(1)
                    val cursorTypeInfo = cursor.getString(2)
                    val cursorInfo = cursor.getString(3)

                    val contact = Contact(cursorId, cursorName, cursorTypeInfo, cursorInfo)
                    contacts.add(contact)
                }
            }
            cursor.close()
        adapter.updateList(contacts)
    }


    private fun dataBaseInitialization() {
        dbHelper = DBHelper(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }


    companion object {
        private const val URI_PATH = "content://com.kmnvxh222.task6/data/contacts"
    }

}
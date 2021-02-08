package com.kmnvxh222.task6.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kmnvxh222.task6.model.Contact

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val db = readableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE $TABLE_CONTACTS ($COLUMN_ID TEXT PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_TYPEINFO TEXT," +
                "$COLUMN_INFO TEXT)")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun addContact(contact: Contact): Contact {
        val values = ContentValues()
        values.put(COLUMN_ID, contact.id)
        values.put(COLUMN_NAME, contact.name)
        values.put(COLUMN_TYPEINFO, contact.typeInfo)
        values.put(COLUMN_INFO, contact.info)

        db?.insert(TABLE_CONTACTS, null, values)
        return contact

    }

    fun deleteContact(id: String): String {
        val query = "SELECT * FROM $TABLE_CONTACTS WHERE $COLUMN_ID = \"$id\""
        val cursor = db?.rawQuery(query, null)

        if (cursor!!.moveToFirst()) {
            val cursorId = cursor.getString(0)
            db?.delete(TABLE_CONTACTS, "$COLUMN_ID=?", arrayOf(cursorId.toString()))
            cursor.close()
        }

        return id
    }

    fun getAllContacts(): MutableList<Contact> {
        val contacts: MutableList<Contact> = ArrayList()
        val cursor = db?.rawQuery("SELECT * FROM $TABLE_CONTACTS", null)

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
        return contacts
    }

    fun updateContact(contact: Contact): Contact {
        val values = ContentValues()
        values.put(COLUMN_ID, contact.id)
        values.put(COLUMN_NAME, contact.name)
        values.put(COLUMN_TYPEINFO, contact.typeInfo)
        values.put(COLUMN_INFO, contact.info)

        db?.update(TABLE_CONTACTS, values, "$COLUMN_ID=?", arrayOf(contact.id))

        return contact
    }

    fun getContactByID(id: String): Contact? {
        val query = "SELECT * FROM $TABLE_CONTACTS WHERE $COLUMN_ID = \"$id\""
        val cursor = db?.rawQuery(query, null)
        var contact: Contact? = null

        if (cursor!!.moveToFirst()) {
            val cursorId = cursor.getString(0)
            val cursorName = cursor.getString(1)
            val cursorTypeInfo = cursor.getString(2)
            val cursorInfo = cursor.getString(3)

            contact = Contact(cursorId, cursorName, cursorTypeInfo, cursorInfo)
            cursor.close()
        }
        return contact
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "contactsDB.db"
        const val TABLE_CONTACTS = "contacts"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPEINFO = "typeInfo"
        const val COLUMN_INFO = "info"
    }

    override fun close() {
        super.close()
        db.close()
    }

}
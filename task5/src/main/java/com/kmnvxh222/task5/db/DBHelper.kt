package com.kmnvxh222.task5.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kmnvxh222.task5.Contacts

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE $TABLE_CONTACTS ($COLUMN_ID TEXT PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_TYPEINFO TEXT," +
                "$COLUMN_INFO TEXT)")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun addContact(contact: Contacts, db: SQLiteDatabase?) {
        val values = ContentValues()
        values.put(COLUMN_ID, contact.id)
        values.put(COLUMN_NAME, contact.name)
        values.put(COLUMN_TYPEINFO, contact.typeInfo)
        values.put(COLUMN_INFO, contact.info)

        db?.insert(TABLE_CONTACTS, null, values)
    }

    fun deleteContact(id: String, db: SQLiteDatabase?) {
        val query = "SELECT * FROM $TABLE_CONTACTS WHERE $COLUMN_ID = \"$id\""
        val cursor = db?.rawQuery(query, null)

        if (cursor!!.moveToFirst()) {
            val cursorId = cursor.getString(0)
            db.delete(TABLE_CONTACTS, "$COLUMN_ID=?", arrayOf(cursorId.toString()))
            cursor.close()
        }
    }

    fun getAllContacts(db: SQLiteDatabase?): MutableList<Contacts> {
        val contacts: MutableList<Contacts> = ArrayList()
        val cursor = db?.rawQuery("SELECT * FROM $TABLE_CONTACTS", null)

        if (cursor!!.moveToFirst()) {
            while (cursor.moveToNext()) {
                val cursorId = cursor.getString(0)
                val cursorName = cursor.getString(1)
                val cursorTypeInfo = cursor.getString(2)
                val cursorInfo = cursor.getString(3)

                val contact = Contacts(cursorId, cursorName, cursorTypeInfo, cursorInfo)
                contacts.add(contact)
            }
        }
        cursor.close()
        return contacts
    }

    fun updateContact(contact: Contacts, db: SQLiteDatabase?) {
        val values = ContentValues()
        values.put(COLUMN_ID, contact.id)
        values.put(COLUMN_NAME, contact.name)
        values.put(COLUMN_TYPEINFO, contact.typeInfo)
        values.put(COLUMN_INFO, contact.info)

        db?.update(TABLE_CONTACTS, values, "$COLUMN_ID=?", arrayOf(contact.id))
    }

    fun getContactByID(id: String, db: SQLiteDatabase?): Contacts? {
        val query = "SELECT * FROM $TABLE_CONTACTS WHERE $COLUMN_ID = \"$id\""
        val cursor = db?.rawQuery(query, null)
        var contact: Contacts? = null

        if (cursor!!.moveToFirst()) {
            val cursorId = cursor.getString(0)
            val cursorName = cursor.getString(1)
            val cursorTypeInfo = cursor.getString(2)
            val cursorInfo = cursor.getString(3)

            contact = Contacts(cursorId, cursorName, cursorTypeInfo, cursorInfo)
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
}
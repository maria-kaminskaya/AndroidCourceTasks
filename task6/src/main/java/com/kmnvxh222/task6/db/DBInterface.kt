package com.kmnvxh222.task6.db

import android.database.sqlite.SQLiteDatabase
import com.kmnvxh222.task6.Contacts

interface DBInterface {

    fun addContact(contact: Contacts, db: SQLiteDatabase?)
    fun deleteContact(id: String, db: SQLiteDatabase?)
    fun getAllContacts(db: SQLiteDatabase?): MutableList<Contacts>
    fun updateContact(contact: Contacts, db: SQLiteDatabase?)
    fun getContactByID(id: String, db: SQLiteDatabase?): Contacts?

}

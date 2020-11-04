package com.kmnvxh222.task9.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val db = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE $TABLE_CONTACTS ($COLUMN_ID TEXT PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_TYPEINFO TEXT," +
                "$COLUMN_INFO TEXT)")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

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
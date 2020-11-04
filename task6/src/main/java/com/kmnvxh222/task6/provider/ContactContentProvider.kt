package com.kmnvxh222.task6.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.kmnvxh222.task6.db.DBHelper


class ContactContentProvider: ContentProvider(){

    private var dbHelper: DBHelper? = null

    private var uriMatcher: UriMatcher? = null

    init {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        if (uriMatcher!=null) {
            uriMatcher?.addURI(AUTHORITY, "data/contacts", URI_CONTACTS_CODE)
        }
    }

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context!!)
        return false
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        var cursor: Cursor? = null
        if (dbHelper!=null){
        when (uriMatcher!!.match(uri)) {
            1 -> cursor = dbHelper!!.db.query("contacts", projection, selection, selectionArgs, null, null, sortOrder)
        }
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return "com.kmnvxh222.task6/object"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (uriMatcher!!.match(uri) == URI_CONTACTS_CODE) {
            val id = dbHelper!!.db.insert("contacts", "", values)
            return Uri.withAppendedPath(uri, id.toString())
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    companion object {
        private const val AUTHORITY = "com.kmnvxh222.task6"
        private const val URI_CONTACTS_CODE = 1
    }

}
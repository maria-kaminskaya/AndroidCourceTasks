package com.kmnvxh222.task6.db.async;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kmnvxh222.task6.Contacts;
import com.kmnvxh222.task6.db.DBConst;
import com.kmnvxh222.task6.db.DBInterface;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 3.RxJava
 */
class DBHelperTread3 extends SQLiteOpenHelper implements DBInterface {

    public DBHelperTread3(@androidx.annotation.Nullable Context context, @androidx.annotation.Nullable String name, @androidx.annotation.Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBConst.DATABASE_NAME, factory, DBConst.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addContact(@NotNull Contacts contact, @Nullable SQLiteDatabase db) {

    }

    @Override
    public void deleteContact(@NotNull String id, @Nullable SQLiteDatabase db) {

    }

    @NotNull
    @Override
    public List<Contacts> getAllContacts(@Nullable SQLiteDatabase db) {
        return null;
    }

    @Override
    public void updateContact(@NotNull Contacts contact, @Nullable SQLiteDatabase db) {

    }

    @Nullable
    @Override
    public Contacts getContactByID(@NotNull String id, @Nullable SQLiteDatabase db) {
        return null;
    }
}

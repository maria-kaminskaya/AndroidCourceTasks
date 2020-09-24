package com.kmnvxh222.task6.db.async;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.kmnvxh222.task6.Contacts;
import com.kmnvxh222.task6.db.DBConst;
import com.kmnvxh222.task6.db.DBInterface;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

import static com.kmnvxh222.task6.db.DBConst.COLUMN_ID;
import static com.kmnvxh222.task6.db.DBConst.COLUMN_INFO;
import static com.kmnvxh222.task6.db.DBConst.COLUMN_NAME;
import static com.kmnvxh222.task6.db.DBConst.COLUMN_TYPEINFO;
import static com.kmnvxh222.task6.db.DBConst.TABLE_CONTACTS;

/**
 * 1.ThreadPoolExecutor + Handler
 */
class DBHelperTread1 extends SQLiteOpenHelper implements DBInterface {

    public DBHelperTread1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBConst.DATABASE_NAME, factory, DBConst.DATABASE_VERSION);
    }

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

    ThreadPoolExecutor threadPoolExecutor1 = (ThreadPoolExecutor) Executors.newFixedThreadPool (5);

    @Override
    public void onCreate(SQLiteDatabase db) {
        threadPoolExecutor.submit(() -> {
            String CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "(" + COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_TYPEINFO + " TEXT," +
                    COLUMN_INFO + " TEXT)");
            if(db != null) {
                db.execSQL(CREATE_CONTACTS_TABLE);
            }
        });
        threadPoolExecutor.shutdown();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addContact(@NotNull Contacts contact, @Nullable SQLiteDatabase db) {
        threadPoolExecutor.submit(() -> {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, contact.getId());
            values.put(COLUMN_NAME, contact.getName());
            values.put(COLUMN_TYPEINFO, contact.getTypeInfo());
            values.put(COLUMN_INFO, contact.getInfo());

            if(db != null) {
                db.insert(TABLE_CONTACTS, null, values);
            }
        });
        threadPoolExecutor.shutdown();
    }

    @Override
    public void deleteContact(@NotNull String id, @Nullable SQLiteDatabase db) {
        threadPoolExecutor.submit(() -> {
//            String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE" + COLUMN_ID + "= \"$id\"";
            if(db != null) {
////                Cursor cursor = db.rawQuery(query, null);
//                if(cursor.moveToFirst()){
//                    String cursorId = cursor.getString(0);
                String[] idList = new String[Integer.parseInt(id)];
                db.delete(TABLE_CONTACTS, "$COLUMN_ID=?", idList);
//                    cursor.close();
            }
//            }
        });
        threadPoolExecutor.shutdown();
    }

    @NotNull
    @Override
    public List<Contacts> getAllContacts(@Nullable SQLiteDatabase db) {
        List<Contacts> contacts = new ArrayList<>();
        threadPoolExecutor.submit(() -> {
            if(db != null) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
                if(cursor.moveToFirst()) {
                    while(cursor.moveToNext()) {
                        String cursorId = cursor.getString(0);
                        String cursorName = cursor.getString(2);
                        String cursorTypeInfo = cursor.getString(3);
                        String cursorInfo = cursor.getString(4);

                        Contacts contact = new Contacts(cursorId, cursorName, cursorTypeInfo, cursorInfo);
                        contacts.add(contact);
                    }
                }
                cursor.close();
            }
        });
        threadPoolExecutor.execute(contacts);
        threadPoolExecutor.shutdown();
        return contacts;
    }

    @Override
    public void updateContact(@NotNull Contacts contact, @Nullable SQLiteDatabase db) {
        threadPoolExecutor.submit(() -> {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, contact.getId());
            values.put(COLUMN_NAME, contact.getName());
            values.put(COLUMN_TYPEINFO, contact.getTypeInfo());
            values.put(COLUMN_INFO, contact.getInfo());

            String[] idList = new String[Integer.parseInt(contact.getId())];
            if(db != null) {
                db.update(TABLE_CONTACTS, values, "$COLUMN_ID=?", idList);
            }
        });
        threadPoolExecutor.shutdown();
    }

    @Nullable
    @Override
    public Contacts getContactByID(@NotNull String id, @Nullable SQLiteDatabase db) {
        Contacts contact = null;
        threadPoolExecutor.submit(() -> {
            String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE" + COLUMN_ID + "= \"$id\"";
            if(db != null) {
                Cursor cursor = db.rawQuery(query, null);
                if(cursor.moveToFirst()){
                    String cursorId = cursor.getString(0);
                    String cursorName = cursor.getString(2);
                    String cursorTypeInfo = cursor.getString(3);
                    String cursorInfo = cursor.getString(4);
//                String[] idList = new String[Integer.parseInt(id)];
//                db.delete(TABLE_CONTACTS, "$COLUMN_ID=?", idList);
                    contact = new Contacts(cursorId, cursorName, cursorTypeInfo, cursorInfo);
                    handler.post(contact);
                    cursor.close();
            }
            }
        });
        threadPoolExecutor.shutdown();
        return contact;
    }


    static class DBRunnable implements Runnable{
        @Override
        public void run() {

        }
    }

    static class DBCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            return null;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    };

}

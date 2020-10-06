package com.kmnvxh222.task6.db.async;

import android.os.Handler;

import com.kmnvxh222.task6.Contact;
import com.kmnvxh222.task6.db.DBHelper;
import com.kmnvxh222.task6.db.DBInterface;
import com.kmnvxh222.task6.db.DBListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadHandlerRepository implements DBInterface {

    private Handler handler = new Handler();
    private DBHelper dbHelper;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue());

    public ThreadHandlerRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void addContact(Contact contact, DBListener<Contact> listener) {

        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                dbHelper.addContact(contact);
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onDataReceived(contact);
            }
        });

    }

    @Override
    public void deleteContact(String id, DBListener<String> listener) {
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                dbHelper.deleteContact(id);
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onDataReceived(id);
            }
        });
    }

    @Override
    public void getAllContacts(DBListener<List<Contact>> listener) {
        List<Contact> list = new ArrayList<>();
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                list.addAll(dbHelper.getAllContacts());
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onDataReceived(list);
            }
        });
    }

    @Override
    public void updateContact(Contact contact, DBListener<Contact> listener) {
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                dbHelper.updateContact(contact);
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onDataReceived(contact);
            }
        });
    }

    @Override
    public void getContactByID(String id, DBListener<List<Contact>> listener) {
        List<Contact> list = new ArrayList<>();

        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                list.addAll(Collections.singleton(dbHelper.getContactByID(id)));
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onDataReceived(list);
            }
        });
    }

    @Override
    public void close() {
        dbHelper.close();
    }
}

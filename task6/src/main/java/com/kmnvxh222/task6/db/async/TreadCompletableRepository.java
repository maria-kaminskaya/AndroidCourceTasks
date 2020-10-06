package com.kmnvxh222.task6.db.async;

import com.kmnvxh222.task6.Contact;
import com.kmnvxh222.task6.db.DBHelper;
import com.kmnvxh222.task6.db.DBInterface;
import com.kmnvxh222.task6.db.DBListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TreadCompletableRepository implements DBInterface {

    private DBHelper dbHelper;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue());

    public TreadCompletableRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void addContact(Contact contact, DBListener<Contact> listener) {
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    dbHelper.addContact(contact);
                }
            }, threadPoolExecutor);
            future.thenAccept(result -> listener.onDataReceived(contact));
            future.get();
        }catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContact(String id, DBListener<String> listener) {
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    dbHelper.deleteContact(id);
                }
            }, threadPoolExecutor);
            future.thenAccept(result -> listener.onDataReceived(id));
            future.get();
        }catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllContacts(DBListener<List<Contact>> listener) {
        List<Contact> list = new ArrayList<>();
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    list.addAll(dbHelper.getAllContacts());
                }
            }, threadPoolExecutor);
            future.thenAccept(result -> listener.onDataReceived(list));
            future.get();
        }catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateContact(Contact contact, DBListener<Contact> listener) {
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    dbHelper.updateContact(contact);
                }
            }, threadPoolExecutor);
            future.thenAccept(result -> listener.onDataReceived(contact));
            future.get();
        }catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getContactByID(String id, DBListener<List<Contact>> listener) {
        List<Contact> list = new ArrayList<>();
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    list.addAll(Collections.singleton(dbHelper.getContactByID(id)));
                }
            }, threadPoolExecutor);
            future.thenAccept(result -> listener.onDataReceived(list));
            future.get();
        }catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        dbHelper.close();
    }
}

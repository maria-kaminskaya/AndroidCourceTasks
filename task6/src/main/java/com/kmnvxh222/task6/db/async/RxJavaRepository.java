package com.kmnvxh222.task6.db.async;

import com.kmnvxh222.task6.Contact;
import com.kmnvxh222.task6.db.DBHelper;
import com.kmnvxh222.task6.db.DBInterface;
import com.kmnvxh222.task6.db.DBListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

import static java.util.Collections.*;

public class RxJavaRepository implements DBInterface {

    private DBHelper dbHelper;
    private Disposable observable;

    public RxJavaRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void addContact(Contact contact, DBListener<Contact> listener) {
        observable = Observable.create((ObservableOnSubscribe<Contact>) emitter -> {
            emitter.onNext(dbHelper.addContact(contact));
            emitter.onComplete();})
                .subscribeOn(Schedulers.io())
                .subscribe(listener::onDataReceived, throwable -> {});
    }

    @Override
    public void deleteContact(String id, DBListener<String> listener) {
        observable = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext(dbHelper.deleteContact(id));
            emitter.onComplete();})
                .subscribeOn(Schedulers.io())
                .subscribe(listener::onDataReceived, throwable -> {});
    }

    @Override
    public void getAllContacts(DBListener<List<Contact>> listener) {
          observable = Observable.create((ObservableOnSubscribe<List<Contact>>) emitter -> {
            emitter.onNext(dbHelper.getAllContacts());
            emitter.onComplete();})
                .subscribeOn(Schedulers.io())
                .subscribe(contact -> listener.onDataReceived(new ArrayList<Contact>(contact)), throwable -> {});
    }

    @Override
    public void updateContact(Contact contact, DBListener<Contact> listener) {
        observable = Observable.create((ObservableOnSubscribe<Contact>) emitter -> {
            emitter.onNext(dbHelper.updateContact(contact));
            emitter.onComplete();})
                .subscribeOn(Schedulers.io())
                .subscribe(listener::onDataReceived, throwable -> {});

    }

    @Override
    public void getContactByID(String id, DBListener<List<Contact>> listener) {
        observable = Observable.create((ObservableOnSubscribe<Contact>) emitter -> {
            emitter.onNext(dbHelper.getContactByID(id));
            emitter.onComplete();})
                .subscribeOn(Schedulers.io())
                .subscribe(contact -> listener.onDataReceived(new ArrayList<Contact>(Collections.singleton(contact))), throwable -> {});

    }

    @Override
    public void close() {
        observable.dispose();
    }
}

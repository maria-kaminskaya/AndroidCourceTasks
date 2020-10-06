package com.kmnvxh222.task6.db;

import com.kmnvxh222.task6.Contact;

import java.util.List;

public interface DBInterface {

    void addContact(Contact contact, DBListener<Contact> listener);

    void deleteContact(String id, DBListener<String> listener);

    void getAllContacts(DBListener<List<Contact>> listener);

    void updateContact(Contact contact, DBListener<Contact> listener);

    void getContactByID(String id, DBListener<List<Contact>> listener);

    void close();

}

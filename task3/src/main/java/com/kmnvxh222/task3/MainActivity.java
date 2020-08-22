package com.kmnvxh222.task3;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static List<Contacts> contacts = new ArrayList<>();
    private String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private FloatingActionButton button;
    private ContactsRecyclerAdapter adapter;
    private TextView itemsNull;
    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        itemsNull = findViewById(R.id.itemsNull);
        searchView = findViewById(R.id.searchView);
        searchListener();

        adapter = new ContactsRecyclerAdapter(contacts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        adapter.setOnItemClickListener(new ContactsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                intent.putExtra("EDIT_CONTACT", contacts.get(position));
                intent.putExtra("ID", contacts.get(position).getId());
                startActivityForResult(intent, 1000);
            }
        });

        button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, 2000);
            }
        });
        itemsNull.setText(R.string.no_contacts);
    }

    //onActivityResult
    private void getData(@Nullable Intent data) {
        Contacts new_contact = (Contacts) data.getSerializableExtra("NEW_CONTACT");
        if(new_contact != null) {
            contacts.add(new_contact);
            adapter.notifyDataSetChanged();
        }
    }

    //onActivityResult
    private void editData(@Nullable Intent data) {
        Contacts editContact = (Contacts) data.getSerializableExtra("EDITED_CONTACT");
        String id = (String) data.getSerializableExtra("ID");

        if(editContact != null && id != null) {
            for(int i = 0; i < contacts.size(); i++) {
                if(contacts.get(i).getId().equals(id)) {
                    contacts.set(i, editContact);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void searchListener() {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });
    }

    void search(String text) {
        List<Contacts> temp = new ArrayList<>();
        for(Contacts c : contacts) {
            if(c.getName().contains(text)) {
                temp.add(c);
            }
        }
        adapter.updateList(temp);
    }

    //onActivityResult
    private void removeContact(@Nullable Intent data) {
        Integer position = (Integer) data.getSerializableExtra("REMOVE_CONTACT");
        if(position != null) {
            contacts.remove((int) position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {
            if(requestCode == 1000) {
                removeContact(data);
                editData(data);
            }else if(requestCode == 2000) {
                getData(data);
                itemsNull.setText("");
            }
        }

    }
}


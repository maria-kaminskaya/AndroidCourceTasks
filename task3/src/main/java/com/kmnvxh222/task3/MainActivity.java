package com.kmnvxh222.task3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<Contacts> contacts = new ArrayList<>();
    private String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private FloatingActionButton button;
    private ContactsRecyclerAdapter adapter;
    private TextView itemsNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        itemsNull = findViewById(R.id.itemsNull);

        Log.d(TAG,"onCreate: Contacts" + contacts);

        adapter = new ContactsRecyclerAdapter(contacts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager (this, RecyclerView.VERTICAL, false));

        adapter.setOnItemClickListener(new ContactsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                intent.putExtra("EDIT_CONTACT",contacts.get(position));
                startActivity(intent);
            }
        });

        button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });
        getData();
        if(contacts.isEmpty()){
            itemsNull.setText(R.string.no_contacts);
        }
    }

    private void getData(){
        Contacts new_contact = (Contacts)getIntent().getSerializableExtra("NEW_CONTACT");
        if(new_contact!=null){
            contacts.add(new_contact);
            adapter.notifyDataSetChanged();
        }
        for(int i = 0; i<contacts.size();i++){
            Log.d(TAG,"getData: Contacts " + contacts.get(i));
        }
    }

}

// и редактирования
//Сделать активити с редактированием
//Завести поиск
//Сделать ту штуку на сотку

//  По второй домашке
//Добавить стили
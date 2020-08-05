package com.kmnvxh222.task3;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditContactActivity extends AppCompatActivity {

    private String TAG = "EditContactActivity";

    private int position;
    private String typeInfo;
    private EditText editTextName;
    private EditText editTextInfo;
    private Contacts edit_contact;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_edit_contact);

        editTextName = findViewById(R.id.editTextName_e);
        editTextInfo = findViewById(R.id.editTextInfo_e);

        toolbar = findViewById(R.id.toolbar_e);

        getData();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditData(edit_contact);
            }
        });

    }

    private void getData(){
        edit_contact = (Contacts)getIntent().getSerializableExtra("EDIT_CONTACT");
        position = (int)getIntent().getSerializableExtra("POSITION");
       if(edit_contact != null){
           editTextName.setText(edit_contact.getName());
           if(edit_contact.getPhone()==null){
               editTextInfo.setText(edit_contact.getEmail());
               typeInfo = "email";
           }else if(edit_contact.getEmail()==null){
               editTextInfo.setText(edit_contact.getPhone());
               typeInfo = "phone";
           }
           editData(edit_contact, typeInfo);
       }

    }

    private void editData(final Contacts edit_contact, final String typeInfo){

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "editData: LISTENER Name " + s);
                edit_contact.setName(s.toString());
            }
        });

        editTextInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(typeInfo.equals("phone")){
                    Log.d(TAG, "getData: typeInfo " + typeInfo);
                    edit_contact.setPhone(s.toString());

                }else if(typeInfo.equals("email")){
                    Log.d(TAG, "getData: typeInfo " + typeInfo);
                    edit_contact.setEmail(s.toString());

                }
            }
        });

        Log.d(TAG, "editData: Name " + edit_contact.getName());
        Log.d(TAG, "editData: Phone " + edit_contact.getPhone());
        Log.d(TAG, "editData: Email " + edit_contact.getEmail());

    }

    private void saveEditData(Contacts edit_contact){
        if(edit_contact!=null){
            Log.d(TAG, "saveContact " + edit_contact.getName()+" "+edit_contact.getEmail()+" "+ edit_contact.getPhone());
            Intent intent = new Intent(EditContactActivity.this, MainActivity.class);
            intent.putExtra("EDITED_CONTACT", (Serializable) edit_contact);
            intent.putExtra("POSITION", position);
            startActivity(intent);
        }
    }
}

//Удаление данных (будем нулевой объект отправлять в маин)
//Тулбар завести

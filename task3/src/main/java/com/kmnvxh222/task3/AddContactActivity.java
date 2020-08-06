package com.kmnvxh222.task3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.io.Serializable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    
    private String TAG = "AddContactActivity";

    private String typeInfo;
    private RadioGroup radioGroup;
    private RadioButton  radioButtonPhone;
    private RadioButton radioButtonEmail;
    private EditText editTextName;
    private EditText editTextInfo;
    private Contacts contact;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        radioGroup = findViewById(R.id.radioGroup);
        radioButtonPhone = findViewById(R.id.radioButtonPhone);
        radioButtonEmail = findViewById(R.id.radioButtonEmail);
        radioButtonPhone.setOnClickListener(radioButtonClickListener);
        radioButtonEmail.setOnClickListener(radioButtonClickListener);
        editTextName = findViewById(R.id.editTextName);
        editTextInfo = findViewById(R.id.editTextInfo);
        toolbar = findViewById(R.id.toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact(contact);
            }
        });

    }

        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = (RadioButton) v;
                switch(radioButton.getId()){
                    case R.id.radioButtonPhone: typeInfo = "phone";
                        break;
                    case R.id.radioButtonEmail: typeInfo = "email";
                        break;
                    default: break;
                }
                getData(typeInfo);
                Log.d(TAG, "radioButtonClickListener: typeInfo " + typeInfo);
            }
        };

    private void getData(String typeInfo){

        String name = editTextName.getText().toString();
        String info = editTextInfo.getText().toString();
        String phone = null;
        String email = null;

        if(typeInfo.equals("phone")){
            phone = info;
            Log.d(TAG, "getData: typeInfo " + typeInfo);

        }else if(typeInfo.equals("email")){
            email = info;
            Log.d(TAG, "getData: typeInfo " + typeInfo);

        }
        Log.d(TAG, "getData: Name " + name);
        Log.d(TAG, "getData: Phone " + phone);
        Log.d(TAG, "getData: Email " + email);

        contact = new Contacts(name,email,phone);
    }

    //Повесить его в тулбар на галочку
    private void saveContact(Contacts contact){
        if(contact!=null){
            Log.d(TAG, "saveContact " + contact.getName()+" "+contact.getEmail()+" "+ contact.getPhone());
            Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
            intent.putExtra("NEW_CONTACT", (Serializable) contact);
            startActivity(intent);
        }
    }
}

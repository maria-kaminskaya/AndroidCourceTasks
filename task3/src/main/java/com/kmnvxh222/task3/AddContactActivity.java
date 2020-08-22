package com.kmnvxh222.task3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddContactActivity extends AppCompatActivity {

    private String TAG = "AddContactActivity";

    private String typeInfo;
    private RadioButton radioButtonPhone;
    private RadioButton radioButtonEmail;
    private EditText editTextName;
    private EditText editTextInfo;
    private Contacts contact;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

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

    private View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton radioButton = (RadioButton) v;
            switch(radioButton.getId()) {
                case R.id.radioButtonPhone:
                    typeInfo = enumTypeInfo.phone.toString();
                    break;
                case R.id.radioButtonEmail:
                    typeInfo = enumTypeInfo.email.toString();
                    break;
                default:
                    break;
            }
            getData(typeInfo);
        }
    };

    private void getData(String typeInfo) {

        String name = editTextName.getText().toString();
        String info = editTextInfo.getText().toString();
        String id = "0";

        String symbols = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder randString = new StringBuilder();
        int count = 5;
        for(int i = 0; i < count; i++) {
            id = randString.append(symbols.charAt((int) (Math.random() * symbols.length()))).toString();
        }

        contact = new Contacts(id, name, typeInfo, info);
    }

    private void saveContact(Contacts contact) {
        if(contact != null) {
            Intent intent = new Intent();
            intent.putExtra("NEW_CONTACT", contact);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}

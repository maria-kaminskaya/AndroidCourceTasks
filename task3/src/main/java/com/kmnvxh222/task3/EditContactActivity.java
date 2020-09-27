package com.kmnvxh222.task3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditContactActivity extends AppCompatActivity {

    private String TAG = "EditContactActivity";

    private String id;
    private EditText editTextName;
    private EditText editTextInfo;
    private Contacts editСontact;
    private Button buttonRemove;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        editTextName = findViewById(R.id.editTextName_e);
        editTextInfo = findViewById(R.id.editTextInfo_e);
        buttonRemove = findViewById(R.id.removeButton);
        toolbar = findViewById(R.id.toolbar_e);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditData(editСontact);
            }
        });

        getData();
        removeData();

    }

    private void getData() {
        editСontact = (Contacts) getIntent().getSerializableExtra("EDIT_CONTACT");
        id = (String) getIntent().getSerializableExtra("ID");
        if(editСontact != null) {
            editTextName.setText(editСontact.getName());
            editTextInfo.setText(editСontact.getInfo());
        }
        editData(editСontact);
    }


    private void editData(final Contacts edit_contact) {

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
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
                edit_contact.setInfo(s.toString());
            }
        });
    }

    private void saveEditData(Contacts editContact) {
        if(editContact != null) {
            Intent intent = new Intent(EditContactActivity.this, MainActivity.class);
            intent.putExtra("EDITED_CONTACT", editContact);
            intent.putExtra("ID", editContact.getId());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }


    private void removeData() {
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = (String) getIntent().getSerializableExtra("ID");
                Intent intent = new Intent(EditContactActivity.this, MainActivity.class);
                intent.putExtra("REMOVE_CONTACT", id);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}


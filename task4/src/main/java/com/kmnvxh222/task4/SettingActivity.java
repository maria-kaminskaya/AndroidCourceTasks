package com.kmnvxh222.task4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void saveSetting(Boolean setting){
        SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putBoolean("MessageSetting",setting);
        edit.apply();


    }
}

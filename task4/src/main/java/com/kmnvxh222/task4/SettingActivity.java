package com.kmnvxh222.task4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import static com.kmnvxh222.task4.Const.PREF_KEY;
import static com.kmnvxh222.task4.Const.SETTING_FILE_NAME;

public class SettingActivity extends AppCompatActivity {

    private CheckBox checkboxMessage;
    private boolean setting;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        checkboxMessage = findViewById(R.id.checkboxMessage);
        checkboxMessage.setOnCheckedChangeListener(checkBoxOnCheckedChangeListener);

        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSetting();
    }

    private CompoundButton.OnCheckedChangeListener checkBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            saveSetting(isChecked);
        }
    };

    private void saveSetting(Boolean setting) {
        SharedPreferences shared = getSharedPreferences(SETTING_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.clear();
        edit.putBoolean(PREF_KEY, setting);
        edit.apply();
    }

    private void getSetting() {
        SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
        setting = shared.getBoolean(PREF_KEY, true);
        checkboxMessage.setChecked(setting);
    }
}

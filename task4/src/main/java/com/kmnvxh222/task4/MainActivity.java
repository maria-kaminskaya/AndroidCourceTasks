package com.kmnvxh222.task4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import static com.kmnvxh222.task4.Const.PREF_KEY;

public class MainActivity extends AppCompatActivity {

    private Button buttonSetting;
    private CustomView customView;
    private ConstraintLayout root;

    private boolean messageSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.constraintLayoutRoot);

        buttonSetting = findViewById(R.id.buttonSetting);
        buttonSetting.setOnClickListener(settingOnClickListener);

        customView = findViewById(R.id.customView);
        customView.setTouchActionListener(customViewTouchActionListener);

    }

    private CustomView.TouchActionListener customViewTouchActionListener = new CustomView.TouchActionListener() {
        @Override
        public void onTouchDown(int x, int y) {
            displayCoordinates(x, y, getSetting());
        }
    };

    private View.OnClickListener settingOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
    };

    private Boolean getSetting() {
        SharedPreferences shared = getSharedPreferences("SettingActivity", Context.MODE_PRIVATE);
        messageSetting = shared.getBoolean(PREF_KEY, true);
        return messageSetting;
    }

    private void displayCoordinates(int x, int y, boolean messageSetting) {
        if(messageSetting) {
            Snackbar.make(root, "X: " + x + " Y: " + y, Snackbar.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "X: " + x + " Y: " + y, Toast.LENGTH_SHORT).show();
        }
    }
}
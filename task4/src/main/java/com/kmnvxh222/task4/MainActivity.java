package com.kmnvxh222.task4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((CustomView) findViewById(R.id.customView)).setTouchActionListener(new CustomView.TouchActionListener() {
            @Override
            public void onTouchDown(int x, int y) {
                Toast.makeText(MainActivity.this, "X: " + x + " Y: " + y, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
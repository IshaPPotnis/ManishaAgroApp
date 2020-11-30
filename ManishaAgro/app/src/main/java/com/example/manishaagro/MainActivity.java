package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ConnectionDetector connectionDetector;
    DBHelper dbHelpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelpers = new DBHelper(this);
        dbHelpers.addData(1011, "offline", "offline");
        dbHelpers.addData(1012, "offline2", "offline2");
        Toast.makeText(MainActivity.this, "Got Data Offline", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);
    }


}
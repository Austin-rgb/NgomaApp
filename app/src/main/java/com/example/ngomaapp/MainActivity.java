package com.example.ngomaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    public void start(View v) {
        if (getSharedPreferences("credentials",0).getBoolean("serverNotSet",true))setup();
        else {
            Intent intent=new Intent(this,ClassesTableActivity.class);
            startActivity(intent);
        }
    }
    public void login(View v) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    void setup() {
        Intent intent=new Intent(this,SetupActivity.class);
        startActivity(intent);
    }
}


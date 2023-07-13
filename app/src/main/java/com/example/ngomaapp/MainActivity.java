package com.example.ngomaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    public void start(View v) {
        Intent intent=new Intent(this,ClassesTableActivity.class);
        startActivity(intent);
    }
    public void login(View v) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}


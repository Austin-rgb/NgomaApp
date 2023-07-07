package com.example.ngomaapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
        //intent.putExtra("table","classes");
        startActivity(intent);
       /* if (getSharedPreferences("data", 0).getBoolean("first_run", true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Usage");
            builder.setMessage("You are using a test version of Ngoma App which runs on test data");
            builder.setPositiveButton("Inflate test data", (di, id) -> {
                MainActivity.this.inflate_test_data();
                MainActivity.this._start();
            });
            builder.setNegativeButton("Cancel", (di, id) -> MainActivity.this.finish());
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            _start();
        }*/
    }

    void inflate_test_data() {
        SharedPrefTestData.inflate(this);
        SharedPreferences.Editor spe = getSharedPreferences("data", 0).edit();
        spe.putBoolean("first_run", false);
        spe.apply();

    }

    void _start() {
        Intent intent = new Intent(this, ClassesTableActivity.class);
        intent.putExtra("table", "classes");
        startActivity(intent);
    }
}


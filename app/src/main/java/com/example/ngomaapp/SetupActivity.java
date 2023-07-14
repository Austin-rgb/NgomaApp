package com.example.ngomaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R
                .layout
                .setup);
    }

    public void set(View v) {
        EditText editText = findViewById(R
                .id
                .ipAddress);
        String address = editText
                .getText()
                .toString();
        String url = "http://"+address;
        new InternetDaemon()
                .setChangeListener(new ChangeListener() {
                    @Override
                    public void onSuccess(String change) {
                        getSharedPreferences("credentials", 0)
                                .edit()
                                .putString("serverAddress", url)
                                .putBoolean("serverNotSet", false)
                                .apply();
                        finish();
                    }

                    @Override
                    public void onFailure(String change) {
                        new AlertDialog.Builder(SetupActivity.this)
                                .setTitle("Connection problem")
                                .setMessage("Could not effectively connect to " + url)
                                .create()
                                .show();
                    }
                })
                .execute(url);
    }
}
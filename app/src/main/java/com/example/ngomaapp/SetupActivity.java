package com.example.ngomaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SetupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R
                .layout
                .setup);
    }

    public void set(View v) {
        EditText editText = findViewById(R.id.ipAddress);
        String address = editText.getText().toString();
        String serverAddress = "http://"+address;
        String setupUrl=serverAddress+"/setup.php";
        new InternetDaemon()
                .setChangeListener((result, error) -> {
                    if (error == null) {
                        {
                            getSharedPreferences("credentials", 0)
                                    .edit()
                                    .putString("serverAddress", serverAddress)
                                    .apply();
                            new InternetDaemon()
                                    .setChangeListener((result1, error1) -> {
                                        if (error1 == null) {
                                            {

                                                String username;
                                                String password;
                                                String database;
                                                try {
                                                    JSONObject setup = new JSONObject(result1);
                                                    username = setup.getString("username");
                                                    password = setup.getString("password");
                                                    database = setup.getString("database");

                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                getSharedPreferences("credentials", 0)
                                                        .edit()
                                                        .putString("username", username)
                                                        .putString("password", password)
                                                        .putString("database", database)
                                                        .putBoolean("serverNotSet", false)
                                                        .apply();
                                                finish();
                                            }
                                        } else {
                                            new AlertDialog.Builder(SetupActivity.this)
                                                    .setTitle("Connection problem")
                                                    .setMessage(error1.getMessage())
                                                    .create()
                                                    .show();
                                        }
                                    })
                                    .execute(setupUrl);
                        }
                    } else {
                        new AlertDialog.Builder(SetupActivity.this)
                                .setTitle("Connection problem")
                                .setMessage(error.getMessage())
                                .create()
                                .show();
                    }
                })

                .execute(serverAddress);
    }
}
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
                .setChangeListener(new ChangeListener() {
                    @Override
                    public void onSuccess(String change) {
                        String username;
                        String password;
                        String database;
                        try {
                            JSONObject setup=new JSONObject(change);
                            username=setup.getString("username");
                            password=setup.getString("password");
                            database=setup.getString("database");

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        getSharedPreferences("credentials", 0)
                                .edit()
                                .putString("serverAddress", serverAddress)
                                .putString("username",username)
                                .putString("password",password)
                                .putString("database",database)
                                .putBoolean("serverNotSet", false)
                                .apply();
                        finish();
                    }
                    @Override
                    public void onFailure(String change) {
                        new AlertDialog.Builder(SetupActivity.this)
                                .setTitle("Connection problem")
                                .setMessage("Could not effectively connect to " + serverAddress)
                                .create()
                                .show();
                    }
                })
                .execute(setupUrl);
    }
}
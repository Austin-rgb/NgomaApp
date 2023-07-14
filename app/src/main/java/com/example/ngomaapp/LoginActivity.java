package com.example.ngomaapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    String server=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        server=getSharedPreferences("credentials",0).getString("serverAddress","127.0.0.1");
        setContentView(R.layout.login);
    }

    public void login(View view) {
        EditText emailInput = (EditText) findViewById(R.id.email);
        EditText passwordInput = (EditText) findViewById(R.id.password);
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String post = null;
        try {
             post = URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8")
                    + "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("Login",e.getMessage());
        }
        String login="http";
        InternetDaemon internetDaemon = new InternetDaemon();
        internetDaemon.setChangeListener(new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                if (change.equals("success")){
                    getSharedPreferences("credentials",0).edit()
                            .putString("email",email)
                            .putString("password",password)
                            .apply();
                }
                else {
                    passwordInput.setText("");
                    AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Authentication")
                            .setMessage("Could not match the provided credentials to a valid teacher account. Please try again with valid credentials or request your administrator for registration")
                            .create().show();
                }
            }

            @Override
            public void onFailure(String change) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Please connect to the internet.");
                builder.setTitle("Network Error");
                builder.setPositiveButton("Ok", (dialogInterface, i) -> finish());

                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }
        }).execute(login,post);
    }
}

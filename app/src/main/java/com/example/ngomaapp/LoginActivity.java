package com.example.ngomaapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        if (email=="" || password=="")return;
        String login=server+"/smi.php";
        InternetDaemon internetDaemon = new InternetDaemon();
        GData gData=new GData(this,login);

        gData.setChangeListener(new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                if (change.equals("")){
                    getSharedPreferences("credentials",0).edit()
                            .putString("email",email)
                            .putString("password",password)
                            .putBoolean("loggedIn",true)
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
        }).rawQuery("select * from teachers where email="+email+",password="+password);
        String post =Utils.encode(new String[]{"username","password"},new String[]{});
    }
}


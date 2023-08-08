package com.example.ngomaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PremiumSetup extends AppCompatActivity {
    String link;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premiumsetup);
        link = getSharedPreferences("credentials", 0).getString("serverAddress", "127.0.0.1");
    }

    public void submit(View view) {
        EditText editText = findViewById(R.id.username);
        String username = editText.getText().toString();
        EditText editText1 = findViewById(R.id.transactionId);
        String transaction = editText1.getText().toString();
        InternetDaemon internetDaemon = new InternetDaemon();
        internetDaemon.setChangeListener((result, error) -> {
            if (error == null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    getSharedPreferences("text", 0)
                            .edit()
                            .putString("username", jsonObject.getString("username"))
                            .putString("password", jsonObject.getString("password"))
                            .apply();
                    Intent intent = new Intent();
                    intent.putExtra("setup", true);
                    setResult(2, intent);
                    finish();
                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Error occurred")
                            .setMessage(result)
                            .create()
                            .show();
                }
            }
        }).execute(link + "/premium.php", Utils.encode(new String[]{"username", "transaction"}, new String[]{username, transaction}));
    }

}

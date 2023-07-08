package com.example.ngomaapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SetupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        SharedPreferences.Editor spe=getSharedPreferences("accessCommands",0).edit();
        new BackgroundWorker(this,new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                try {
                    JSONObject jsonObject=new JSONObject(change);
                    spe.putString("class",jsonObject.getString("class"));
                    spe.putString("subject",jsonObject.getString("subject"));
                    spe.putString("topic",jsonObject.getString("topic"));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                spe.apply();
            }
        });
    }
}

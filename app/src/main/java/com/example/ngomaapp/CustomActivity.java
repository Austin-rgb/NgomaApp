package com.example.ngomaapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class CustomActivity extends AppCompatActivity {
String table;
protected void onCreate(Bundle b){
  super.onCreate(b);
  table=getIntent().getStringExtra("table");
  setTitle(table);
}
}

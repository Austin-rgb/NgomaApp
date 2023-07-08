package com.example.ngomaapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;


public class CustomActivity extends AppCompatActivity {
String table;
protected void onCreate(Bundle b){
  super.onCreate(b);
  table=getIntent().getStringExtra("table");
  setTitle(table);
}
public boolean onCreateOptionsMenu(Menu menu){
  getMenuInflater().inflate(R.menu.menu,menu);
  return true;
}
public boolean onOptionsItemSelected(MenuItem menuItem){
  switch (menuItem.getTitle().toString()){
    case "About":
      //To do
  }
  return super.onOptionsItemSelected(menuItem);
}
}

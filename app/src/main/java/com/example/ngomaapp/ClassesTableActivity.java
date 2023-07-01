package com.example.ngomaapp;

import android.os.Bundle;

public class ClassesTableActivity extends CustomActivity {
@Override
protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  setContentView(new ClassesTableView(this,table));
}
}

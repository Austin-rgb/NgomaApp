package com.example.ngomaapp;

import android.os.Bundle;

public class ClassesTableActivity extends CustomActivity {
  ClassesTableView classesTableView;
@Override
protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);

  classesTableView = new ClassesTableView(this,table);
  setContentView(classesTableView);
}
}

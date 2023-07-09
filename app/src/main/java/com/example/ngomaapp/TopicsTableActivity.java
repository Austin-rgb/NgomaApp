package com.example.ngomaapp;

import android.os.Bundle;

public class TopicsTableActivity extends CustomActivity{
  String form;
  TopicsTableView topicsTableView;
@Override
protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  form = getIntent().getStringExtra("form");

  topicsTableView = new TopicsTableView(this,form,table);
  setContentView(topicsTableView);
}
}

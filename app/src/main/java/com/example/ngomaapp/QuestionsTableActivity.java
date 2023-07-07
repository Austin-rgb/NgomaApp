package com.example.ngomaapp;

import android.os.Bundle;

public class QuestionsTableActivity extends CustomActivity{
@Override
protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  String form=getIntent().getStringExtra("form");
  String subject =getIntent().getStringExtra("subject");
  String topic =getIntent().getStringExtra("topic");
  setContentView(new QuestionsTableView(this,form,subject,topic));
}
}

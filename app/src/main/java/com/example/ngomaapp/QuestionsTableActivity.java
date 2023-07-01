package com.example.ngomaapp;

import android.os.Bundle;

public class QuestionsTableActivity extends CustomActivity{
@Override
protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  setContentView(new QuestionsTableView(this,table));
}
}

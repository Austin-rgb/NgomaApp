package com.example.ngomaapp;

import android.os.Bundle;

public class TopicsTableActivity extends CustomActivity{
@Override
protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  setContentView(new TopicsTableView(this,table));
}

}

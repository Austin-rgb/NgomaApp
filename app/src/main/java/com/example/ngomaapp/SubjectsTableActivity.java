package com.example.ngomaapp;

import android.os.Bundle;
public class SubjectsTableActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SubjectsTableView(this,table));
    }
}

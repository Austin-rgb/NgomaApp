package com.example.ngomaapp;

import android.os.Bundle;
public class SubjectsTableActivity extends CustomActivity {
    SubjectsTableView subjectsTableView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjectsTableView = new SubjectsTableView(this,table);
        setContentView(subjectsTableView);
    }
}

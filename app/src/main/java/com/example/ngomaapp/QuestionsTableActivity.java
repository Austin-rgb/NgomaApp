package com.example.ngomaapp;

import android.os.Bundle;

public class QuestionsTableActivity extends CustomActivity {
    String form;
    String subject;
    String topic;
    QuestionsTableView questionsTableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        form = getIntent().getStringExtra("form");
        subject = getIntent().getStringExtra("subject");
        topic = getIntent().getStringExtra("table");

        questionsTableView = new QuestionsTableView(this, form, subject, topic);
        setContentView(questionsTableView);
    }
}

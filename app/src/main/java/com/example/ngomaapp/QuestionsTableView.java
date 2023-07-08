package com.example.ngomaapp;

import android.content.Context;

public class QuestionsTableView extends CustomView {
    public QuestionsTableView(Context ctx,String form, String subject,String topic) {
        super(ctx, topic);
        greetings.setText(getContext().getString(R.string.questions_on)+topic);
        new BackgroundWorker(getContext(),this).execute("question",form,subject,topic);

    }
}
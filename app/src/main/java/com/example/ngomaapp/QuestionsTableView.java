package com.example.ngomaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class QuestionsTableView extends CustomView {
    public QuestionsTableView(Context ctx,String form, String subject,String topic) {
        super(ctx, topic);
        greetings.setText(getContext().getString(R.string.questions_on)+topic);
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("data",0);
        String index=form+subject+topic;
        Log.d("Progress","Getting data for questions "+index);
        String data=sharedPreferences.getString("question"+index,"");
        if (data==""){
        new BackgroundWorker(getContext(),this).execute("question",form,subject,topic);
        }else {
            onSuccess(data);
        }
    }
}
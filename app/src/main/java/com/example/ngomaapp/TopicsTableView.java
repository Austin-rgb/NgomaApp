package com.example.ngomaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class TopicsTableView extends CustomView {
    public TopicsTableView(Context ctx,String form, String table) {
        super(ctx, table);
        greetings.setText(R.string.choose_a_topic);
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("data",0);
        String index=form+table;
        Log.d("Progress","Getting data for topics "+index);
        String data=sharedPreferences.getString("topic"+index,"");
        if (data==""){
        new BackgroundWorker(getContext(),this).execute("topic",form,table);
        } else {
            onSuccess(data);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView=(TextView)view;
                Intent intent=new Intent(getContext(), QuestionsTableActivity.class);
                intent.putExtra("form",form);
                intent.putExtra("subject",table);
                intent.putExtra("table",textView.getText().toString());
                getContext().startActivity(intent);
            }
        });
    }
}
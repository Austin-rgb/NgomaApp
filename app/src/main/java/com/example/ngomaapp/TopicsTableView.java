package com.example.ngomaapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class TopicsTableView extends CustomView {
    public TopicsTableView(Context ctx,String form, String table) {
        super(ctx, table);
        greetings.setText(R.string.choose_a_topic);
        new BackgroundWorker(getContext(),this).execute("topic",form,table);
        //setContent();
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
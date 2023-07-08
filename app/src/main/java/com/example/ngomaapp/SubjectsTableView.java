package com.example.ngomaapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class SubjectsTableView extends CustomView {
    public SubjectsTableView(Context ctx, String table) {
        super(ctx, table);
        greetings.setText("Choose a subject");
        new BackgroundWorker(getContext(),this).execute("subject",table);
        //setContent();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView=( TextView)view;
                Intent intent=new Intent(getContext(), TopicsTableActivity.class);
                intent.putExtra("form",table);
                intent.putExtra("table",textView.getText().toString());
                getContext().startActivity(intent);
            }
        });
    }
}
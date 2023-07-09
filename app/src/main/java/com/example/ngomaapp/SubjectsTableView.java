package com.example.ngomaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class SubjectsTableView extends CustomView {
    public SubjectsTableView(Context ctx, String table) {
        super(ctx, table);
        greetings.setText("Choose a subject");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", 0);
        Log.d("Progress","Getting data for questions "+table);
        String data=sharedPreferences.getString("subject"+table,"");
        if (data == "") {
            new BackgroundWorker(getContext(), this).execute("subject", table);
        } else {
            onSuccess(data);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                Intent intent = new Intent(getContext(), TopicsTableActivity.class);
                intent.putExtra("form", table);
                intent.putExtra("table", textView.getText().toString());
                getContext().startActivity(intent);
            }
        });
    }
}
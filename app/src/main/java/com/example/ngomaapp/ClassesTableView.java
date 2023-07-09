package com.example.ngomaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class ClassesTableView extends CustomView {

    public ClassesTableView(Context ctx, String table) {
        super(ctx, table);
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("data",0);
        String data=sharedPreferences.getString("class","");
        if (data==""){
        new BackgroundWorker(getContext(),this).execute("class");
        }else {
            onSuccess(data);
        }
        greetings.setText(R.string.choose_your_class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),SubjectsTableActivity.class);
                TextView tv=(TextView)view;
                intent.putExtra("table",tv.getText());
                getContext().startActivity(intent);
            }
        });
    }
}
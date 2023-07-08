package com.example.ngomaapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class ClassesTableView extends CustomView {

    public ClassesTableView(Context ctx, String table) {
        super(ctx, table);
        new BackgroundWorker(getContext(),this).execute("class");
        greetings.setText(R.string.choose_your_class);
        //setContent();
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
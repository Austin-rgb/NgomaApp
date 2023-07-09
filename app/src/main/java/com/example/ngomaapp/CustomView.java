package com.example.ngomaapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomView extends LinearLayout implements ChangeListener {
TextView greetings ;
String table,data;
    ArrayList<String> result=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listView;
public CustomView(Context ctx, String table){
  super (ctx);
  this.table=table;
    listView=new ListView(this.getContext());
    adapter=new ArrayAdapter<String>(this.getContext(), R.layout.listview,result);
    listView.setAdapter(adapter);
  setOrientation(LinearLayout.VERTICAL);
  greetings=new TextView(ctx);
  addView(greetings);
  addView(listView);
}
    public void onSuccess(String change) {
    data=change;
        String[] rows=change.split(",");
            for (int i=0;i<rows.length-1;i++){
                result.add(rows[i+1]);
            }
        adapter.notifyDataSetChanged();
    }
}

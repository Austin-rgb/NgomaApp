package com.example.ngomaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubjectsTableView extends CustomView {
    public SubjectsTableView(Context ctx, String table) {
        super(ctx, table);
        greetings.setText("Choose a subject");
        new BackgroundWorker(getContext(),this).execute(ctx.getSharedPreferences("setup",0).getString("subjects","")+"where class="+table+";");
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
        //setContent();
        //setNavigation();
    }
    void setContent(){
  LinearLayout linearLayout=new LinearLayout(getContext());
  linearLayout.setOrientation(LinearLayout.VERTICAL);
  Integer counter=new Integer(1);
  SharedPreferences sp=getContext().getSharedPreferences(table,0);
  String next="";
  while(true){
    next=sp.getString(counter.toString(),"");
    if(next=="")break;
    Button nextButton=new Button(getContext());
    nextButton.setText(next);
    nextButton.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v){
      Button button=(Button)v;
        Intent intent=new Intent(getContext(),TopicsTableActivity.class);
        intent.putExtra("table",table+"%"+button.getText().toString());
        getContext().startActivity(intent);
      }
    });
    linearLayout.addView(nextButton);
    counter+=1;
}
content.addView(linearLayout);
}

}
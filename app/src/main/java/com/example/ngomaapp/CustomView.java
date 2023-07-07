package com.example.ngomaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class CustomView extends LinearLayout implements ChangeListener {
TextView greetings ;
ScrollView content;
LinearLayout navigation;
String table,table_nickname;
    ArrayList<String> result=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listView;
public CustomView(Context ctx, String table){
  super (ctx);
  this.table=table;
    listView=new ListView(this.getContext());
    adapter=new ArrayAdapter<String>(this.getContext(), R.layout.listview,result);
    listView.setAdapter(adapter);
  //String[] nick=table.split("%");
  //table_nickname=nick[nick.length-1];
  setOrientation(LinearLayout.VERTICAL);
  greetings=new TextView(ctx);
  addView(greetings);
  addView(listView);
  //content=new ScrollView(ctx);
  //addView(content);
  //navigation=new LinearLayout(ctx);
  //addView(navigation);
}


public void setNavigation(){
  if(table!="classes"){
    Button prev_button=new Button(getContext());
Button next_button=new Button(getContext());
String[] location=locate();
if(location==null)return ;
if(location[0]!=""){
  prev_button.setText(location[0]);
  navigation.addView(prev_button);
  }
if(location[1]!=""){
  next_button.setText(location[1]);
  navigation.addView(next_button);
  
}
}
}
String[] locate(){
  String parent=get_parent(),
  previous="",
  next="";
  if(parent=="")return null;
  SharedPreferences sp=getContext().getSharedPreferences(parent,0);
  Integer counter=new Integer(1);
  while(true){
    if(sp.getString(counter.toString(),"")==table_nickname){
     counter-=1; previous=sp.getString(counter.toString(),"");
     counter+=2;
      next=sp.getString(counter.toString(),"");
      break ;
    }
    if(counter>10){
      AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
builder.setTitle("Error");
builder.setMessage("No match found for "+table);
AlertDialog dialog=builder.create();
dialog.show();
break ;
    }
    counter+=1;
    }
   String[] result= {previous,next};
   return result;
  }
  String get_parent(){
    String [] tree=table.split("%");
    if(tree.length>=2)return tree[tree.length-2];
    else return "";
  }
    public void onSuccess(String change) {
        try {
            JSONArray jsonArray=new JSONArray(change);
            for (int i=0;i<jsonArray.length()-1;i++){
                result.add(jsonArray.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            result.add(e.getMessage());
            result.add(change);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String change) {

    }
}

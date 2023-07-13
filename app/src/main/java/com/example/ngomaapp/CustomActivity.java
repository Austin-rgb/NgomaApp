package com.example.ngomaapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class CustomActivity extends AppCompatActivity implements ChangeListener {
final String mainUrl="http://127.0.0.1:8080/getdata.php";
InternetDaemon internetDaemon;
BottomNavigationView bottomNavigationView;
ListView listView;
ArrayList <String>arrayList;
ArrayAdapter<String> arrayAdapter;
FloatingActionButton floatingActionButton;
protected void onCreate(Bundle b){
  super.onCreate(b);
  setContentView(R.layout.table);
  internetDaemon=new InternetDaemon();
  bottomNavigationView=findViewById(R.id.navigation);
  arrayList=new ArrayList<>();
  arrayAdapter=new ArrayAdapter<>(this,R.layout.listview,arrayList);
  listView=findViewById(R.id.listview);
  floatingActionButton=findViewById(R.id.actionButton);
  internetDaemon.setChangeListener(this);
}
public boolean onCreateOptionsMenu(Menu menu){
  getMenuInflater().inflate(R.menu.menu,menu);
  return true;
}
public boolean onOptionsItemSelected(MenuItem menuItem){
  switch (menuItem.getTitle().toString()){
    case "About":
      //To do
  }
  return super.onOptionsItemSelected(menuItem);
}

  @Override
  public void onSuccess(String change) {
    String [] dataAndVersion=change.split(";");
    String data=dataAndVersion[0];
    String version=dataAndVersion[1];
    String[] rows=data.split(",");
    for (int i=0;i<rows.length-1;i++){
      arrayList.add(rows[i+1]);
    }
    arrayAdapter.notifyDataSetChanged();
    //Save for offline
    getSharedPreferences("data",0)
            .edit()
            .putString(internetDaemon.forTable(),change)
            .apply();
  }

  @Override
  public void onFailure(String change) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Please connect to the internet.")
            .setTitle("Network Error")
            .setPositiveButton("Ok", (dialogInterface, i) -> {
              finish();
            });

    AlertDialog dialog = builder.create();
    dialog.setCancelable(false);
    dialog.show();
  }
}

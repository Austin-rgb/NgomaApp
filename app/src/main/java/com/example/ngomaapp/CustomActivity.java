package com.example.ngomaapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomActivity extends AppCompatActivity implements Callback {
    final String testUrl = "/smi.php";
    TextView status;
    ProgressBar progressBar;
    AlertDialog.Builder builder;
    String table = null;
    JSONArray jsonData = null;
    BottomNavigationView bottomNavigationView;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    FloatingActionButton floatingActionButton;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.table);
        status = findViewById(R.id.status);
        progressBar = findViewById(R.id.progressBar);
        bottomNavigationView = findViewById(R.id.navigation);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.listview, arrayList);
        listView = findViewById(R.id.listview);
        listView.setAdapter(arrayAdapter);
        floatingActionButton = findViewById(R.id.actionButton);
        builder = new AlertDialog.Builder(this);
    }
public boolean onCreateOptionsMenu(Menu menu){
  getMenuInflater().inflate(R.menu.menu,menu);
  return true;
}
public boolean onOptionsItemSelected(MenuItem menuItem){
  return super.onOptionsItemSelected(menuItem);
}

  public void onSuccess(String change) {
      status.setText(R.string.ready);
      progressBar.setIndeterminate(false);
      Log.i("CustomActivity", "got remote data " + change);
      try {
          jsonData = new JSONArray(change);
          for (int i = 0; i < jsonData.length(); i++) {
              JSONObject jsonObject = jsonData.getJSONObject(i);
              arrayList.add(jsonObject.getString(table));
          }
      } catch (JSONException e) {
          throw new RuntimeException(e);
      }
      arrayAdapter.notifyDataSetChanged();
  }

    public void onFailure(NgomaException change) {
        status.setText(R.string.failed);
        progressBar.setIndeterminate(false);
        Utils.showError(this, change);
    }

    @Override
    public void callback(String result, NgomaException error) {
        if (result != null) onSuccess(result);
        else onFailure(error);
    }
}
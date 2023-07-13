package com.example.ngomaapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Objects;

public class QuestionsTableActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String form=getIntent().getStringExtra("class");
        final String subject=getIntent().getStringExtra("subject");
        final String topic=getIntent().getStringExtra("topic");
        //Check for availability of offline data
        SharedPreferences sharedPreferences = getSharedPreferences("data", 0);
        String data = sharedPreferences.getString("class", "");
        if (data.equals("")) {
            //Get remote data
            internetDaemon.setChangeListener(this);
            internetDaemon.execute(mainUrl, URLEncoder.encode("request") + "=" + URLEncoder.encode("class"));
        } else {
            //Render offline data
            String[] datasetAndVersion = data.split(";");
            String rows = datasetAndVersion[0];
            String version = datasetAndVersion[1];
            onSuccess(rows);
            //Confirm version of the offline data
            internetDaemon.setChangeListener(new ChangeListener() {
                @Override
                public void onSuccess(String change) {
                    if (!Objects.equals(change, version)) {
                        internetDaemon.setChangeListener(new ChangeListener() {
                            @Override
                            public void onSuccess(String change) {
                                //Render new data
                                String newData = change.split(";")[0];
                                String[] newRows = newData.split(",");
                                arrayList.clear();
                                arrayList.addAll(Arrays.asList(newRows));
                                arrayAdapter.notifyDataSetChanged();
                                //Backup data
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("class", change);
                                editor.apply();
                            }

                            @Override
                            public void onFailure(String change) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(String change) {

                }
            });
            internetDaemon.execute(mainUrl, URLEncoder.encode("request") + "=" + URLEncoder.encode("version"));
        }
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, SubjectsTableActivity.class);
            TextView tv = (TextView) view;
            intent.putExtra("table", tv.getText());
            startActivity(intent);
        });
        //if (sharedPreferences.getBoolean("logged in",false)) {
        floatingActionButton.setVisibility(View.VISIBLE);
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            TextView textView = (TextView) view;
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getTitle().toString()) {
                        case "Rename":
                            //To do
                        case "Delete":
                            AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsTableActivity.this);
                            builder.setTitle("DELETE");
                            builder.setMessage("Are you sure you want to delete " + textView.getText().toString());
                            builder.setPositiveButton("OK", (dialogInterface, i1) -> {

                            });
                            builder.setNegativeButton("CANCEL", (dialogInterface, i12) -> {
                                //To do
                            });
                            builder.create().show();
                    }
                    return true;
                }
            });
            popupMenu.show();
            return true;
        });
        // }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
    }
}

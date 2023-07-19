package com.example.ngomaapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

public class SubjectsTableActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Subjects");
        table = "subject";
        String form=getIntent().getStringExtra("class");
        GData gData=new GData(this,testUrl);
        gData.setChangeListener(this).rawQuery("select distinct subject from questions where class=\""+form+"\"");
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, TopicsTableActivity.class);
            TextView tv = (TextView) view;
            intent.putExtra("class", form).putExtra("subject",tv.getText().toString());
            startActivity(intent);
        });
        if (getSharedPreferences("credentials", 0).getBoolean("logged in", false)) {
            floatingActionButton.setVisibility(View.VISIBLE);
            listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
                TextView textView = (TextView) view;
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SubjectsTableActivity.this);
                    switch (menuItem.getTitle().toString()) {
                        case "Rename":
                            EditText newName = new EditText(SubjectsTableActivity.this);
                            newName.setText(textView.getText());
                            newName.selectAll();
                            builder.setTitle("RENAME")
                                    .setView(newName)
                                    .setPositiveButton("OK", (dialogInterface, i1) -> {
                                        gData.setChangeListener(new ChangeListener() {
                                            @Override
                                            public void onSuccess(String change) {

                                            }

                                            @Override
                                            public void onFailure(String change) {

                                            }
                                        }).update("questions","class=\""+newName.getText().toString()+"\"","class=\""+textView.getText().toString()+"\"");
                                    })
                                    .setNegativeButton("CANCEL", (dialogInterface, i12) -> {
                                        //To do
                                    })
                                    .create().show();
                        case "Delete":
                            builder.setTitle("DELETE")
                                    .setMessage("Are you sure you want to delete " + textView.getText().toString())
                                    .setPositiveButton("OK", (dialogInterface, i1) -> {
                                        gData.setChangeListener(new ChangeListener() {
                                            @Override
                                            public void onSuccess(String change) {
                                                AlertDialog.Builder builder1=new AlertDialog.Builder(SubjectsTableActivity.this);
                                                builder1.setTitle("Success")
                                                        .setMessage("Successfully deleted "+textView.getText().toString())
                                                        .create().show();
                                            }

                                            @Override
                                            public void onFailure(String change) {
//create user 'use***' identified by 'pas***'
                                            }
                                        }).delete("questions","class="+form+" subject="+textView.getText().toString());


                                    })
                                    .setNegativeButton("CANCEL", (dialogInterface, i12) -> {
                                        //To do
                                    })
                                    .create().show();
                    }
                    return true;
                });
                popupMenu.show();
                return true;
            });
            // }
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> false);
            bottomNavigationView.getMenu().removeItem(0);
        }
    }
}

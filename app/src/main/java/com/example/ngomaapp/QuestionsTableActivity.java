package com.example.ngomaapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

public class QuestionsTableActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Classes");
        table = "class";
        String form=getIntent().getStringExtra("class");
        String subject=getIntent().getStringExtra("subject");
        String topic=getIntent().getStringExtra("topic");
        GData gData=new GData(this,testUrl);
        gData.setChangeListener(this).rawQuery("select question,question_id from questions where class=\""+form+"\" and subject=\""+subject+"\" and topic=\""+topic+"\"");
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, AnswerActivity.class);
            TextView tv = (TextView) view;
            intent.putExtra("class", tv.getText());
            startActivity(intent);
        });
        if (getSharedPreferences("credentials", 0).getBoolean("logged in", false)) {
            floatingActionButton.setVisibility(View.VISIBLE);
            listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
                TextView textView = (TextView) view;
                android.widget.PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(QuestionsTableActivity.this);
                    switch (menuItem.getTitle().toString()) {
                        case "Rename":
                            EditText newName = new EditText(QuestionsTableActivity.this);
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
                                        }).update("questions","class=\""+form+newName.getText().toString()+"\"","class=\""+textView.getText().toString()+"\"");
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
                                                android.app.AlertDialog.Builder builder1=new AlertDialog.Builder(QuestionsTableActivity.this);
                                                builder1.setTitle("Success")
                                                        .setMessage("Successfully deleted "+textView.getText().toString())
                                                        .create().show();
                                            }

                                            @Override
                                            public void onFailure(String change) {

                                            }
                                        }).delete("questions","class="+textView.getText().toString());


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

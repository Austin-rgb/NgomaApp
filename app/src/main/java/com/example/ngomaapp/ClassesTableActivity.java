package com.example.ngomaapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ClassesTableActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Classes");
        table = "class";
        //Check for availability of offline data

        GData gData = new GData(this, testUrl, "questions");
        status.setText(R.string.loading);
        progressBar.setIndeterminate(true);
        gData.rawQuery("select distinct class from questions", this);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, SubjectsTableActivity.class);
            TextView tv = (TextView) view;
            intent.putExtra("class", tv.getText());
            startActivity(intent);
        });
        if (getSharedPreferences("credentials", 0).getBoolean("logged in", false)) {
            floatingActionButton.setVisibility(View.VISIBLE);
            listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
                TextView textView = (TextView) view;
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClassesTableActivity.this);
                    switch (menuItem.getTitle().toString()) {
                        case "Rename":
                            EditText newName = new EditText(ClassesTableActivity.this);
                            newName.setText(textView.getText());
                            newName.selectAll();
                            builder.setTitle("RENAME")
                                    .setView(newName)
                                    .setPositiveButton("OK", (dialogInterface, i1) -> gData.update("questions", "class=\"" + newName.getText().toString() + "\"", "class=\"" + textView.getText().toString() + "\"", null))
                                    .setNegativeButton("CANCEL", (dialogInterface, i12) -> {
                                        //To do
                                    })
                                    .create().show();
                        case "Delete":
                            builder.setTitle("DELETE")
                                    .setMessage("Are you sure you want to delete " + textView.getText().toString())
                                    .setPositiveButton("OK", (dialogInterface, i1) -> gData.delete("questions", "class=" + textView.getText().toString(), null))
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

}
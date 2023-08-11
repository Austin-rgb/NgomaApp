package com.example.ngomaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

public class QuestionsTableActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Questions");
        table = "question";
        control.setContext(this);
        control.getQuestions(this);
        Button prev = findViewById(R.id.navPrev),
                next = findViewById(R.id.navNext);
        prev.setText(control.getPrevious());
        next.setText(control.getNext());
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, AnswerActivity.class);
            TextView tv = (TextView) view;
            control.chooseQuestion(tv.getText().toString());
            intent.putExtra("control", control);
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
                                       /* gData.update("questions", "class=\"" + form + newName.getText().toString() + "\"", "class=\"" + textView.getText().toString() + "\"",
                                                (result, error) -> {
                                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                                                    if (error == null) {
                                                        builder1.setTitle("Success")
                                                                .setMessage("Successfully updated " + textView.getText().toString())
                                                                .create()
                                                                .show();
                                                    } else {
                                                        builder1.setTitle("Success")
                                                                .setMessage(error.getMessage())
                                                                .create()
                                                                .show();
                                                    }
                                                });*/
                                    })
                                    .setNegativeButton("CANCEL", (dialogInterface, i12) -> {
                                        //To do
                                    })
                                    .create().show();
                        case "Delete":
                            builder.setTitle("DELETE")
                                    .setMessage("Are you sure you want to delete " + textView.getText().toString())
                                    .setPositiveButton("OK", (dialogInterface, i1) -> {
                                        //gData.delete("questions", "class=" + textView.getText().toString(),null);
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

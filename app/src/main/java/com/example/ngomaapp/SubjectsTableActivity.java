package com.example.ngomaapp;

import android.os.Bundle;

public class SubjectsTableActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Subjects");
        table = "subject";
        control.setContext(this);
        String form = getIntent().getStringExtra("class");
        control.getSubjects(this);
       /* Button prev = findViewById(R.id.navPrev),
                next = findViewById(R.id.navNext);
        prev.setText(control.getPrevious("class"));
        prev.setOnClickListener(view -> {
            control.moveToPrevious();
            Intent intent=new Intent(this, SubjectsTableActivity.class);
            intent.putExtra("control",control);
            startActivity(intent);
        });
        next.setText(control.getNext("class"));
        next.setOnClickListener(view -> {
            control.moveToNext();
            Intent intent=new Intent(this, SubjectsTableActivity.class);
            intent.putExtra("control",control);
            startActivity(intent);
        });
        GData gData = new GData(this, testUrl, "questions", false);
        //gData.rawQuery("select distinct subject from questions where class=\'" + form + "\'", this);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, TopicsTableActivity.class);
            TextView tv = (TextView) view;
            control.chooseSubject(tv.getText().toString());
            intent.putExtra("control", control);
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
                                    .setPositiveButton("OK",
                                            (dialogInterface, i1) -> {
                                                gData.update("questions", "class=\"" + newName.getText().toString() + "\"", "class=\"" + textView.getText().toString() + "\"",
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
                                                        });
                                            })
                                    .setNegativeButton("CANCEL", (dialogInterface, i12) -> {
                                        //To do
                                    })
                                    .create().show();
                        case "Delete":
                            builder.setTitle("DELETE")
                                    .setMessage("Are you sure you want to delete " + textView.getText().toString())
                                    .setPositiveButton("OK", (dialogInterface, i1) -> {
                                        gData.delete("questions", "class=" + form + " subject=" + textView.getText().toString(),
                                                (result, error) -> {
                                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                                                    if (error == null) {
                                                        builder1.setTitle("Success")
                                                                .setMessage("Successfully deleted " + textView.getText().toString())
                                                                .create()
                                                                .show();
                                                    } else {
                                                        builder1.setTitle("Success")
                                                                .setMessage(error.getMessage())
                                                                .create()
                                                                .show();
                                                    }
                                                });


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
        }*/
    }
}

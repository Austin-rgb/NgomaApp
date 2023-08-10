package com.example.ngomaapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

public class TopicsTableActivity extends CustomActivity{
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("Topics");
    table = "topic";
    control.setContext(this);
    control.getTopics(this);
    // gData.rawQuery("select distinct topic from questions where class=\'" + form + "\' and " + "subject=\'" + subject + "\'", this);
    listView.setOnItemClickListener((adapterView, view, i, l) -> {
        Intent intent = new Intent(this, QuestionsTableActivity.class);
        TextView tv = (TextView) view;
        control.chooseTopic(tv.getText().toString());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(TopicsTableActivity.this);
        switch (menuItem.getTitle().toString()) {
          case "Rename":
            EditText newName = new EditText(TopicsTableActivity.this);
              newName.setText(textView.getText());
              newName.selectAll();
              builder.setTitle("RENAME")
                      .setView(newName)
                      .setPositiveButton("OK", (dialogInterface, i1) -> {
                      })
                      .setNegativeButton("CANCEL", (dialogInterface, i12) -> {
                          //To do
                      })
                      .create().show();
              break;
            case "Delete":
                builder.setTitle("DELETE")
                        .setMessage("Are you sure you want to delete " + textView.getText().toString())
                        .setPositiveButton("OK", (dialogInterface, i1) -> {
                        /*gData.delete("questions", "class=" + form + " subject=" + textView.getText().toString(), (result, error) -> {
                            if (result != null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                                builder1.setTitle("Success")
                                        .setMessage("successfully deleted " + textView.getText().toString())
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                                builder1.setTitle("Failed")
                                        .setMessage("could not delete " + textView.getText().toString())
                                        .create()
                                        .show();
                            }
                        });*/
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

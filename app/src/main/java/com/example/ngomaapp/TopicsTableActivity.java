package com.example.ngomaapp;

import android.os.Bundle;

public class TopicsTableActivity extends CustomActivity{
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("Topics");
    table = "topic";
    control.setContext(this);
    control.getTopics(this);
    /*Button prev = findViewById(R.id.navPrev),
            next = findViewById(R.id.navNext);
    prev.setText(control.getPrevious("subject"));
    prev.setOnClickListener(view -> {
        control.moveToPrevious();
        Intent intent=new Intent(this, TopicsTableActivity.class);
        intent.putExtra("control",control);
        startActivity(intent);
    });
    next.setText(control.getNext("subject"));
    next.setOnClickListener(view -> {
        control.moveToNext();
        Intent intent=new Intent(this, TopicsTableActivity.class);
        intent.putExtra("control",control);
        startActivity(intent);
    });
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
              case "Rename" -> {
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
              }
              case "Delete" -> builder.setTitle("DELETE")
                      .setMessage("Are you sure you want to delete " + textView.getText().toString())
                      .setPositiveButton("OK", (dialogInterface, i1) -> {
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

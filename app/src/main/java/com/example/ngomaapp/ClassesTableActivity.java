package com.example.ngomaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ClassesTableActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSharedPreferences("testApp", 0).getBoolean("testData", true))
            Utils.setTestData(this, (result, error) -> {
                if (result == null) Utils.showError(this, error);
                else
                    getSharedPreferences("testApp", 0).edit().putBoolean("testData", false).apply();
            });
        setTitle("Classes");
        table = "class";
        control = new Control(this);
        status.setText(R.string.loading);
        progressBar.setIndeterminate(true);
        control.getClasses(this);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, SubjectsTableActivity.class);
            TextView tv = (TextView) view;
            // intent.putExtra("class", tv.getText());
            control.chooseClass(tv.getText().toString());
            intent.putExtra("control", control);
            startActivity(intent);
        });
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

package com.example.ngomaapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ClassesTableActivity extends CustomActivity implements Revise {
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
        setContentView(R.layout.table);
        ClassesFragment classesFragment = new ClassesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.classesFragment, classesFragment, null)
                .commit();
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

    @Override
    public Control getControl() {
        return control;
    }

    @Override
    public void setupSubjectNavigation(View view, Callback target) {
        setupNavigation(view, "subject", target);
    }

    @Override
    public void setupTopicNavigation(View view, Callback target) {
        setupNavigation(view, "topic", target);
    }

    void setupNavigation(View view, String type, Callback update) {
        LinearLayout navigation = view.findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);
        navigation.setOrientation(LinearLayout.HORIZONTAL);
        navigation.setBottom(view.getBottom());
        Button previous = view.findViewById(R.id.navPrevious);
        previous.setText(control.getPrevious("class"));
        if (!"End".equals(previous.getText().toString())) previous.setOnClickListener(view12 -> {
            control.chooseClass(control.getPrevious("class"));
            control.getSubjects(update);
        });
        Button next = view.findViewById(R.id.navNext);
        next.setText(control.getNext("class"));
        if (!"End".equals(next.getText().toString())) next.setOnClickListener(view12 -> {
            control.chooseClass(control.getNext("class"));
            control.getSubjects(update);
        });
    }

}

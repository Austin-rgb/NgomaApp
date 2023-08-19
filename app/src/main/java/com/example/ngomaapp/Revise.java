package com.example.ngomaapp;

import android.view.View;

public interface Revise {
    Control getControl();

    void setupSubjectNavigation(View view, Callback target);

    void setupTopicNavigation(View view, Callback target);
}

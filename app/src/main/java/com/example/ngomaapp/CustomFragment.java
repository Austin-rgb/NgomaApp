package com.example.ngomaapp;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public abstract class CustomFragment extends Fragment {
    Control control;

    void setupNavigation(View view, String type) {
        Button back = view.findViewById(R.id.navBack);
        back.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());
        LinearLayout navigation = view.findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);
        navigation.setOrientation(LinearLayout.HORIZONTAL);
        navigation.setBottom(view.getBottom());
        Button previous = view.findViewById(R.id.navPrevious);
        previous.setText(control.getPrevious(type));
        if (!"End".equals(previous.getText().toString())) previous.setOnClickListener(view12 -> {
            switch (type) {
                case "class" -> {
                    control.chooseClass(control.getPrevious(type));
                    control.getSubjects((result, error) -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.classesFragment, new SubjectsFragment(), null).addToBackStack(type).commit());
                }
                case "subject" -> {
                    control.chooseSubject(control.getPrevious(type));
                    control.getTopics((result, error) -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.classesFragment, new SubjectsFragment(), null).addToBackStack(type).commit());
                }
                case "topic" -> {
                    control.chooseTopic(control.getPrevious(type));
                    control.getQuestions((result, error) -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.classesFragment, new SubjectsFragment(), null).addToBackStack(type).commit());
                }
            }
        });
        Button next = view.findViewById(R.id.navNext);
        next.setText(control.getNext(type));
        if (!"End".equals(next.getText().toString())) next.setOnClickListener(view12 -> {
            switch (type) {
                case "class" -> {
                    control.chooseClass(control.getNext(type));
                    control.getSubjects((result, error) -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.classesFragment, new SubjectsFragment(), null).addToBackStack(type).commit());
                }
                case "subject" -> {
                    control.chooseSubject(control.getNext(type));
                    control.getTopics((result, error) -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.classesFragment, new TopicsFragment(), null).addToBackStack(type).commit());
                }
                case "topic" -> {
                    control.chooseTopic(control.getNext(type));
                    control.getQuestions((result, error) -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.classesFragment, new QuestionsFragment(), null).addToBackStack(type).commit());
                }
            }
        });
    }

}

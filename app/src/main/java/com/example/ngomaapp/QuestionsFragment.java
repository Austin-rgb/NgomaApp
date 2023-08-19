package com.example.ngomaapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class QuestionsFragment extends CustomFragment {

    public QuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        ListView listView = view.findViewById(R.id.listview);
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.listview, list);
        listView.setAdapter(adapter);
        Revise revise = (Revise) getActivity();
        Callback update = (result, error) -> {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.getJSONObject(i).getString("topic"));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
        assert revise != null;
        control = revise.getControl();
        control.getQuestions((result, error) -> {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.getJSONObject(i).getString("question"));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            Toast.makeText(getActivity(), "item selected", Toast.LENGTH_SHORT).show();
            TextView textView = ((TextView) view1);
            control.chooseQuestion(textView.getText().toString());
        });
        setupNavigation(view, "topic");
        return view;
    }
}
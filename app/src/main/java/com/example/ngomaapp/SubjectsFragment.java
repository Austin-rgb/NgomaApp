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

public class SubjectsFragment extends CustomFragment {

    public SubjectsFragment() {
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
        TextView title = view.findViewById(R.id.title);
        title.setText(R.string.choose_a_subject);
        Revise revise = (Revise) getActivity();
        assert revise != null;
        control = revise.getControl();
        Callback update =
                (result, error) -> {
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        if (!list.isEmpty())
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.classesFragment, new SubjectsFragment(), null).commit();
                        for (int i = 0; i < jsonArray.length(); i++)
                            list.add(jsonArray.getJSONObject(i).getString("subject"));
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                };
        control.getSubjects(update);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            TextView textView = ((TextView) view1);
            control.chooseSubject(textView.getText().toString());
            topicsView();
        });
        setupNavigation(view, "class");
        return view;
    }

    public void topicsView() {
        TopicsFragment classesFragment = new TopicsFragment();
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.classesFragment, classesFragment, null)
                .setReorderingAllowed(true)
                .addToBackStack("subject")
                .commit();
        Toast.makeText(getActivity(), "topics fragment added", Toast.LENGTH_SHORT).show();
    }
}
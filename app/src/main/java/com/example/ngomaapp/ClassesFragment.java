package com.example.ngomaapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ClassesFragment extends Fragment {
    public ClassesFragment() {
        // Required empty public constructor
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
        assert revise != null;
        Control control = revise.getControl();
        control.getClasses((result, error) -> {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.getJSONObject(i).getString("class"));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            Toast.makeText(getActivity(), "item selected", Toast.LENGTH_SHORT).show();
            TextView textView = ((TextView) view1);
            control.chooseClass(textView.getText().toString());
            subjectsView();
        });
        return view;
    }

    public void subjectsView() {
        SubjectsFragment classesFragment = new SubjectsFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.classesFragment, classesFragment, null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .setReorderingAllowed(true)
                .addToBackStack("class")
                .commit();
        Toast.makeText(getActivity(), "subject fragment added", Toast.LENGTH_SHORT).show();
    }
}
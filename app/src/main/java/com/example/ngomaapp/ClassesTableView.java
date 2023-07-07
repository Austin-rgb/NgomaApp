package com.example.ngomaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClassesTableView extends CustomView {

    public ClassesTableView(Context ctx, String table) {
        super(ctx, table);
        new BackgroundWorker(getContext(),this).execute(ctx.getSharedPreferences("setup",0).getString("classes","")+"SELECT CLASS FROM questions;");
        greetings.setText(R.string.choose_your_class);
        //setContent();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),SubjectsTableActivity.class);
                TextView tv=(TextView)view;
                intent.putExtra("class",tv.getText());
                getContext().startActivity(intent);
            }
        });
    }

    void setContent() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Integer counter = new Integer(1);
        SharedPreferences sp = getContext().getSharedPreferences(table, 0);
        String next = "";
        while (true) {
            next = sp.getString(counter.toString(), "");
            if (next == "") break;
            Button nextButton = new Button(getContext());
            nextButton.setText(next);
            nextButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button button = (Button) v;
                    try {
                        Intent intent = new Intent(getContext(), SubjectsTableActivity.class);
                        intent.putExtra("table", button.getText().toString());
                        getContext().startActivity(intent);
                    } catch (Exception e) {
                        String report = "";
                        report += e.getMessage() + "\n";
                        StackTraceElement[] ste = e.getStackTrace();
                        for (StackTraceElement se : ste) {
                            report += se.getClassName() + "." + se.getMethodName() + ":" + se.getLineNumber() + "\n";
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Error");
                        builder.setMessage(report);
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }
            });
            linearLayout.addView(nextButton);
            counter += 1;
        }
        content.addView(linearLayout);
    }

}
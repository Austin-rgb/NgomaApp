package com.example.ngomaapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("id");
        GData data = new GData(this, "/smi.php", "answers", false);
        LinearLayout linearLayout = new LinearLayout(this);
        Button button = new Button(this);
        button.setText(R.string.back);
        button.setOnClickListener(view -> finish());
        TextView textView = new TextView(this);
        WebView webView = new WebView(this);
        linearLayout.addView(button);
        linearLayout.addView(textView);
        linearLayout.addView(webView);
        setContentView(linearLayout);
        data.rawQuery("select text,link from answers where question_id=" + id, (result, error) -> {
            if (error == null) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result);
                    String text = jsonObject.getString("text");
                    String link = jsonObject.getString("link");
                    String data1 = "<iframe src=" + link + "></iframe>";
                    textView.setText(text);
                    webView.loadData(data1, "text/html", "utf-8");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Error occurred")
                        .setMessage(error.getMessage())
                        .create()
                        .show();
            }
        });

    }
}

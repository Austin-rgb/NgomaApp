package com.example.ngomaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class AnswerActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("id");
        GData data = new GData(this, "/smi.php", "answers", false);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Button button = new Button(this);
        button.setText(R.string.back);
        button.setOnClickListener(view -> finish());
        TextView textView = new TextView(this);
        textView.setPadding(10, 10, 10, 20);
        TextView webViewTitle = new TextView(this);
        webViewTitle.setText(R.string.webviewtitle);
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        linearLayout.addView(button);
        linearLayout.addView(textView);
        linearLayout.addView(webViewTitle);
        linearLayout.addView(webView);
        setContentView(linearLayout);
        data.rawQuery("select answer,link from answers where question_id=" + id, (result, error) -> {
            if (error == null) {

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONArray(result).getJSONObject(0);
                    String text = jsonObject.getString("answer");
                    String link = jsonObject.getString("link");
                    String data1 = "<iframe src=" + link + "></iframe>";
                    textView.setText(text);
                    webView.loadData(data1, "text/html", "utf-8");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                String errorMessage = error.getMessage();
                if (Objects.requireNonNull(errorMessage).contains("denied")) {
                    startActivityForResult(new Intent(this, PremiumSetup.class), 2);
                } else {
                    Utils.showError(this, error);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 2) {
            onRestart();
        }
    }
}

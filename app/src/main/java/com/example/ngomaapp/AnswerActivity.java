package com.example.ngomaapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;
public class AnswerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout=new LinearLayout(this);
        String type=getIntent().getStringExtra("type");
        TextView textView=new TextView(this);
        WebView webView=new WebView(this);
        if (Objects.equals(type, "text")){
            String text=getIntent().getStringExtra("text");
            textView.setText(text);
            linearLayout.addView(textView);
        }else
        if (Objects.equals(type, "video")){
            String frame=getIntent().getStringExtra("frame");
            webView.loadData(frame,"text/html","utf-8");
            linearLayout.addView(webView);
        }
        Button button=new Button(this);
        button.setText(R.string.back);
        button.setOnClickListener(view -> finish());
        linearLayout.addView(button);
        setContentView(linearLayout);
    }
}

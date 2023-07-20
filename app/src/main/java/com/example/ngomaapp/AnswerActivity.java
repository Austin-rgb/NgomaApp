package com.example.ngomaapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
public class AnswerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String serverAddress=getSharedPreferences("credentials",0).getString("serverAddress","127.0.0.1");
        String url=serverAddress+"/smi.php";
        GData data=new GData(this,url);
        LinearLayout linearLayout=new LinearLayout(this);
        Button button=new Button(this);
        button.setText(R.string.back);
        button.setOnClickListener(view -> finish());
        linearLayout.addView(button);
        setContentView(linearLayout);
        TextView textView=new TextView(this);
        WebView webView=new WebView(this);
        data.setChangeListener(new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(change);
                    String text=jsonObject.getString("text");
                    String link = jsonObject.getString("link");
                    String data = "<iframe></iframe>";
                    textView.setText(text);
                    webView.loadData(link,"text/html","utf-8");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(String change) {

            }
        }).rawQuery("select text,link from answers where question_id=");

    }
}

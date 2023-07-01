package com.example.ngomaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class QuestionView extends LinearLayout {
    public QuestionView(Context ctx, String question, String answer) {
        super(ctx);
        setPadding(10, 20, 10, 20);
        setOrientation(LinearLayout.VERTICAL);
        TextView questionView = new TextView(getContext()),
                textAnswer = new TextView(getContext());
        Button openvideo = new Button(getContext()),
                opentext = new Button(getContext());
        openvideo.setText(R.string.video_description);
        questionView.setText(question);
        textAnswer.setTag("text");
        opentext.setText(R.string.text_answer);
        addView(questionView);
        LinearLayout linearLayout = new LinearLayout(getContext());
        WebView youtube = new WebView(getContext());
        youtube.setTag("youtube");
        ViewSwitcher answerView = new ViewSwitcher(getContext());
        answerView.addView(textAnswer);
        answerView.addView(youtube);
        answerView.setTag(Boolean.FALSE);
        linearLayout.addView(opentext);
        ScaleAnimation openAnimation = new ScaleAnimation(1, 1, 1, 10),
                closeAnimation = new ScaleAnimation(1, 1, 10, 1);
        openAnimation.setDuration(500);
        closeAnimation.setDuration(500);
        openvideo.setOnClickListener(view -> {
            if (connectedInternet()){
                Intent intent=new Intent(getContext(),AnswerActivity.class);
                intent.putExtra("type","video");
                intent.putExtra("frame","<iframe width='400' height='300' src='https://www.youtube.com/embed/tgbNymZ7vqY' ></iframe>" );
                getContext().startActivity(intent);
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.network_problem);
                builder.setMessage(R.string.please_connect_to_the_internet);
                builder.create().show();
            }
        });
        linearLayout.addView(openvideo);
        opentext.setTag(Boolean.TRUE);
        opentext.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), AnswerActivity.class);
            intent.putExtra("type","text");
            intent.putExtra("text",answer);
            getContext().startActivity(intent);
        });

        addView(linearLayout);

        addView(answerView);
    }

    boolean connectedNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) || (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
    }
    boolean connectedInternet(){
        if (connectedNetwork(getContext())) {
            try {
                URL url = new URL("http://www.google.com");
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                return true;
            } catch (IOException e) {
                return false;
            }
        }else return false;
    }
}
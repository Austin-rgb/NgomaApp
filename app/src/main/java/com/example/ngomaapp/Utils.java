package com.example.ngomaapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;

public class Utils {
    public static String encode(String[] keys,String[] values){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            try {
                if (i != 0) result.append("&");
                result.append(URLEncoder.encode(keys[i], "UTF-8")).append("=").append(URLEncoder.encode(values[i], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Log.e("Utils.encode", e.getMessage());
            }
        }
        Log.i("Utils.encode", "successfully encoded " + result);
        return result.toString();
    }

    public static String html2txt(String html) {
        return html.replaceAll("&quote;", "\"")
                .replaceAll("<p\\W+?>", "\n")
                .replaceAll("<.+?>", "")
                .replaceAll("</.+?>", "\n");
    }

    public static void showError(Context context, NgomaException error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(error.getTitle())
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Activity a = (Activity) context;
                    a.finish();
                })
                .setMessage(error.getMessage());

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void showWarning(Context context, NgomaException error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(error.getTitle())
                .setMessage(error.getMessage())
                .create()
                .show();
    }

    public static void setTestData(Context context) {
        LData lData = new LData(context, "ngomatest", null, 0);
        try {
            Scanner scanner = new Scanner(context.getAssets().open("testData.sql"));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext()) stringBuilder.append(scanner.next());
            lData.execSQL(stringBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

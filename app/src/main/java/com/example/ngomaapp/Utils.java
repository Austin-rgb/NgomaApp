package com.example.ngomaapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Scanner;

public enum Utils {
    ;

    public static String encode(String[] keys, String[] values) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            if (i != 0) result.append("&");
            try {
                result.append(URLEncoder.encode(keys[i], "UTF-8")).append("=").append(URLEncoder.encode(values[i], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
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
        Log.i("Showing error", error.getMessage());
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

    public static void setTestData(Context context, Callback callback) {
        Log.i("Utils", "setting up test data");
        try {
            inflateAsset2sqlite(context, "questions.json", callback);
            inflateAsset2sqlite(context, "answers.json", callback);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void inflateAsset2sqlite(Context context, String asset, Callback callback) throws IOException {
        try (LData lData = new LData(context, "ngomatest", null, 1)) {
            try (Scanner scanner = new Scanner(context.getAssets().open(asset))) {
                StringBuilder stringBuilder = new StringBuilder();
                while (scanner.hasNext()) stringBuilder.append(scanner.nextLine());
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    ExtendedJSONObj jsonObj = new ExtendedJSONObj(jsonArray.get(i).toString());
                    lData.insert(asset.replaceAll("\\.json", ""), jsonObj.getNames(), jsonObj.getValues(), callback);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class ExtendedJSONObj extends JSONObject {
        public ExtendedJSONObj(String s) throws JSONException {
            super(s);
        }

        public String[] getNames() throws JSONException {
            JSONArray jsonArray = names();
            String[] strings = new String[Objects.requireNonNull(jsonArray).length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                strings[i] = jsonArray.getString(i);
            }
            return strings;
        }

        public String[] getValues() throws JSONException {
            JSONArray jsonArray = names();
            String[] names,
                    values = new String[Objects.requireNonNull(jsonArray).length()];
            names = getNames();
            for (int i = 0; i < names.length; i++) {
                values[i] = get(names[i]).toString();
            }
            return values;
        }
    }

}


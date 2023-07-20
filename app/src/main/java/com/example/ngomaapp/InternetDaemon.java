package com.example.ngomaapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class InternetDaemon extends AsyncTask<String, String, String> {
    ChangeListener changeListener;
    String table;

    @Override
    protected String doInBackground(String... strings) {
        return getRemoteData(strings);
    }

    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (Objects.equals(s, "//connection problem//") || Objects.equals(s, "//incorrect url//"))
            changeListener.onFailure("//connection problem//");
        else changeListener.onSuccess(s);
    }

    public void connect(String... strings) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getRemoteData(strings);
            }
        });
        thread.setDaemon(true);
        thread.setName("internet");

    }

    private String getRemoteData(String[] strings) {
        URL url;
        StringBuilder result;
        try {
            url = new URL(strings[0]);
            HttpURLConnection huc;
            try {
                huc = (HttpURLConnection) url.openConnection();
                huc.setDoInput(true);
                huc.setDoOutput(true);
                OutputStream os;
                os = huc.getOutputStream();
                BufferedWriter bw;
                bw = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                if (strings.length > 1) {
                    bw.write(strings[1]);
                    bw.flush();
                    bw.close();
                    Log.i("InternetDaemon", "sent request " + strings[1]);
                }
                InputStreamReader isr;
                isr = new InputStreamReader(huc.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                result = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                isr.close();
                Log.i("InternetDaemon", result.toString());
            } catch (IOException e) {
                Log.e("Internet daemon", "got response " + e.getMessage());
                return "//connection problem//";
            }
            huc.disconnect();
        } catch (MalformedURLException e) {
            Log.e("Internet daemon", e.getMessage());
            return "//incorrect url//";
        }
        return result.toString();
    }

    public InternetDaemon setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
        return this;
    }

    public InternetDaemon forTable(String table) {
        this.table = table;
        return this;
    }

    public String forTable() {
        return table;
    }
}

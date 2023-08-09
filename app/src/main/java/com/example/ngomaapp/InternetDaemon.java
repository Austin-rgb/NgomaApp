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

public class InternetDaemon extends AsyncTask<String, String, Result> {
    Callback changeListener;

    @Override
    protected Result doInBackground(String... strings) {
        return getRemoteData(strings);
    }

    protected void onPostExecute(Result s) {
        super.onPostExecute(s);
        if (s.e() == null) {
            changeListener.callback(s.result(), null);
        } else changeListener.callback(null, s.e());
    }

    private Result getRemoteData(String[] strings) {
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
                Log.e("Internet daemon", "IOException " + e.getMessage());
                return new Result(null, new NgomaException("IOException ", e.getMessage()));
            }
            huc.disconnect();
        } catch (MalformedURLException e) {
            Log.e("Internet daemon", "MalformedURLException " + e.getMessage());
            return new Result(null, new NgomaException("MalformedURLException ", e.getMessage()));
        }
        return new Result(result.toString(), null);
    }

    public InternetDaemon setChangeListener(Callback changeListener) {
        this.changeListener = changeListener;
        return this;
    }

}

final class Result {
    private final String result;
    private final NgomaException e;

    Result(String result, NgomaException e) {
        this.result = result;
        this.e = e;
    }

    public String result() {
        return result;
    }

    public NgomaException e() {
        return e;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Result) obj;
        return Objects.equals(this.result, that.result) &&
                Objects.equals(this.e, that.e);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, e);
    }

    @Override
    public String toString() {
        return "Result[" +
                "result=" + result + ", " +
                "e=" + e + ']';
    }

}

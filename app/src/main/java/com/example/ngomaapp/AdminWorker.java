package com.example.ngomaapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AdminWorker extends AsyncTask<String,String,String> {
    ChangeListener listener;
    private Context context;
    String[] params;
    public AdminWorker(Context ctx, ChangeListener changeListener) {
        context=ctx;
        listener=changeListener;
    }

    @Override
    protected String doInBackground(String... params) {
        this.params=params;
        return crudData(params);
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.onSuccess(s);
        SharedPreferences.Editor editor= context.getSharedPreferences("data",0).edit();
        String index=String.join("",params);
        editor.putString(index,s);
        editor.apply();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    String crudData(String... args){
        String session=args[0];
        String script="na";
        StringBuilder result = new StringBuilder();
        try{
            URL url=new URL("http://localhost:8080/cruddata.php");
            try {
                HttpURLConnection huc=(HttpURLConnection)url.openConnection();
                huc.setDoInput(true);
                huc.setDoOutput(true);
                OutputStream os=huc.getOutputStream();
                BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String post= URLEncoder.encode("session","UTF-8")+
                        "="+URLEncoder.encode(session,"UTF-8")+
                        "&"+URLEncoder.encode("script","UTF-8")+
                        "="+URLEncoder.encode(script,"UTF-8");
                bw.write(post);
                bw.flush();
                bw.close();
                InputStreamReader isr=new InputStreamReader(huc.getInputStream());
                BufferedReader br=new BufferedReader(isr);
                String line;
                while((line=br.readLine())!=null){
                    result.append(line);
                }
                br.close();
                isr.close();
                huc.disconnect();
            }catch (IOException e){
                Activity context1 = (Activity) context;
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Please connect to the internet.");
                builder.setTitle("Network Error");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context1.finish();
                    }
                });
                context1.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog dialog= builder.create();
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                });
            }
        }catch(MalformedURLException e){
            throw new RuntimeException(e);
        }
        return result.toString();
    }
}

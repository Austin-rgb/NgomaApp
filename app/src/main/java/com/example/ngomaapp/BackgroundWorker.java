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

public class BackgroundWorker extends AsyncTask<String,String,String> {
    ChangeListener listener;
    private Context context;
    String[] params;
    public BackgroundWorker(Context ctx, ChangeListener changeListener) {
        context=ctx;
        listener=changeListener;
    }

    @Override
    protected String doInBackground(String... params) {
        this.params=params;
        return getRemoteData(params);
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
    String getRemoteData(String... args){
        String request=args[0];
        String form="na";
        if (args.length>1)form=args[1];
        String subject="na";
        if (args.length>2)subject=args[2];
        String topic="na";
        if (args.length>3)topic=args[3];
        StringBuilder result = new StringBuilder();
                try{
                    URL url=new URL("http://localhost:8080/getdata.php");
                    try {
                        HttpURLConnection huc=(HttpURLConnection)url.openConnection();
                        huc.setDoInput(true);
                        huc.setDoOutput(true);
                        OutputStream os=huc.getOutputStream();
                        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                        String post= URLEncoder.encode("request","UTF-8")+
                                "="+URLEncoder.encode(request,"UTF-8")+
                                "&"+URLEncoder.encode("class","UTF-8")+
                                "="+URLEncoder.encode(form,"UTF-8")+
                                "&"+URLEncoder.encode("subject","UTF-8")+
                                "="+URLEncoder.encode(subject,"UTF-8")+
                                "&"+URLEncoder.encode("topic","UTF-8")+
                                "="+URLEncoder.encode(topic,"UTF-8");
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

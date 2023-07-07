package com.example.ngomaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class DataBaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "Ngoma.db";
    private final Context context;
    public String TABLE_NAME;

    public DataBaseHelper(Context context, String table) {
        super(context, DATABASE_NAME, null, 1);
        TABLE_NAME = table;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "
                        + TABLE_NAME
                        + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TINYTEXT,DESCRIPTION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public Cursor getAllData() {
        Cursor res = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Log.d("Progress", "querying the database");
            res = db.rawQuery("Select * from " + TABLE_NAME, null);
        } catch (SQLException e) {
            Log.d("Progress", e.getMessage()+", creating new table, "+TABLE_NAME);
            db.execSQL("create table "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TINYTEXT,DESCRIPTION TEXT)");
            Log.d("Progress", "channelling remote data to the new table "+TABLE_NAME);
            insertJson(getRemoteData("127.0.0.1","root","Ostiness","ngomatest",TABLE_NAME));
            Log.d("Progress", "Successfully added remote data, querying the database");
            res = db.rawQuery("Select * from " + TABLE_NAME, null);
        }
        return res;
    }
String getRemoteData(String... args){
        String server=args[0];
        String username=args[1];
        String password=args[2];
        String database=args[3];
        String table=args[4];
    final String[] result = {""};
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try{
                URL url=new URL("http://localhost:8080/getdata.php");
                try {
                    HttpURLConnection huc=(HttpURLConnection)url.openConnection();
                    huc.setDoInput(true);
                    huc.setDoOutput(true);
                    OutputStream os=huc.getOutputStream();
                    BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    String post=URLEncoder.encode("username","UTF-8")+
                            "="+URLEncoder.encode(username,"UTF-8")+
                            "&"+URLEncoder.encode("password","UTF-8")+
                            "="+URLEncoder.encode(password,"UTF-8")+
                            "&"+URLEncoder.encode("database","UTF-8")+
                            "="+URLEncoder.encode(database,"UTF-8")+
                            "&"+URLEncoder.encode("table","UTF-8")+
                            "="+URLEncoder.encode(table,"UTF-8")+
                            "&"+URLEncoder.encode("servername","UTF-8")+
                            "="+URLEncoder.encode(server,"UTF-8");
                    bw.write(post);
                    bw.flush();
                    bw.close();
                    InputStreamReader isr=new InputStreamReader(huc.getInputStream());
                    BufferedReader br=new BufferedReader(isr);
                    String line;
                    while((line=br.readLine())!=null){
                        result[0] +=line;
                    }
                    br.close();
                    isr.close();
                    huc.disconnect();
                    notify();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }catch(MalformedURLException e){
                Log.e("URLError","malformed url");
            }
        }
    };
    runnable.run();
    try {
        Log.d("Progress","waiting for remote data");
        wait();
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    return result[0];
}
void insertJson(String json)  {
    JSONArray jsonArray;
    try {
        jsonArray = new JSONArray(json);
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            insert(jsonObject.getString("name"),jsonObject.getString("description"));
        }
    } catch (JSONException e) {
        Log.e("JSONLError",e.getMessage());
    }


}
    public boolean insert(String name, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("DESCRIPTION", comment);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }
}

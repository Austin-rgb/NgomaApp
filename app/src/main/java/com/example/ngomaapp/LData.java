package com.example.ngomaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LData extends SQLiteOpenHelper {
    String databaseName;
    Context context;
    public LData(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        databaseName = name;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table questions(question_id int,question text,topic tiny_text,subject tiny_text,class tiny_text,upload_date date default current_timestamp,teacher varchar(64))");
        sqLiteDatabase.execSQL("create table answers(question_id int,answer text,link varchar(64))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS questions");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS answers");
    }

    public LData rawQuery(String query, Callback callback) {
        Cursor cursor = this.getWritableDatabase().rawQuery(query, null);
        String[] columns = cursor.getColumnNames();
        Log.i("LData", " Querying " + query);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            JSONObject jsonObject = new JSONObject();
            for (int j = 0; j < columns.length; j++) {
                try {
                    jsonObject.put(columns[j], cursor.getString(j));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            jsonArray.put(jsonObject);
        }
        callback.callback(jsonArray.toString(), null);
        cursor.close();
        return this;
    }

    public void insert(String table, String[] columns, String[] values, Callback callback) {
        Log.i("LData", "inserting into " + table + " " + Arrays.toString(columns) + " " + Arrays.toString(values));
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            contentValues.put(columns[i], values[i]);
        }
        SQLiteDatabase database = this.getWritableDatabase();
        long r = database.insert(table, null, contentValues);
        database.close();
        if (callback == null) {
            return;
        }
        if (r < 0)
            callback.callback(null, new NgomaException("Database error", "Could not insert data into the local database"));
        else callback.callback("Success", null);
    }

    public LData update(String table, String[] columns, String[] values, String where, String[] fields) {
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            contentValues.put(columns[i], values[i]);
        }
        this.getWritableDatabase().update(table, contentValues, where, fields);
        return this;
    }

    public void delete(String table, String where, String[] whereArgs) {
        this.getWritableDatabase().delete(table, where, whereArgs);
    }
}

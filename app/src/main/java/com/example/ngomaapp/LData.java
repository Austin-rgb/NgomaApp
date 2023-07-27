package com.example.ngomaapp;

import android.app.AlertDialog;
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

public class LData extends SQLiteOpenHelper {
    String databaseName;
    Context context;
    Callback callback;

    public LData(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        databaseName = name;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table questions(question_id int,question text,topic tiny_text,subject tiny_text,class tiny_text,upload_date date default current_timestamp,teacher varchar(64))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public LData rawQuery(String query, Callback callback) {
        this.callback = callback;
        Cursor cursor = null;
        try {
            cursor = this.getWritableDatabase().rawQuery(query, null);
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Database error")
                    .setMessage(e.getMessage())
                    .create()
                    .show();
            Log.e("LData", e.getMessage());
            failure(e);
        }

        assert cursor != null;
        String[] columns = cursor.getColumnNames();
        int rows = cursor.getCount();
        if (rows > 0) {
            StringBuilder jsonArray = new StringBuilder();
            jsonArray.append("[");
            for (int i = 0; i < rows; i++) {
                cursor.moveToPosition(i);
                JSONObject jsonObject = new JSONObject();
                for (int j = 0; j < columns.length; j++) {
                    try {
                        jsonObject.put(columns[i],cursor.getString(i));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                jsonArray.append(jsonObject);
            }
            jsonArray.append("]");
            try {
                success(String.valueOf(new JSONArray(jsonArray.toString())));
            } catch (JSONException e) {
                failure(new Exception("Could not parse the data returned from the database"));
            }
        } else {
            failure(new Exception("No data"));
        }
        cursor.close();
        return this;
    }

    public void insert(String table, String[] columns, String[] values, Callback callback) {
        this.callback = callback;
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            contentValues.put(columns[i], values[i]);
        }
        SQLiteDatabase database = this.getWritableDatabase();
        long r = database.insert(table, null, contentValues);
        database.close();
        if (r < 0) failure(new Exception("Could not insert data into the local database"));

    }

    public LData update(String table, String[] columns, String[] values, String where, String[] fields, Callback callback) {
        this.callback = callback;
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            contentValues.put(columns[i], values[i]);
        }
        this.getWritableDatabase().update(table, contentValues, where, fields);
        return this;
    }

    public void delete(String table, String where, String[] whereArgs, Callback callback) {
        this.callback = callback;
        this.getWritableDatabase().delete(table, where, whereArgs);
    }

    private void success(String change) {
        if (callback != null) callback.callback(change, null);
    }

    private void failure(Exception change) {
        if (callback != null) callback.callback(null, change);
    }
}

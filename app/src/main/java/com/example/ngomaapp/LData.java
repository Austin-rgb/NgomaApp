package com.example.ngomaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LData extends SQLiteOpenHelper {
    ChangeListener changeListener;
    String databaseName;
    public LData(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        databaseName=name;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table questions(question_id int,question text,topic tiny_text,subject tiny_text,class tiny_text,upload_date date default current_timestamp,teacher varchar(64))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public LData rawQuery(String query) {
        Cursor cursor=this.getWritableDatabase().rawQuery(query,null);
        String[] columns= cursor.getColumnNames();
        int rows= cursor.getCount();
        if (rows>0) {
            StringBuilder jsonArray = new StringBuilder();
            jsonArray.append("[");
            for (int i = 0; i < rows; i++) {
                cursor.moveToPosition(i);
                JSONObject jsonObject=new JSONObject();
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
                changeListener.onSuccess(String.valueOf(new JSONArray(jsonArray.toString())));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            changeListener.onFailure("No data");
        }
        cursor.close();
        return this;
    }

    public LData insert(String table, String[] columns, String[] values) {
        ContentValues contentValues=new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            contentValues.put(columns[i],values[i]);
        }
        SQLiteDatabase database= this.getWritableDatabase();
                ;
        changeListener.onSuccess(String.valueOf(database.insert(table,null,contentValues)));
        return this;
    }

    public LData update(String table, String[] columns, String[] values, String where, String[] fields) {
        ContentValues contentValues=new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            contentValues.put(columns[i],values[i]);
        }
        this.getWritableDatabase().update(table,contentValues,where,fields);
        return this;
    }

    public LData delete(String table, String where, String[] whereArgs) {
        this.getWritableDatabase().delete(table,where,whereArgs);
        return this;
    }

    public LData then(ChangeListener changeListener) {
        this.changeListener=changeListener;
        return this;
    }
}

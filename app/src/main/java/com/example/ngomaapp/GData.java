package com.example.ngomaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GData {
    Context ctx;
    private final Boolean backup;
    LData lData;
    RData rData;
    String table;
    String link, username, password, database;

    public GData(Context context, String link, String table, Boolean backup) {
        this.ctx = context;
        this.backup = backup;
        SharedPreferences sharedPreferences = context.getSharedPreferences("credentials", 0);
        String serverAddress = sharedPreferences.getString("serverAddress", "http://127.0.0.1:8080");
        this.link = serverAddress + link;
        this.username = sharedPreferences.getString("username", "student");
        this.password = sharedPreferences.getString("password", "student");
        this.database = sharedPreferences.getString("database", "ngomatest");
        this.table = table;
        rData = new RData(context, this.link, username, password, this.database);
        lData = new LData(context, database, null, 1);
    }
    public GData rawQuery(String query, Callback callback) {
        Log.i("GData", "rawQuery " + query);
        lData.rawQuery(query, (result, error) -> {
            if (result != null) {
                callback.callback(result, null);
            } else {
                rData.rawQuery(query, (result1, error1) -> {
                    if (result1 != null) {
                        try {
                            JSONArray insert = new JSONArray(result1);
                            for (int i = 0; i < insert.length(); i++) {
                                JSONObject object = insert.getJSONObject(i);
                                JSONArray names = object.names();
                                assert names != null;
                                String[] columns = new String[names.length()];
                                String[] values = new String[names.length()];
                                for (int j = 0; j < names.length(); j++) {
                                    columns[j] = names.getString(j);
                                }
                                for (int j = 0; j < names.length(); j++) {
                                    values[j] = object.getString(columns[j]);
                                }
                                if (backup) lData.insert(table, columns, values, null);
                            }
                            callback.callback(result1, null);
                        } catch (JSONException e) {
                            callback.callback(null, new NgomaException("Json error", e.getMessage()));
                            Log.e("GData", e.getMessage());
                        }
                    } else {
                        callback.callback(null, error1);
                    }
                });
            }
        });
        return this;
    }

    public GData insert(String table, String[] columns, String[] values, Callback callback) {
        rData.insert(table, columns, values, (result, error) -> {
            if (result != null) lData.insert(table, columns, values, callback);
            else callback.callback(null, error);
        });
        return this;
    }

    public GData update(String table, String set, String where, Callback callback) {
        //create columns, values, where and whereArgs for sqlite dependant LData
        String[] sets = set.split(",");
        String[] columns = new String[sets.length];
        String[] values = new String[sets.length];
        for (int i = 0; i < sets.length; i++) {
            String[] strings = sets[i].split("=");
            columns[i] = strings[0];
            values[i] = strings[1];
        }
        String[] wheres = where.split(",");
        String[] fields = new String[wheres.length];
        rData.update(table, set, where, (result, error) -> {
            if (result == null) {
                callback.callback(null, error);
            } else {
                StringBuilder localWhere = new StringBuilder();
                for (int i = 0; i < wheres.length; i++) {
                    String[] strings = wheres[i].split("=");
                    localWhere.append(strings[0]).append("=?");
                    fields[i] = strings[1];
                }
                lData.update(table, columns, values, where, fields, callback);
            }
        });

        return this;
    }

    public void delete(String table, String where, Callback callback) {
        String[] wheres = where.split(",");
        String[] whereArgs = new String[wheres.length];
        rData.delete(table, where, (result, error) -> {
            if (result != null) {
                StringBuilder localWhere = new StringBuilder();
                for (int i = 0; i < wheres.length; i++) {
                    String[] s = wheres[i].split("=");
                    localWhere.append(s[0]).append("=?");
                    whereArgs[i] = s[1];
                }
                lData.delete(table, localWhere.toString(), whereArgs, callback);
            } else
                callback.callback(null, error);
        });

    }
}

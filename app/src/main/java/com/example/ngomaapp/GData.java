package com.example.ngomaapp;

import android.content.Context;
import android.content.SharedPreferences;

public class GData {
    ChangeListener changeListener;
    ChangeListener listener;
    Context ctx;
    LData lData;
    RData rData;
    String link, username, password, database;

    public GData(Context context, String link ) {
        this.ctx = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("credentials", 0);
        String serverAddress = sharedPreferences.getString("serverAddress", "http://127.0.0.1");
        this.link = serverAddress + link;
        this.username = sharedPreferences.getString("username", "student");
        this.password = sharedPreferences.getString("password", "student");
        this.database = sharedPreferences.getString("database", "ngomatest");
        rData = new RData(context, this.link, username, password, this.database);
        lData = new LData(context, database, null, 1);
        listener = new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                if (change.contains("<br")) changeListener.onFailure(change);
                else changeListener.onSuccess(change);
            }

            @Override
            public void onFailure(String change) {
                changeListener.onFailure(change);
            }
        };
    }

    public GData rawQuery(String query) {
        lData.then(new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                changeListener.onSuccess(change);
            }

            @Override
            public void onFailure(String change) {
                rData.then(listener).rawQuery(query);
            }
        }).rawQuery(query);
        return this;
    }

    public GData insert(String table, String[] columns, String[] values) {
        rData.then(new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                lData.then(listener).insert(table, columns, values);
            }

            @Override
            public void onFailure(String change) {
                listener.onFailure(change);
            }
        }).insert(table, columns, values);
        return this;
    }

    public GData update(String table, String set, String where) {
        //create columns, values, where and whereArgs for sqlite dependant LData
        String[] sets=set.split(",");
        String[] columns=new String[sets.length];
        String[] values=new String[sets.length];
        for (int i = 0; i < sets.length; i++) {
            String[] strings=sets[i].split("=");
            columns[i]=strings[0];
            values[i]=strings[1];
        }
        String[] wheres=where.split(",");
        String[] fields=new String[wheres.length];
        rData.then(new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                StringBuilder localWhere=new StringBuilder();
                for (int i = 0; i < wheres.length; i++) {
                    String[] strings=wheres[i].split("=");
                    localWhere.append(strings[0]).append("=?");
                    fields[i]=strings[1];
                }
                lData.update(table, columns, values, where, fields).then(listener);
            }

            @Override
            public void onFailure(String change) {
                listener.onFailure(change);
            }
        }).update(table,set,where);

        return this;
    }

    public GData delete(String table, String where) {
        String[] wheres=where.split(",");
        String[] whereArgs=new String[wheres.length];
        rData.then(new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                //do the delete on the local database
                StringBuilder localWhere= new StringBuilder();
                for (int i = 0; i < wheres.length; i++) {
                    String[] s=wheres[i].split("=");
                    localWhere.append(s[0]).append("=?");
                    whereArgs[i]=s[1];
                }
                lData.then(listener).delete(table, localWhere.toString(), whereArgs);
            }

            @Override
            public void onFailure(String change) {
                listener.onFailure(change);
            }
        })
                //do delete on the remote database
                .delete(table, where);

        return this;
    }
    public GData setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
        return this;
    }
}

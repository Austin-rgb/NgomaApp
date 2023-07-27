package com.example.ngomaapp;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class RData {

    Context ctx;
    String link;
    String username;
    String password;
    String database;
    InternetDaemon internetDaemon;
    AlertDialog.Builder serverError;

    public RData(Context context, String link, String username, String password, String database) {
        this.ctx = context;
        this.link = link;
        this.username = username;
        this.password = password;
        this.database = database;
        internetDaemon = new InternetDaemon();
        serverError = new AlertDialog.Builder(context);
        serverError.setTitle("Server Error");

    }

    public RData rawQuery(String script, Callback callback) {
        internetDaemon.setChangeListener((result, error) -> {
                    if (error == null) {
                        if (result.contains("<br")) {
                            WebView view = new WebView(ctx);
                            view.loadData(result, "text/html", "utf-8");
                            serverError.setView(view)
                                    .setPositiveButton("ok", (dialogInterface, i) -> {
                                        Activity a = (Activity) ctx;
                                        a.finish();
                                    })
                                    .create()
                                    .show();
                        } else callback.callback(result, null);
                    } else callback.callback(null, error);
        });
        try {
            internetDaemon.execute(link, Utils.encode(new String[]{"username", "password", "database", "script"}, new String[]{username, password, database, script}));
        } catch (Exception ignored) {

        }
        return null;
    }

    public void insert(String table, String[] columns, String[] values, Callback callback) {
        StringBuilder script = new StringBuilder("insert into " + table + "(");
        for (String s :
                columns) {
            script.append(s);
        }
        script.append(")values(");
        for (String s :
                values) {
            script.append("\"");
            script.append(s);
            script.append("\",");
        }
        script.append(")");
        internetDaemon.setChangeListener(callback)
                .execute(link, Utils.encode(new String[]{"username", "password", "database", "script"}, new String[]{username, password, database, script.toString()}));
    }

    public RData update(String table, String set, String where, Callback callback) {
        String script = "update " + table + " set " + set + " where " + where;
        internetDaemon.setChangeListener(callback)
                .execute(link, Utils.encode(new String[]{"username", "password", "database", "script"}, new String[]{username, password, database, script}));

        return this;
    }

    public void delete(String table, @NonNull String where, Callback callback) {
        String script = "delete from " + table + " where " + where;

        internetDaemon.setChangeListener(callback)
                .execute(link, Utils.encode(new String[]{"username", "password", "database", "script"}, new String[]{username, password, database, script}));

    }
}

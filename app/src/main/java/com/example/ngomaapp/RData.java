package com.example.ngomaapp;

import android.content.Context;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class RData {

    Context ctx;
    ChangeListener changeListener;
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
        serverError=new AlertDialog.Builder(context);
        serverError.setTitle("Server Error");

    }

    public RData then(ChangeListener select) {
        this.changeListener = new ChangeListener() {
            @Override
            public void onSuccess(String change) {
                if (change.contains("<br")) {
                    WebView error=new WebView(ctx);
                    error.loadData(change,"text/html","utf-8");
                    serverError.setView(error).create().show();
                } else select.onSuccess(change);
            }

            @Override
            public void onFailure(String change) {
                select.onFailure(change);
            }
        };
        return this;
    }

    public RData rawQuery(String script) {
        internetDaemon.setChangeListener(changeListener)
                .execute(link, Utils.encode(new String[]{"username", "password", "database", "script"}, new String[]{username, password, database, script}));

        return null;
    }

    public RData insert(String table, String[] columns, String[] values) {
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
        internetDaemon.setChangeListener(changeListener)
                .execute(link, Utils.encode(new String[]{"username", "password", "database", "script"}, new String[]{username, password, database, script.toString()}));
        return this;
    }

    public RData update(String table, String set, String where) {
        String script = "update "+table+" set "+set+" where "+where;
        internetDaemon.setChangeListener(changeListener)
                .execute(link, Utils.encode(new String[]{"username", "password", "database", "script"}, new String[]{username, password, database, script}));

        return this;
    }

    public RData delete(String table, @NonNull String where) {
        String script = "delete from " + table + " where "+where;

        internetDaemon.setChangeListener(changeListener)
                .execute(link, Utils.encode(new String[]{"username", "password", "database", "script"}, new String[]{username, password, database, script}));

        return this;
    }
}

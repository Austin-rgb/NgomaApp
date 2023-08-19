package com.example.ngomaapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

public class Control implements Serializable {
    transient Context context;
    private String form;
    private String subject;
    private String topic;
    private String questionId;
    private String classes;
    private String subjects;
    private String topics;
    private String currentView;
    private String previousView;
    private String questions;
    private String currentTable;

    public Control(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getClasses(Callback callback) {
        try (LData lData = new LData(context, "ngomatest", null, 1)) {
            lData.rawQuery("select distinct class from questions", (result, error) -> {
                classes = result;
                previousView = currentView;
                currentView = classes;
                callback.callback(result, error);
            });
        }
    }

    public void getSubjects(Callback callback) {
        try (LData lData = new LData(context, "ngomatest", null, 1)) {
            lData.rawQuery("select distinct subject from questions where class='"
                            + form
                            + "'",
                    (result, error) -> {
                        subjects = result;
                        callback.callback(result, error);
                    });
        }
    }

    public void moveToNext() {

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(previousView);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            try {
                if (Objects.equals(jsonArray.getString(i), currentTable)) {
                    currentTable = jsonArray.getString(i + 1);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getNext(String table) {
        switch (table) {
            case "class":
                return navigator(classes, table);
            case "subject ":
                return navigator(subjects, table);
            case "topic ":
                return navigator(topics, table);
        }

        return table;
    }

    String navigator(String previousView, String table) {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(previousView);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String next = null;
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            try {
                if (Objects.equals(jsonArray.getJSONObject(i).getString(table), currentTable)) {
                    next = jsonArray.getJSONObject(i + 1).getString(table);
                    Log.i("Control.getNext", "got " + currentTable + "in " + previousView);
                    break;
                }
            } catch (JSONException e) {
                Log.e("Control.getNext", e.getMessage() + " while working with " + previousView);
            }
        }
        if (next == null) {
            Log.i("Control.getNext", "could not get " + currentTable + " in " + previousView);
            return "End";
        } else return next;

    }

    public void moveToPrevious() {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(previousView);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 1; i < jsonArray.length(); i++) {
            try {
                if (Objects.equals(jsonArray.getString(i), currentTable)) {
                    form = jsonArray.getString(i - 1);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getPrevious(String table) {

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(previousView);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 1; i < jsonArray.length(); i++) {
            try {
                String test = jsonArray.getJSONObject(i).getString(table);
                if (Objects.equals(test, currentTable)) {
                    Log.i("Control.getNext", "got " + currentTable + " in " + previousView);
                    return jsonArray.getJSONObject(i - 1).getString(table);
                }
            } catch (JSONException e) {
                Log.e("Control.getPrevious", e.getMessage() + " while working with " + previousView);
            }
        }
        Log.i("Control.getPrevious", "could not get " + currentTable + " in " + previousView);
        return "End";
    }

    public void getAnswer(Callback callback) {
        try (LData lData = new LData(context, "ngomatest", null, 1)) {
            lData.rawQuery("select answer,link from answers where question_id="
                    + questionId, callback);
        }
    }

    public void getQuestions(Callback callback) {
        try (LData lData = new LData(context, "ngomatest", null, 1)) {
            lData.rawQuery("select class,subject,topic,question,question_id from questions where class='" + form + "' and subject='" + subject + "' and topic='" + topic + "'", (result, error) -> {
                questions = result;
                previousView = currentView;
                currentView = result;
                callback.callback(result, error);
            });
        }
    }

    public void getTopics(Callback callback) {
        try (LData lData = new LData(context, "ngomatest", null, 1)) {
            lData.rawQuery("select distinct topic from questions where class='"
                    + form
                    + "' and subject='"
                    + subject
                    + "'", (result, error) -> {
                topics = result;
                previousView = currentView;
                currentView = topics;
                callback.callback(result, error);
            });
        }
    }

    public void chooseClass(String form) {
        this.currentTable = form;
        this.form = form;
    }

    public void chooseSubject(String subject) {
        this.currentTable = subject;
        this.subject = subject;
    }

    public void chooseTopic(String topic) {
        this.topic = topic;
        this.currentTable = topic;
    }

    public void chooseQuestion(String question) {
        //search for question id
        this.currentTable = question;
        try {
            JSONArray jsonArray = new JSONArray(questions);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("question").equals(question))
                    questionId = String.valueOf(jsonObject.getInt("question_id"));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}

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
    String answer;
    String form;
    String subject;
    String topic;
    String question;
    String questionId;
    String classes;
    String subjects;
    String topics;
    String currentView;
    String previousView;
    String questions;
    String currentTable;

    public Control(Context context) {
        this.context = context;
    }

    public Control() {
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
                        previousView = currentView;
                        currentView = subjects;
                        callback.callback(result, error);
                    });
        }
    }

    public void next() {

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(classes);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            try {
                if (Objects.equals(jsonArray.getString(i), form)) {
                    form = jsonArray.getString(i + 1);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getNext() {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(previousView);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            try {
                if (Objects.equals(jsonArray.getString(i), currentTable)) {
                    Log.i("Control.getNext", "got " + currentTable);
                    return jsonArray.getString(i + 1);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        Log.i("Control.getNext", "could not get " + currentTable);
        return "End";
    }

    public void previous() {
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

    public String getPrevious() {

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(previousView);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 1; i < jsonArray.length(); i++) {
            try {
                if (Objects.equals(jsonArray.getString(i), currentTable)) {
                    Log.i("Control.getNext", "got " + currentTable);
                    return jsonArray.getString(i - 1);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        Log.i("Control.getPrevious", "could not get " + currentTable);
        return "End";
    }

    public void getAnswer(Callback callback) {
        try (LData lData = new LData(context, "ngomatest", null, 1)) {
            lData.rawQuery("select answer,link from answers where question_id="
                    + questionId, (result, error) -> {
                answer = result;
                callback.callback(result, error);
            });
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

    public int teacherLogin(String question) {
        return 0;
    }

    public int addQuestion(String question) {
        return 0;
    }

    public int deleteClass(String question) {
        return 0;
    }

    public int deleteSubject(String question) {
        return 0;
    }

    public int deleteTopic(String question) {
        return 0;
    }
}

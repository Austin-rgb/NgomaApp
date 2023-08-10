package com.example.ngomaapp;

import android.content.Context;

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
    String questions;

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

    public void next() {
        if ("subjects".equals(currentView)) {
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
    }

    public void previous() {
        if ("subjects".equals(currentView)) {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(classes);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            for (int i = 1; i < jsonArray.length(); i++) {
                try {
                    if (Objects.equals(jsonArray.getString(i), form)) {
                        form = jsonArray.getString(i - 1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
                callback.callback(result, error);
            });
        }
    }

    public int chooseClass(String form) {
        currentView = "classes";
        this.form = form;
        return 0;
    }

    public int chooseSubject(String subject) {
        currentView = "subjects";
        this.subject = subject;
        return 0;
    }

    public int chooseTopic(String topic) {
        currentView = "topics";
        this.topic = topic;
        return 0;
    }

    public int chooseQuestion(String question) {
        //search for question id
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
        return 0;
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

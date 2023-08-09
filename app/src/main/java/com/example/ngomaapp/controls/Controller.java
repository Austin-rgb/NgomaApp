package com.example.ngomaapp.controls;

import org.json.JSONArray;
import org.json.JSONObject;

public interface Controller {
    public JSONArray getClasses();

    public JSONArray getSubjects();

    public JSONArray getTopics();

    public JSONObject getAnswer();

    public int upgradeToPremium();

    public int teacherLogin();

    public int administratorLogin();

    public int addQuestion();

    public int deleteQuestion();

    public int deleteTopic();
}

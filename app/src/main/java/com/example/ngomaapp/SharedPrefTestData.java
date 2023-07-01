package com.example.ngomaapp;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefTestData {
static Context ctx;
public static void inflate(Context context){
ctx=context;
  SharedPreferences sp=ctx.getSharedPreferences("classes",0);
  SharedPreferences.Editor spe=sp.edit();
  spe.putString("1","Form 1");
  setSubjects("Form 1");
  spe.putString("2","Form 2");
  setSubjects("Form 2");
  spe.putString("3","Form 3");
  setSubjects("Form 3");
  spe.putString("4","Form 4");
  setSubjects("Form 4");
  spe.apply();
}
static void setSubjects(String s){
  SharedPreferences sp=ctx.getSharedPreferences(s,0);
  SharedPreferences.Editor spe=sp.edit();
  spe.putString("1","Mathematics");
  setTopics(s+"%Mathematics");
  spe.putString("2","Chemistry");
  setTopics(s+"%Chemistry");
  spe.commit();
  }
  static void setTopics(String s){
    SharedPreferences sp=ctx.getSharedPreferences(s,0);
  SharedPreferences.Editor spe=sp.edit();
spe.putString("1","Topic 1");
setQuestions(s+"%Topic 1");
spe.putString("2","Topic 2");
setQuestions(s+"%Topic 2");
spe.putString("3","Topic 3");
setQuestions(" Topic 3");
spe.putString("4","Topic 4");
setQuestions(s+"%Topic 4");
spe.commit();
  }
  static void setQuestions(String s){
    SharedPreferences sp=ctx.getSharedPreferences(s,0);
  SharedPreferences.Editor spe=sp.edit();
spe.putString("1","Question 1");
spe.putString("2","Question 2");
spe.putString("3","Question 3");
spe.putString("4","Question 4");
spe.commit();
  }
}
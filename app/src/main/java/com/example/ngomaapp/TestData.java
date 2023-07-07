package com.example.ngomaapp;

import android.content.Context;
import android.database.Cursor;


public class TestData {
static Context context;
public static void inflate(Context ctx){
  context=ctx;
  setClasses("classes");
}
static  void setClasses(String table){
    DataBaseHelper db=new DataBaseHelper(context,table);
  db.insert("Form%1","2 subjects ");
  db.insert("Form%2","2 subjects ");
  db.insert("Form%3","2 subjects ");
  db.insert("Form%4","2 subjects ");
  db.close();
  setSubjects("classes");
  }
  static void setSubjects(String table){
    DataBaseHelper db=new DataBaseHelper(context,table);
    Cursor cursor=db.getAllData();
    /*for(String data:cursor){
       db=new DataBaseHelper(context,data);
      db.insert("Mathematics","20 topics");
      db.insert("Chemistry","20 topics");
      db.close();
      setTopics(data);
    }*/
  }
static void setTopics(String table){
    DataBaseHelper db=new DataBaseHelper(context,table);
  Cursor cursor=db.getAllData();
  /*for (String data:cursor){
    db=new DataBaseHelper(context,data);
    db.insert("Topic%1","10 Questions");
    db.insert("Topic%2","10 Questions");
    db.insert("Topic%3","10 Questions");
    db.insert("Topic%4","10 Questions");
    db.close();
    setQuestions(table+"%%"+data);
  }*/
  }
static  void setQuestions(String table){
   DataBaseHelper db=new DataBaseHelper(context,table);
   Cursor cursor=db.getAllData();
   /*for (String data:cursor){
     db=new DataBaseHelper(context,data);
       db.insert("Question 1", "Answer 1");
       db.insert("Question 2", "Answer 2");
db.insert("Question 3", "Answer 3");
db.insert("Question 4", "Answer 4");
db.close();
     
   }*/
  }
}

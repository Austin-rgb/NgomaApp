package com.example.ngomaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.LinearLayout;

public class QuestionsTableView extends CustomView {
    public QuestionsTableView(Context ctx, String table) {
        super(ctx, table);
        greetings.setText(getContext().getString(R.string.questions_on)+table);
        setContent();
       // setNavigation();
    }
    void setContent(){
  LinearLayout linearLayout=new LinearLayout(getContext());
  linearLayout.setOrientation(LinearLayout.VERTICAL);
  int counter= 1;
  SharedPreferences sp=getContext().getSharedPreferences(table,0);
  String next;
  while(true){
    next=sp.getString(Integer.toString(counter),"");
    if(next.equals(""))break;
    QuestionView questionView=new QuestionView(getContext(),next,"The right answer for this question is that this is a test question that needs a test answer");
    linearLayout.addView(questionView);
    counter+=1;
}
content.addView(linearLayout);
}

}
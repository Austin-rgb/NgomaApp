package com.example.ngomaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;

public class QuestionsTableView extends CustomView implements ChangeListener{
    public QuestionsTableView(Context ctx,String form, String subject,String topic) {
        super(ctx, topic);
        greetings.setText(getContext().getString(R.string.questions_on)+topic);
        new BackgroundWorker(getContext(),this).execute(ctx.getSharedPreferences("setup",0).getString("questions","")+"where class="+form+" and subject="+subject+"and topic="+topic+";");
        //setContent();

        //setContent();
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

    @Override
    public void onSuccess(String change) {
        try {
            JSONArray jsonArray=new JSONArray(change);
            for (int i=0;i<jsonArray.length()-1;i++){
                result.add(jsonArray.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            result.add(e.getMessage());
        }
        adapter.notifyDataSetChanged();
    }
}
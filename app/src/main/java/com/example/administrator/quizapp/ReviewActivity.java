package com.example.administrator.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.example.administrator.quizapp.adapter.QuestionAdapter;
import com.example.administrator.quizapp.lib.AppHelper;
import com.example.administrator.quizapp.model.Question;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    RecyclerView rvQuestion;
    ArrayList<Question> arrQuestion;
    QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        rvQuestion = findViewById(R.id.rvQuestion);

        arrQuestion = new ArrayList<>();

        try {
            for (int i = 0; i < AppHelper.questionData.length(); i++) {
                JSONObject obj = (JSONObject) AppHelper.questionData.get(i);

                if (AppHelper.isChallenge()) {
                    arrQuestion.add(new Question(
                            obj.getString("_id"),
                            obj.getString("level"),
                            obj.getString("content"),
                            obj.getString("image"),
                            obj.getString("base64Image"),
                            obj.getJSONObject("player1"),
                            obj.getJSONArray("answers")
                    ));
                } else {
                    arrQuestion.add(new Question(
                            obj.getString("_id"),
                            obj.getString("level"),
                            obj.getString("content"),
                            obj.getString("image"),
                            obj.getString("base64Image"),
                            obj.getJSONObject("player1"),
                            obj.getJSONObject("player2"),
                            obj.getJSONArray("answers")
                    ));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new QuestionAdapter(this, arrQuestion);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvQuestion.setLayoutManager(layoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvQuestion);

        rvQuestion.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

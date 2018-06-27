package com.example.administrator.quizapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.quizapp.adapter.AnswerAdapter;
import com.example.administrator.quizapp.lib.AppHelper;
import com.example.administrator.quizapp.model.Answer;
import com.example.administrator.quizapp.model.Question;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements AnswerAdapter.OnItemClickedListener {

    Chronometer cTime;
    public int currentQuestion = 0;
    TextView tvQuestion, tvScore;
    ImageView ivImage;
    ArrayList<Answer> lstAnswer;
    RecyclerView rvAnswer;
    AnswerAdapter answerAdapter;
    long time;
    long timeStart;
    int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getQuestions();
        lstAnswer = new ArrayList<>();

        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        ivImage = findViewById(R.id.ivImage);
        rvAnswer = findViewById(R.id.rvAnswer);
        cTime = findViewById(R.id.cTime);
        cTime.start();
        timeStart = System.currentTimeMillis();

        getCurrentQuestion(currentQuestion);
    }

    private void getQuestions() {
        try {
            AppHelper.questionData = AppHelper.gameData.getJSONObject("package").getJSONArray("questions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getCurrentQuestion(int index) {
        try {
            lstAnswer = new ArrayList<>();
            rvAnswer.setVisibility(View.INVISIBLE);

            JSONObject obj = (JSONObject) AppHelper.questionData.get(index);
            Question q = new Question(
                    obj.getString("_id"),
                    obj.getString("level"),
                    obj.getString("content"),
                    obj.getString("image"),
                    obj.getString("base64Image"),
                    obj.getJSONArray("answers")
            );
            tvQuestion.setText(q.getContent());
            if (q.getImage().length() > 0) {
                byte[] decodedString = Base64.decode(q.getBase64Image(), Base64.NO_WRAP);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivImage.setImageBitmap(decodedByte);
            } else {
                ivImage.setVisibility(View.GONE);
            }

            for (int i = 0; i < q.getAnswer().length(); i++) {
                JSONObject answerObj = q.getAnswer().getJSONObject(i);
                lstAnswer.add(new Answer(
                        answerObj.getString("content"),
                        answerObj.getBoolean("correct")
                ));
            }

            answerAdapter = new AnswerAdapter(GameActivity.this, lstAnswer);
            LinearLayoutManager layoutManager = new LinearLayoutManager(GameActivity.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvAnswer.setLayoutManager(layoutManager);
            final Handler handler = new Handler();
            rvAnswer.setAdapter(answerAdapter);
            answerAdapter.notifyDataSetChanged();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rvAnswer.setVisibility(View.VISIBLE);
                }
            }, 1500);
            answerAdapter.setOnItemClickedListener(GameActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(final int position, final Answer answer, final boolean correct) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (correct) {
                    points++;
                    tvScore.setText(points + "");
                }
                saveResultForQuestion(answer, correct);
                currentQuestion++;
                if (currentQuestion < AppHelper.maxQuestion) {
                    getCurrentQuestion(currentQuestion);
                } else {
                    time = System.currentTimeMillis() - timeStart;
                    saveResultForGame();
                    Intent i = new Intent(GameActivity.this, StatActivity.class);
                    startActivity(i);
                }
            }
        }, 1500);
    }

    private void saveResultForQuestion(Answer answer, boolean correct) {
        try {
            JSONObject result = new JSONObject();
            result.put("choose", answer.getContent());
            result.put("result", correct);
            if (AppHelper.isChallenge()) {
                AppHelper.questionData.getJSONObject(currentQuestion).put("player1", result);
            } else {
                AppHelper.questionData.getJSONObject(currentQuestion).put("player2", result);
            }
            Log.d("mmm", AppHelper.questionData.getJSONObject(currentQuestion).getJSONObject("player1").getString("choose")+ "c");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveResultForGame() {
        JSONObject result = new JSONObject();
        JSONObject details = new JSONObject();
        try {
            details.put("points", points);
            details.put("time", time);
            if (AppHelper.isChallenge()) {
                result.put("player1", details);
                AppHelper.gameData.put("idUser1", AppHelper.userEmail);
            } else {
                result.put("player2", details);
                AppHelper.gameData.put("idUser2", AppHelper.userEmail);
            }
            AppHelper.gameData.put("result", result);
            Log.d("uuu", AppHelper.gameData.getJSONObject("result").toString() + "c");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

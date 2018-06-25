package com.example.administrator.quizapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.quizapp.lib.AppHelper;
import com.example.administrator.quizapp.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameActivity extends AppCompatActivity {

    Chronometer cTime;
    int currentQuestion = 0;
    TextView tvQuestion, tvScore;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getQuestions();

        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        ivImage = findViewById(R.id.ivImage);
        cTime = findViewById(R.id.cTime);
        cTime.start();

        getCurrentQuestion(currentQuestion);
    }

    private void getQuestions() {
        try {
            AppHelper.questionData = AppHelper.gameData.getJSONObject("package").getJSONArray("questions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCurrentQuestion(int index) {
        try {
            JSONObject obj = (JSONObject) AppHelper.questionData.get(index);
            Question q = new Question(
                    obj.getString("_id"),
                    obj.getString("level"),
                    obj.getString("content"),
                    obj.getString("image"),
                    obj.getString("base64Image")
            );
            tvQuestion.setText(q.getContent());
            if (q.getImage().length() > 0) {
                byte[] decodedString = Base64.decode(q.getBase64Image(), Base64.NO_WRAP);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivImage.setImageBitmap(decodedByte);
            } else {
                ivImage.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

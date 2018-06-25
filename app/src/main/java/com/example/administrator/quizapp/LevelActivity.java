package com.example.administrator.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.quizapp.lib.AppHelper;

public class LevelActivity extends AppCompatActivity {

    TextView tvGameType, tvTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        tvGameType = findViewById(R.id.tvGameType);
        tvTopic = findViewById(R.id.tvTopic);

        tvGameType.setText(AppHelper.gameType);
        tvTopic.setText(AppHelper.choosenTopic.getName());
    }
}

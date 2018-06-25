package com.example.administrator.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.quizapp.lib.AppHelper;

public class HomeActivity extends AppCompatActivity {

    TextView tvName;
    Button btnGame, btnChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvName = findViewById(R.id.tvName);
        btnChallenge = findViewById(R.id.btnChallenge);
        btnGame = findViewById(R.id.btnGame);

        btnChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, TopicActivity.class);
                AppHelper.gameType = getString(R.string.challenge);
                startActivity(i);
            }
        });

        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, TopicActivity.class);
                AppHelper.gameType = getString(R.string.normal);
                startActivity(i);
            }
        });
    }
}

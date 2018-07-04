package com.example.administrator.quizapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.quizapp.lib.ApiHelper;
import com.example.administrator.quizapp.lib.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadingActivity extends AppCompatActivity {

    CountDownTimer cd;
    boolean wait = false;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        btnCancel = findViewById(R.id.btnCancel);

        cd = new CountDownTimer(10000, 3000) {

            public void onTick(long millisUntilFinished) {
                if (wait) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (AppHelper.isChallenge()) {
                                new createChallenge().execute(ApiHelper.SERVER + ApiHelper.CHALLENGES);
                            } else {
                                new findGame().execute(ApiHelper.SERVER + ApiHelper.GAMES + "/find");
                            }
                        }
                    });
                }
                wait = true;
            }

            public void onFinish() {
                cd.cancel();
                Toast.makeText(LoadingActivity.this, "Cannot find any game.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoadingActivity.this, HomeActivity.class);
                startActivity(i);
            }
        }.start();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoadingActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }

    private class findGame extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject obj = new JSONObject();
                JSONObject data = new JSONObject();
                data.put("level", AppHelper.choosenLevel);
                data.put("topic", AppHelper.choosenTopic);
                data.put("userId", AppHelper.userEmail);
                obj.put("mobile", true);
                obj.put("data", data);
                URL url = new URL(strings[0]);
                return ApiHelper.POST_URL(url, obj);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("ppp", s);
            if (!s.equalsIgnoreCase("")) {
                try {
                    AppHelper.gameData = new JSONObject(s);
                    Intent i = new Intent(LoadingActivity.this, GameActivity.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class createChallenge extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject obj = new JSONObject();
                JSONObject data = new JSONObject();
                data.put("level", AppHelper.choosenLevel);
                data.put("topic", AppHelper.choosenTopic);
                obj.put("mobile", true);
                obj.put("data", data);
                URL url = new URL(strings[0]);
                return ApiHelper.POST_URL(url, obj);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ggg", s + "cs");
            if (!s.equalsIgnoreCase("")) {
                try {
                    AppHelper.gameData = new JSONObject(s);
                    Intent i = new Intent(LoadingActivity.this, GameActivity.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
        cd.cancel();
    }
}

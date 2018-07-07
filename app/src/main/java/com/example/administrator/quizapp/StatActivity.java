package com.example.administrator.quizapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.quizapp.lib.ApiHelper;
import com.example.administrator.quizapp.lib.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class StatActivity extends AppCompatActivity {

    TextView tvResult, tvName1, tvName2, tvPoint1, tvPoint2, tvTime1, tvTime2;
    Button btnHome, btnReview;
    boolean isFromHistory;
    boolean isUser1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        tvName1 = findViewById(R.id.tvName1);
        tvName2 = findViewById(R.id.tvName2);
        tvPoint1 = findViewById(R.id.tvPoint1);
        tvPoint2 = findViewById(R.id.tvPoint2);
        tvTime1 = findViewById(R.id.tvTime1);
        tvTime2 = findViewById(R.id.tvTime2);
        tvResult = findViewById(R.id.tvResult);
        btnHome = findViewById(R.id.btnHome);
        btnReview = findViewById(R.id.btnReview);

        try {
            isUser1 = AppHelper.userEmail.equals(AppHelper.gameData.getString("idUser1")) ? true : false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle extras = getIntent().getExtras();
        isFromHistory = extras != null ? true : false;
        if (!isFromHistory) {
            displayStat();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (AppHelper.isChallenge()) {
                        new saveChallenge().execute(ApiHelper.SERVER + ApiHelper.CHALLENGES + "/save");
                    } else {
                        new saveGame().execute(ApiHelper.SERVER + ApiHelper.GAMES);
                    }
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        AppHelper.gameType = getString(R.string.normal);
                        AppHelper.questionData = AppHelper.gameData.getJSONObject("package").getJSONArray("questions");
                        new getListImage().execute(ApiHelper.SERVER + ApiHelper.QUESTIONS + "/list_image");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            displayStatFromHistory();
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatActivity.this, ReviewActivity.class);
                startActivity(i);
            }
        });
    }

    private void displayStatFromHistory() {
        try {
            String idUser1 = AppHelper.gameData.getString("idUser1");
            String idUser2 = AppHelper.gameData.getString("idUser2");
            JSONObject result = AppHelper.gameData.getJSONObject("result");
            int point1 = Integer.parseInt(result.getJSONObject("player1").getString("points"));
            int point2 = Integer.parseInt(result.getJSONObject("player2").getString("points"));
            int time1 = Integer.parseInt(result.getJSONObject("player1").getString("time"));
            int time2 = Integer.parseInt(result.getJSONObject("player2").getString("time"));
            if (isUser1) {
                if (idUser1.equalsIgnoreCase(result.getString("winner"))) {

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new getUserInGame().execute(ApiHelper.SERVER + ApiHelper.USERS + "/get_by_email", "idUser2");
                        tvName1.setText(AppHelper.userName);
                    }
                });
                tvPoint1.setText(point1 + "");
                tvPoint2.setText(point2 + "");
                tvTime1.setText(secondsToString(time1));
                tvTime2.setText(secondsToString(time2));
                if (point1 > point2) {
                    tvResult.setText("You won!");
                } else if (point1 < point2) {
                    tvResult.setText("You lost!");
                } else {
                    if (time1/1000 < time2/1000) {
                        tvResult.setText("You won!");
                    } else if (time1/1000 > time2/1000) {
                        tvResult.setText("You lost!");
                    } else {
                        tvResult.setText("Game draw");
                    }
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new getUserInGame().execute(ApiHelper.SERVER + ApiHelper.USERS + "/get_by_email", "idUser1");
                        tvName1.setText(AppHelper.userName);
                    }
                });
                tvPoint1.setText(point2 + "");
                tvPoint2.setText(point1 + "");
                tvTime1.setText(secondsToString(time2));
                tvTime2.setText(secondsToString(time1));
                if (point1 > point2) {
                    tvResult.setText("You lost!");
                } else if (point1 < point2) {
                    tvResult.setText("You won!");
                } else {
                    if (time1/1000 < time2/1000) {
                        tvResult.setText("You lost!");
                    } else if (time1/1000 > time2/1000) {
                        tvResult.setText("You won!");
                    } else {
                        tvResult.setText("Game draw");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayStat() {
        try {
            JSONObject result = AppHelper.gameData.getJSONObject("result");
            if (AppHelper.isChallenge()) {
                tvName1.setText(AppHelper.userName);
                tvPoint1.setText(result.getJSONObject("player1").getString("points"));
                tvTime1.setText(secondsToString(result.getJSONObject("player1").getInt("time")));
            } else {
                int point1 = Integer.parseInt(result.getJSONObject("player2").getString("points"));
                int point2 = Integer.parseInt(result.getJSONObject("player1").getString("points"));
                tvName1.setText(AppHelper.userName);
                tvPoint1.setText(result.getJSONObject("player2").getString("points"));
                tvTime1.setText(secondsToString(result.getJSONObject("player2").getInt("time")));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new getUserInGame().execute(ApiHelper.SERVER + ApiHelper.USERS + "/get_by_email", "idUser1");
                    }
                });
                tvPoint2.setText(result.getJSONObject("player1").getString("points"));
                tvTime2.setText(secondsToString(result.getJSONObject("player1").getInt("time")));

                if (point1 > point2) {
                    tvResult.setText("You won!");
                    AppHelper.gameData.getJSONObject("result").put("winner", AppHelper.gameData.getString("idUser2"));
                } else if (point1 < point2) {
                    tvResult.setText("You lost!");
                    AppHelper.gameData.getJSONObject("result").put("winner", AppHelper.gameData.getString("idUser1"));
                } else {
                    int time1 = Integer.parseInt(result.getJSONObject("player2").getString("time"));
                    int time2 = Integer.parseInt(result.getJSONObject("player1").getString("time"));
                    if (time1/1000 < time2/1000) {
                        tvResult.setText("You won!");
                        AppHelper.gameData.getJSONObject("result").put("winner", AppHelper.gameData.getString("idUser2"));
                    } else if (time1/1000 > time2/1000) {
                        tvResult.setText("You lost!");
                        AppHelper.gameData.getJSONObject("result").put("winner", AppHelper.gameData.getString("idUser1"));
                    } else {
                        tvResult.setText("Game draw");
                        AppHelper.gameData.getJSONObject("result").put("winner", "");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class saveGame extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                JSONObject obj = new JSONObject();
                obj.put("data", AppHelper.gameData);
                obj.put("mobile", true);

                return ApiHelper.POST_URL(url, obj);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class saveChallenge extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                JSONObject obj = new JSONObject();
                obj.put("data", AppHelper.gameData);
                obj.put("mobile", true);

                return ApiHelper.POST_URL(url, obj);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
        }
    }

    private class getUserInGame extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                JSONObject obj = new JSONObject();
                obj.put("email", AppHelper.gameData.getString(strings[1]));

                return ApiHelper.POST_URL(url, obj);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject obj = new JSONObject(s);

                Log.d("iii", obj.getString("name"));
                tvName2.setText(obj.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getListImage extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                String arrImage = "";
                for (int i = 0; i < AppHelper.maxQuestion; i++) {
                    String imagePath = AppHelper.gameData.getJSONObject("package").getJSONArray("questions").getJSONObject(i).getString("image");
                    if (!imagePath.equalsIgnoreCase("")) {
                        arrImage += imagePath + ",";
                    }
                }
                Log.d("eee", arrImage);
                JSONObject obj = new JSONObject();
                obj.put("data", arrImage);

                return ApiHelper.POST_URL(url, obj);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                return;
            }
            String[] arrImage = s.split(",");
            for (int i = 0; i < arrImage.length; i++) {
                if (!arrImage[i].equalsIgnoreCase("")) {
                    try {
                        AppHelper.questionData.getJSONObject(i).put("base64Image", arrImage[i]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String secondsToString(int pTime) {
        pTime /= 1000;
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }
}

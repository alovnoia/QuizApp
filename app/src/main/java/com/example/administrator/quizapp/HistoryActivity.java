package com.example.administrator.quizapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.quizapp.adapter.HistoryAdapter;
import com.example.administrator.quizapp.lib.ApiHelper;
import com.example.administrator.quizapp.lib.AppHelper;
import com.example.administrator.quizapp.model.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rvHistory;
    ArrayList<Game> lstGame;
    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rvHistory);
        lstGame = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getGameByUser().execute(ApiHelper.SERVER + ApiHelper.GAMES + "/review");
            }
        });
    }

    private class getGameByUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                JSONObject obj = new JSONObject();
                obj.put("user", AppHelper.userEmail);

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
            Log.d("uuu", s + "cs");
            try {
                JSONArray arr = new JSONArray(s);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    lstGame.add(new Game(
                            obj.getString("gameType"),
                            obj.getString("idUser1"),
                            obj.getString("idUser2"),
                            obj.getJSONObject("result"),
                            obj.getJSONObject("package")
                    ));
                }

                adapter = new HistoryAdapter(HistoryActivity.this, lstGame);

                AppHelper.recyclerViewHelper(HistoryActivity.this, rvHistory);
                rvHistory.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

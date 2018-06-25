package com.example.administrator.quizapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.quizapp.adapter.TopicAdapter;
import com.example.administrator.quizapp.lib.ApiHelper;
import com.example.administrator.quizapp.lib.AppHelper;
import com.example.administrator.quizapp.model.Topic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {

    TextView tvGameType;
    TopicAdapter adapter;
    ArrayList<Topic> lstTopic;
    RecyclerView rvTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        tvGameType = findViewById(R.id.tvType);
        rvTopic = findViewById(R.id.rvTopic);

        lstTopic = new ArrayList<>();

        if (AppHelper.isChallenge()) {
            tvGameType.setText(getString(R.string.up_challenge));
        } else {
            tvGameType.setText(getString(R.string.up_normal));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("ttt", ApiHelper.SERVER + ApiHelper.TOPIC);
                new getListTopic().execute(ApiHelper.SERVER + ApiHelper.TOPIC);
            }
        });
    }

    private class getListTopic extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                return ApiHelper.GET_URL(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ttt", s + "s");
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    if (obj.getBoolean("status")) {
                        lstTopic.add(new Topic(
                                obj.getString("_id"),
                                obj.getString("name"),
                                obj.getString("desc"),
                                obj.getBoolean("status")
                        ));
                    }
                }

                adapter = new TopicAdapter(TopicActivity.this, lstTopic);

                LinearLayoutManager layoutManager = new LinearLayoutManager(TopicActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTopic.getContext(),
                        layoutManager.getOrientation());
                rvTopic.addItemDecoration(dividerItemDecoration);

                rvTopic.setLayoutManager(layoutManager);
                rvTopic.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

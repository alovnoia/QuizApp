package com.example.administrator.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.quizapp.lib.AppHelper;

import org.json.JSONException;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity {

    TextView tvGameType, tvTopic;
    ListView lvLevel;
    ArrayAdapter<String> adapterLevel;
    ArrayList<String> lstLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        tvGameType = findViewById(R.id.tvGameType);
        tvTopic = findViewById(R.id.tvTopic);
        lvLevel = findViewById(R.id.lvLevel);

        tvGameType.setText(AppHelper.gameType);
        try {
            tvTopic.setText(AppHelper.choosenTopic.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lstLevel = new ArrayList<>();
        lstLevel.add(AppHelper.LEVEL_EASY);
        lstLevel.add(AppHelper.LEVEL_MEDIUM);
        lstLevel.add(AppHelper.LEVEL_HARD);

        adapterLevel = new ArrayAdapter<>(LevelActivity.this, android.R.layout.simple_list_item_1, lstLevel);

        lvLevel.setAdapter(adapterLevel);
        adapterLevel.notifyDataSetChanged();

        lvLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppHelper.choosenLevel = adapterLevel.getItem(i);
                Intent intent = new Intent(LevelActivity.this, LoadingActivity.class);
                startActivity(intent);
            }
        });
    }
}

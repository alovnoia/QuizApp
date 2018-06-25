package com.example.administrator.quizapp.lib;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.quizapp.TopicActivity;
import com.example.administrator.quizapp.model.Question;
import com.example.administrator.quizapp.model.Topic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppHelper {

    public static String gameType = null;
    public static JSONObject choosenTopic;
    public static String LEVEL_EASY = "easy";
    public static String LEVEL_MEDIUM = "medium";
    public static String LEVEL_HARD = "hard";
    public static String choosenLevel;
    public static JSONObject gameData;
    public static int maxQuestion = 5;
    public static JSONArray questionData;

    public static boolean isChallenge() {
        return gameType.equals("challenge");
    }

    public static void recyclerViewHelper (Context context, RecyclerView rv) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                layoutManager.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        rv.setLayoutManager(layoutManager);
    }

}

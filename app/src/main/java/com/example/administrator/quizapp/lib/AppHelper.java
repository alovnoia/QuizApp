package com.example.administrator.quizapp.lib;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

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
    public static String userEmail;
    public static String userName;

    public static void saveUser(String email) {
        userEmail = email + "@gmail.com";
    }

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

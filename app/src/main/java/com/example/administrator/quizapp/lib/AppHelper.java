package com.example.administrator.quizapp.lib;

import com.example.administrator.quizapp.model.Topic;

public class AppHelper {

    public static String gameType = null;
    public static Topic choosenTopic;

    public static boolean isChallenge() {
        return gameType.equals("challenge");
    }

}

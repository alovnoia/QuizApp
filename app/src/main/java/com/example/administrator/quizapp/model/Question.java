package com.example.administrator.quizapp.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Question {

    String id, level, content, image, code, base64Image;
    Topic topic;
    JSONObject player1, player2;
    JSONArray answer;

    public Question(String id, String level, String content, String image, String base64Image, JSONObject player1, JSONArray answer) {
        this.id = id;
        this.level = level;
        this.content = content;
        this.image = image;
        this.base64Image = base64Image;
        this.player1 = player1;
        this.answer = answer;
    }

    public Question(String id, String level, String content, String image, String base64Image, JSONObject player1, JSONObject player2, JSONArray answer) {
        this.id = id;
        this.level = level;
        this.content = content;
        this.image = image;
        this.base64Image = base64Image;
        this.player1 = player1;
        this.player2 = player2;
        this.answer = answer;
    }

    public Question(String id, String level, String content, String image, String base64Image, JSONArray answer) {
        this.id = id;
        this.level = level;
        this.content = content;
        this.image = image;
        this.base64Image = base64Image;
        this.answer = answer;
    }

    public Question(String id, String level, String content, String image, String base64Image) {
        this.id = id;
        this.level = level;
        this.content = content;
        this.image = image;
        this.base64Image = base64Image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public JSONObject getPlayer1() {
        return player1;
    }

    public void setPlayer1(JSONObject player1) {
        this.player1 = player1;
    }

    public JSONObject getPlayer2() {
        return player2;
    }

    public void setPlayer2(JSONObject player2) {
        this.player2 = player2;
    }

    public JSONArray getAnswer() {
        return answer;
    }

    public void setAnswer(JSONArray answer) {
        this.answer = answer;
    }
}

package com.example.administrator.quizapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Game {

    String gameType, idUser1, idUser2;
    JSONObject result, pack;

    public Game(String gameType, String idUser1, String idUser2, JSONObject result, JSONObject pack) {
        this.gameType = gameType;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.result = result;
        this.pack = pack;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public JSONObject getPack() {
        return pack;
    }

    public void setPack(JSONObject pack) {
        this.pack = pack;
    }

    public JSONObject gameToJsonObject () {
        JSONObject obj = new JSONObject();
        try {
            obj.put("gameType", this.gameType);
            obj.put("idUser1", this.idUser1);
            obj.put("idUser2", this.idUser2);
            obj.put("result", this.result);
            obj.put("package", this.pack);

            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

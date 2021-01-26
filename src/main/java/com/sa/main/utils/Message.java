package com.sa.main.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Message {

    private static JsonObject JSON;

    public Message(String jsonstr) {
        JsonParser parser = new JsonParser();
        JSON = (JsonObject) parser.parse(jsonstr);
    }

    public String getAuthor() {
        return JSON.get("author").getAsString();
    }

    public String getMsg() {
        return JSON.get("msg").getAsString();
    }

    public String getIp() {
        return JSON.get("ip").getAsString();
    }
}

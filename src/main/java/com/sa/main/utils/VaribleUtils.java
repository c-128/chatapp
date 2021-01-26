package com.sa.main.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VaribleUtils {

    public static boolean isInt(String i) {
        try {
            Integer.parseInt(i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isJSON(String i) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject obj = (JsonObject) parser.parse(i);
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }
}

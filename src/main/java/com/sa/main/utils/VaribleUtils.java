package com.sa.main.utils;

import java.io.IOException;

public class VaribleUtils {

    public static boolean isInt(String i) {
        try {
            Integer.parseInt(i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

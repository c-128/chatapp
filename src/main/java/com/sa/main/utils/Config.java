package com.sa.main.utils;

import java.io.*;
import java.util.Properties;

public class Config {

    private static Properties p;
    private static String file;

    public static void init() throws IOException {
        p = new Properties();
        file = "app.config";
        InputStream is;
        File f = new File(file);

        if (!f.exists()) {
            f.createNewFile();
            p.setProperty("server.port", "90");
            p.setProperty("client.logged_in", "false");
            p.setProperty("client.ip", "null");
            p.setProperty("client.port", "null");
            p.setProperty("client.usr", "null");
        }

        is = new FileInputStream(file);
        p.load(is);

        save();
    }

    public static Properties getConfig() {
        return p;
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(p.getProperty(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(p.getProperty(key));
    }

    public static String getString(String key) {
        return p.getProperty(key);
    }

    public static void setString(String v, String k) {
        p.setProperty(v, k);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() throws IOException {
        OutputStream o = new FileOutputStream(file);
        p.save(o, "This is the config for the chatapp");
    }
}

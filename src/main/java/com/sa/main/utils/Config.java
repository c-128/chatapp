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
            p.setProperty("server.ip", "localhost");
        }

        is = new FileInputStream(file);
        p.load(is);

        save();
    }

    public static Properties getConfig() {
        return p;
    }

    public static void save() throws IOException {
        OutputStream o = new FileOutputStream(file);
        p.save(o, "This is the config for the chatapp");
    }
}

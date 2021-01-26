package com.sa.client;

import com.sa.client.ui.ClientLoginUI;
import com.sa.client.ui.ClientUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    public static int PORT = 0;
    public static String IP = "";
    public static String USR = "lol";

    public static void start() throws IOException {

        ClientLoginUI.init();
        ClientLoginUI.open();
    }
}

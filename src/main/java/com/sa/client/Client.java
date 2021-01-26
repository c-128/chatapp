package com.sa.client;

import com.sa.client.ui.ClientUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    public static String USR = "";

    public static void start() throws IOException {
        System.out.print("Username: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        USR = in.readLine();

        ClientSocket.init();

        ClientUI.init();
        ClientUI.open();
    }
}

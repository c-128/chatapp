package com.sa.client;


import com.sa.client.ui.ClientUI;

import java.io.*;
import java.net.Socket;

public class ClientSocket {

    public static Socket s;

    public static void init() throws IOException {
        s = new Socket("localhost", 90);

        DataInputStream in = new DataInputStream(s.getInputStream());

        Thread msgt = new Thread(() -> {
            while (true) {
                try {
                    String msg = in.readUTF();
                    ClientUI.msgs.setText(ClientUI.msgs.getText() + "<p>" + msg + "</p>");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        msgt.start();
    }
}

package com.sa.client;


import com.sa.client.ui.ClientUI;

import java.io.*;
import java.net.Socket;

public class ClientSocket {

    public static Socket s;

    public static void init() throws IOException {
        s = new Socket(Client.IP, Client.PORT);

        Thread msgt = new Thread(() -> {
            try {
                DataInputStream in = new DataInputStream(s.getInputStream());

                while (!s.isClosed()) {
                    String msg = in.readUTF();
                    ClientUI.msgs.setText(ClientUI.msgs.getText() + "<p>" + msg + "</p>");
                }

                System.out.println("Closing inputstream");
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        msgt.start();
    }
}

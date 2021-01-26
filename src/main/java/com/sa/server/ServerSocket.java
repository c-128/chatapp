package com.sa.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sa.main.utils.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerSocket {

    public static void init() throws IOException {
        java.net.ServerSocket s = new java.net.ServerSocket(90);

        ArrayList<Socket> clients = new ArrayList<>();

        while (true) {
            Socket client = s.accept();
            clients.add(client);
            Thread t = new Thread(() -> {
                try {
                    DataInputStream in = new DataInputStream(client.getInputStream());

                    String data = "";
                    while (!data.equalsIgnoreCase("exit")) {
                        data = in.readUTF();
                        for (Socket c : clients) {
                            //if (!c.getInetAddress().getHostAddress().equalsIgnoreCase(client.getInetAddress().getHostAddress())) {
                                Message msg = new Message(data);
                                new DataOutputStream(c.getOutputStream()).writeUTF(msg.getAuthor() + ": " + msg.getMsg());
                            //}
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}

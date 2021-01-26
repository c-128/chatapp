package com.sa.server;

import com.sa.main.utils.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
                    while (!data.equalsIgnoreCase("end")) {
                        data = in.readUTF();
                        for (Socket c : clients) {
                            Message msg = new Message(data);
                            new DataOutputStream(c.getOutputStream()).writeUTF(msg.getAuthor() + ": " + msg.getMsg());
                        }
                    }

                    in.close();
                    clients.remove(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}

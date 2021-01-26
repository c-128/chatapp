package com.sa.client;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sa.client.ui.ClientUI;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocket {

    public static Socket s;
    public static ArrayList<String> ONLINE = new ArrayList<>();

    public static void init() throws IOException {
        s = new Socket(Client.IP, Client.PORT);

        Thread msgt = new Thread(() -> {
            try {
                DataInputStream in = new DataInputStream(s.getInputStream());

                JsonObject login = new JsonObject();
                login.addProperty("task", "login");
                login.addProperty("author", Client.USR);
                new DataOutputStream(s.getOutputStream()).writeUTF(login.toString());

                while (!s.isClosed()) {
                    String msg = in.readUTF();
                    JsonParser parser = new JsonParser();
                    JsonObject json = (JsonObject) parser.parse(msg);

                    String task = json.get("task").getAsString();

                    if (task.equalsIgnoreCase("msg")) {
                        ClientUI.msgs.setText(ClientUI.msgs.getText() + "<p>" + json.get("author").getAsString() + ": " + json.get("msg").getAsString() + "</p>");
                    } else if (task.equalsIgnoreCase("online-memb")) {
                        ONLINE.clear();
                        JsonArray online = json.getAsJsonArray("online");
                        for (int i = 0; i < online.size(); i++) {
                            ONLINE.add(online.get(i).getAsString());
                        }
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        msgt.start();
    }
}

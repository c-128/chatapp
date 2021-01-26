package com.sa.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sa.client.Client;
import com.sa.client.ClientSocket;
import com.sa.main.utils.Message;
import com.sa.main.utils.VaribleUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerSocket {

    public static void init() throws IOException {
        java.net.ServerSocket s = new java.net.ServerSocket(90);

        ArrayList<Socket> clients = new ArrayList<>();
        HashMap<InetAddress, String> usrs = new HashMap<>();
        System.out.println("Server ready!");

        while (true) {
            Socket client = s.accept();
            clients.add(client);

            DataInputStream in = new DataInputStream(client.getInputStream());
            String firstmsg = in.readUTF();
            JsonParser parser = new JsonParser();
            JsonObject firstjson = (JsonObject) parser.parse(firstmsg);

            usrs.put(client.getInetAddress(), firstjson.get("author").getAsString());

            System.out.println("New connection from " + client.getInetAddress().getHostAddress());
            Thread t = new Thread(() -> {
                try {

                    String data = "";
                    while (!data.equalsIgnoreCase("end")) {
                        data = in.readUTF();
                        if (VaribleUtils.isJSON(data)) {
                            JsonObject json = (JsonObject) parser.parse(data);

                            String task = json.get("task").getAsString();

                            if (task.equalsIgnoreCase("msg")) {
                                Message msg = new Message(data);
                                for (Socket c : clients) {
                                    JsonObject jsonobj = new JsonObject();
                                    jsonobj.addProperty("task", "msg");
                                    jsonobj.addProperty("author", msg.getAuthor());
                                    jsonobj.addProperty("msg", msg.getMsg());

                                    new DataOutputStream(c.getOutputStream()).writeUTF(jsonobj.toString());
                                }
                                System.out.println(msg.getAuthor() + ": " + msg.getMsg());
                            } else if (task.equalsIgnoreCase("online-memb")) {
                                JsonObject jsonobj = new JsonObject();
                                jsonobj.addProperty("task", "online-memb");
                                JsonArray online = new JsonArray();
                                for (String name : usrs.values()) {
                                    online.add(name);
                                }
                                jsonobj.add("online", online);
                                new DataOutputStream(client.getOutputStream()).writeUTF(jsonobj.toString());
                            }
                        }
                    }

                    in.close();
                    usrs.remove(client.getInetAddress());
                    clients.remove(client);
                    System.out.println("Connection closed from " + client.getInetAddress().getHostAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}

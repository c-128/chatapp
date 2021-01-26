package com.sa.client;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;

public class ClientSocket {

    public static void init() throws IOException {
        Socket s = new Socket("localhost", 90);

        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());

        Thread readt = new Thread(() -> {
            try {
                String data = "";
                while (!data.equalsIgnoreCase("end")) {
                    data = cin.readLine();

                    JsonObject json = new JsonObject();
                    json.addProperty("author", Client.USR);
                    json.addProperty("msg", data);
                    json.addProperty("ip", s.getInetAddress().getHostAddress());
                    json.addProperty("usr", Client.USR);
                    out.writeUTF(json.toString());
                }

                s.close();
                cin.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readt.start();

        Thread msgt = new Thread(() -> {
            while (true) {
                try {
                    String msg = in.readUTF();
                    System.out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        msgt.start();
    }
}

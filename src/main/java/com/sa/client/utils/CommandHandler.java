package com.sa.client.utils;

import com.sa.client.ClientSocket;

import java.io.DataOutputStream;
import java.io.IOException;

public class CommandHandler {

    public static void handle(String cmd) throws IOException {
        if (cmd.startsWith("!")) {
            String[] args = cmd.substring(1).split(" ");

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("exit")) {
                    DataOutputStream out = new DataOutputStream(ClientSocket.s.getOutputStream());
                    out.writeUTF("end");
                    System.exit(0);
                }
            }
        }
    }
}

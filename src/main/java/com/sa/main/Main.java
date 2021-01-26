package com.sa.main;

import com.sa.client.Client;
import com.sa.server.Server;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] launchargs) throws IOException {
        List<String> args = Arrays.asList(launchargs);

        if (args.contains("server")) {
            Server.start();
        } else {
            Client.start();
        }
    }
}

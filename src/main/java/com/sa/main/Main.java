package com.sa.main;

import com.sa.client.Client;
import com.sa.main.utils.Config;
import com.sa.server.Server;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] launchargs) throws Exception {
        List<String> args = Arrays.asList(launchargs);
        Config.init();

        if (args.contains("server")) {
            Server.start();
        } else {
            Client.start();
        }
    }
}

package com.sa.server;

import java.io.IOException;

public class Server {

    public static void start() throws IOException {
        ServerSocket.init();
        System.out.println("Server ready!");
    }
}

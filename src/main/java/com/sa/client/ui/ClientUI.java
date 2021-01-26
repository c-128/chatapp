package com.sa.client.ui;

import com.google.gson.JsonObject;
import com.sa.client.Client;
import com.sa.client.ClientSocket;
import com.sa.client.utils.CommandHandler;

import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientUI {

    private static JFrame f;
    public static JLabel msgs;

    public static void init() throws IOException {
        f = new JFrame();
        f.setSize(new Dimension(520, 400));

        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));
        p.setLayout(null);

        msgs = new JLabel("<html><body>");
        JScrollPane msgscroll = new JScrollPane(msgs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        msgscroll.setBounds(10, 10, 480, 300);
        p.add(msgscroll);

        JTextField msg = new JTextField();
        msg.setBounds(10, 310, 410, 25);
        p.add(msg);

        JButton sendmsg = new JButton("->");
        sendmsg.setBounds(420, 310, 70, 25);
        DataOutputStream out = new DataOutputStream(ClientSocket.s.getOutputStream());
        sendmsg.addActionListener(e -> {
            try {
                CommandHandler.handle(msg.getText());
                JsonObject json = new JsonObject();
                json.addProperty("author", Client.USR);
                json.addProperty("msg", msg.getText());
                json.addProperty("ip", ClientSocket.s.getInetAddress().getHostAddress());
                json.addProperty("usr", Client.USR);

                out.writeUTF(json.toString());
                msg.setText(null);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        p.add(sendmsg);

        f.add(p, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("ChatApp");
    }

    public static void open() {
        f.setVisible(true);
    }

    public static void close() {
        f.setVisible(false);
    }
}

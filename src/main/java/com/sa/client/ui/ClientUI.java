package com.sa.client.ui;

import com.google.gson.JsonObject;
import com.sa.client.Client;
import com.sa.client.ClientSocket;
import com.sa.client.utils.CommandHandler;
import com.sa.main.utils.Config;

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
        DataOutputStream out = new DataOutputStream(ClientSocket.s.getOutputStream());

        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));
        p.setLayout(null);

        JButton logout = new JButton("LOGOUT");
        logout.setBounds(10, 10, 60, 25);
        logout.addActionListener(e -> {
            close();
            Config.setString("client.logged_in", "false");
            Config.setString("client.port", "null");
            Config.setString("client.usr", "null");
            Config.setString("client.ip", "null");
            try {
                CommandHandler.handle("!exit");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        p.add(logout);

        JButton members = new JButton("ON");
        members.setBounds(70, 10, 360, 25);
        members.addActionListener(e -> {
            try {
                JsonObject json = new JsonObject();
                json.addProperty("task", "online-memb");

                out.writeUTF(json.toString());

                String on = "<html><body><p>Online members:</p>";
                for (String name : ClientSocket.ONLINE) {
                    on = on + "<p>" + name + "</p>";
                }
                on = on + "</body></html>";

                JOptionPane.showConfirmDialog(f, on, "ChatApp - Members", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception Exception) {
                Exception.printStackTrace();
            }
        });
        p.add(members);

        JButton exit = new JButton("X");
        exit.setBounds(430, 10, 50, 25);
        exit.addActionListener(e -> {
            try {
                CommandHandler.handle("!exit");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        p.add(exit);

        msgs = new JLabel("<html><body><p style='color: red;'>PLEASE EXIT WITH '!exit'</p>");
        JScrollPane msgscroll = new JScrollPane(msgs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        msgscroll.setBounds(10, 60, 480, 250);
        p.add(msgscroll);

        JTextField msg = new JTextField();
        msg.setBounds(10, 310, 410, 25);
        p.add(msg);

        JButton sendmsg = new JButton("->");
        sendmsg.setBounds(420, 310, 75, 25);
        sendmsg.addActionListener(e -> {
            try {
                CommandHandler.handle(msg.getText());
                JsonObject json = new JsonObject();
                json.addProperty("task", "msg");
                json.addProperty("author", Client.USR);
                json.addProperty("msg", msg.getText());
                json.addProperty("ip", ClientSocket.s.getInetAddress().getHostAddress());
                json.addProperty("usr", Client.USR);

                out.writeUTF(json.toString());
                msg.setText(null);
            } catch (Exception Exception) {
                Exception.printStackTrace();
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

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
    private static boolean members_vis = false;

    public static JLabel msgs;

    public static void init() throws IOException {
        f = new JFrame();
        f.setSize(new Dimension(520, 400));
        f.setResizable(false);
        DataOutputStream out = new DataOutputStream(ClientSocket.s.getOutputStream());

        JPanel con = new JPanel();
        con.setLayout(new BoxLayout(con, BoxLayout.X_AXIS));

        JPanel opt = new JPanel();
        opt.setLayout(new BoxLayout(con, BoxLayout.X_AXIS));

        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));
        p.setLayout(null);

        JPanel op = new JPanel();
        op.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));
        op.setVisible(false);
        op.setLayout(null);

        JPanel optionsmenu = new JPanel();
        optionsmenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        optionsmenu.setLayout(new GridLayout(10, 0));

        JPanel generalopt = new JPanel();
        generalopt.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        generalopt.setVisible(false);
        generalopt.setLayout(null);

        JPanel layoutopt = new JPanel();
        layoutopt.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        layoutopt.setVisible(false);
        layoutopt.setLayout(null);

        JButton bruh = new JButton("Bruh");
        bruh.setBounds(140, 10, 100, 30);
        generalopt.add(bruh);

        JButton general = new JButton("General");
        general.setBounds(5, 10, 100, 30);
        general.addActionListener(e -> {
            generalopt.setVisible(!generalopt.isVisible());
            layoutopt.setVisible(false);
        });
        optionsmenu.add(general);

        JButton defaultlayout = new JButton("Default");
        defaultlayout.setBounds(140, 10, 100, 30);
        layoutopt.add(defaultlayout);

        JButton darklayout = new JButton("Dark");
        darklayout.setBounds(250, 10, 100, 30);
        layoutopt.add(darklayout);

        JButton layout = new JButton("Layout");
        layout.setBounds(5, 50, 100, 30);
        layout.addActionListener(e -> {
            layoutopt.setVisible(!layoutopt.isVisible());
            generalopt.setVisible(false);
        });
        optionsmenu.add(layout);

        JButton logout = new JButton("<html><p style='color: red;'>LOGOUT</p></html>");
        logout.setBounds(5, 400, 100, 30);
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
        optionsmenu.add(logout);

        JScrollPane optionscroll = new JScrollPane(optionsmenu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        optionscroll.setBounds(10, 10, 130, 340);
        op.add(optionscroll);

        JButton optionsex = new JButton("X");
        optionsex.setBounds(440, 10, 50, 50);
        optionsex.addActionListener(e -> {
            f.setTitle("ChatApp - Options");
            op.setVisible(false);
            p.setVisible(true);
        });
        op.add(optionsex);

        JPanel on = new JPanel();
        on.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));
        on.setVisible(members_vis);
        on.setLayout(null);

        JLabel ondes = new JLabel("Online Members:");
        ondes.setBounds(10, 10, 280, 25);
        on.add(ondes);

        JLabel online = new JLabel("<html><body><p>lol</p></html></body>");
        JScrollPane onscorll = new JScrollPane(online, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        onscorll.setBounds(10, 50, 230, 250);
        on.add(onscorll);

        JButton options = new JButton("Settings");
        options.setBounds(10, 10, 60, 25);
        options.addActionListener(e -> {
            f.setTitle("ChatApp");
            op.setVisible(true);
            p.setVisible(false);
            on.setVisible(false);
        });
        p.add(options);

        JButton members = new JButton("Memberlist");
        members.setBounds(70, 10, 360, 25);
        members.addActionListener(e -> {
            try {
                JsonObject json = new JsonObject();
                json.addProperty("task", "online-memb");
                out.writeUTF(json.toString());

                Thread.sleep(500);

                String membs = "<html><body><p>";

                for (String name : ClientSocket.ONLINE) {
                    membs = membs + name + "<br />";
                }
                membs = membs + "</p></body></html>";

                online.setText(membs);

                members_vis = !members_vis;
                on.setVisible(members_vis);
            } catch (IOException | InterruptedException ioException) {
                ioException.printStackTrace();
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

        op.setBounds(0, 0, 520, 400);
        p.setBounds(0, 0, 520, 400);
        on.setBounds(0, 220, 300, 400);

        layoutopt.setBounds(0, 0, 400, 400);
        generalopt.setBounds(0, 0, 400, 400);

        op.add(layoutopt);
        op.add(generalopt);

        con.add(p);
        con.add(op);
        con.add(on);

        f.add(con, BorderLayout.CENTER);
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

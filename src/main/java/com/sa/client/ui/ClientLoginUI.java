package com.sa.client.ui;

import com.sa.client.Client;
import com.sa.client.ClientSocket;
import com.sa.main.utils.Config;
import com.sa.main.utils.VaribleUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientLoginUI {

    private static JFrame f;

    public static void init() {
        f = new JFrame();
        f.setSize(new Dimension(520, 400));

        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));
        p.setLayout(null);

        JLabel iplabel = new JLabel("IP:");
        iplabel.setBounds(10, 10, 200, 25);
        p.add(iplabel);

        JTextField ip = new JTextField();
        ip.setBounds(220, 10, 270, 25);
        p.add(ip);

        JLabel portlabel = new JLabel("PORT:");
        portlabel.setBounds(10, 50, 200, 25);
        p.add(portlabel);

        JTextField port = new JTextField();
        port.setBounds(220, 50, 270, 25);
        p.add(port);

        JLabel usrlabel = new JLabel("Username:");
        usrlabel.setBounds(10, 90, 200, 25);
        p.add(usrlabel);

        JTextField usr = new JTextField();
        usr.setBounds(220, 90, 270, 25);
        p.add(usr);

        JCheckBox save = new JCheckBox("Keep me logged in");
        save.setBounds(10, 130, 400, 25);
        p.add(save);

        JButton login = new JButton("LOGIN");
        login.setBounds(10, 270, 480, 75);
        login.addActionListener(e -> {
            try {
                Client.IP = ip.getText();
                if (VaribleUtils.isInt(port.getText())) {
                    Client.PORT = Integer.parseInt(port.getText());
                } else {
                    JOptionPane.showConfirmDialog(f, "<html><body><p>Invalid Port<br />Please check the values or contact the<br />system administrator.</p></body></html>", "ChatApp - Error", JOptionPane.DEFAULT_OPTION);
                }
                Client.USR = usr.getText();
                ClientSocket.init();
                close();
                ClientUI.init();
                ClientUI.open();

                if (save.isSelected()) {
                    Config.setString("client.logged_in", "true");
                    Config.setString("client.ip", Client.IP);
                    Config.setString("client.port", "" + Client.PORT);
                    Config.setString("client.usr", Client.USR);
                } else {
                    Config.setString("client.logged_in", "false");
                }
            } catch (IOException ioException) {
                JOptionPane.showConfirmDialog(f, "<html><body><p>Invalid IP or Port<br />Please check the value doesn't contain any character.</p></body></html>", "ChatApp - Error", JOptionPane.DEFAULT_OPTION);
            }
        });
        p.add(login);

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

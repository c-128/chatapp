package com.sa.client.ui;

import com.sa.client.Client;
import com.sa.client.ClientSocket;
import com.sa.main.utils.VaribleUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientLoginUI {

    private static JFrame f;

    public static void init() throws IOException {
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

        JButton login = new JButton("LOGIN");
        login.setBounds(10, 270, 480, 75);
        login.addActionListener(e -> {
            try {
                Client.IP = ip.getText();
                if (VaribleUtils.isInt(port.getText())) {
                    Client.PORT = Integer.parseInt(port.getText());
                } else {
                    JOptionPane.showConfirmDialog(f, "ChatApp - Error", "Invalid Port\nPlease check the values or contact the\nsystem administrator.", JOptionPane.DEFAULT_OPTION);
                }
                Client.USR = usr.getText();
                ClientSocket.init();
                close();
                ClientUI.init();
                ClientUI.open();
            } catch (IOException ioException) {
                JOptionPane.showConfirmDialog(f, "ChatApp - Error", "Invalid IP or Port\nPlease check the value doesn't contain any character.", JOptionPane.DEFAULT_OPTION);
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

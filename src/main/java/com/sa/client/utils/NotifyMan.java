package com.sa.client.utils;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class NotifyMan {

    public static void newmsg(String title, String msg, String tooltip) throws AWTException, MalformedURLException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage(new URL("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.R2VLSIatqytfHObM2J1IigHaFj%26pid%3DApi&f=1"));

        TrayIcon trayIcon = new TrayIcon(image, tooltip);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(tooltip);
        tray.add(trayIcon);

        trayIcon.displayMessage(title, msg, TrayIcon.MessageType.INFO);
    }
}

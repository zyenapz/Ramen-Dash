package com.tayag.helpers;

import java.awt.*;

public class DisplayHelper {
    public static void displayStringCentered(String message, int x, int y, Graphics g) {
        int continueMsg_Width = g.getFontMetrics().stringWidth(message);
        int halfWidth = (continueMsg_Width / 2);

        g.drawString(message, x - halfWidth, y);
    }
}

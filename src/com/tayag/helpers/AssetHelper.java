package com.tayag.helpers;

import javax.swing.*;
import java.awt.*;

public class AssetHelper {
    public static Image loadImage(String path) {
        ImageIcon ico = new ImageIcon(path);
        Image img = ico.getImage();
        return img;
    }
}

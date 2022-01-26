package com.tayag.scenes;

import com.tayag.easel.GameCanvas;
import com.tayag.helpers.AssetHelper;
import com.tayag.helpers.DisplayHelper;
import com.tayag.helpers.WindowContext;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MenuScene extends AbstractScene {

    boolean isMouseClicked = false;

    // Assets
    private Image backgroundImg;
    private Image narutoImg;
    private Image ramenImg;
    private Image titleImg;

    public MenuScene() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void update(GameCanvas canvas) {
        if (isMouseClicked) {
            notifySceneChange(canvas, SceneID.SCENE_TUTORIAL);
        }
    }

    @Override
    public void draw(GameCanvas canvas, Graphics g) {
        g.drawImage(backgroundImg, 0, 0, canvas);

        // ----------------------
        int wincenWidth = (WindowContext.WIDTH / 2);
        g.setColor(Color.WHITE);

        // Title label
        int titleW = titleImg.getWidth(canvas);
        int titleH = titleImg.getHeight(canvas);
        int titleX = (WindowContext.WIDTH / 2) - (titleW / 2);
        int titleY = 32;
        g.drawImage(titleImg, titleX, titleY, titleW, titleH, canvas);

        // Draw naruto
        int narutoW = narutoImg.getWidth(canvas) * 3;
        int narutoH = narutoImg.getHeight(canvas) * 3;
        int narutoX = (WindowContext.WIDTH / 2) - (narutoW / 2);
        int narutoY = ((WindowContext.HEIGHT / 2) - (narutoH / 2)) - 16;
        g.drawImage(narutoImg, narutoX, narutoY, narutoW, narutoH, canvas);

        // Draw ramen
        int ramenW = ramenImg.getWidth(canvas) * 5;
        int ramenH = ramenImg.getHeight(canvas) * 5;
        int ramenX = (WindowContext.WIDTH / 2) - (ramenW / 2);
        int ramenY = narutoY + 64;
        g.drawImage(ramenImg, ramenX, ramenY, ramenW, ramenH, canvas);

        // "Click to Start" label
        String playMsg = "Click to Start!";
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
        DisplayHelper.displayStringCentered(playMsg, wincenWidth, (int) (WindowContext.HEIGHT * 0.8), g);

        // Credits
        String creditsMsg = "By " + WindowContext.AUTHOR + " for IT173p LabExer 2";
        g.setFont(new Font("Arial", Font.ITALIC, 16));
        DisplayHelper.displayStringCentered(creditsMsg, wincenWidth, WindowContext.HEIGHT - 48, g);
    }

    @Override
    public void loadAssets() {
        backgroundImg = AssetHelper.loadImage("resources/images/background.png");
        narutoImg = AssetHelper.loadImage("resources/images/naruto/D1.png");
        ramenImg = AssetHelper.loadImage("resources/images/ramen.png");
        titleImg = AssetHelper.loadImage("resources/images/title.png");
    }

    @Override
    public void initializeListeners() {
        keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isMouseClicked = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isMouseClicked = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

}

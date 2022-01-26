package com.tayag.scenes;

import com.tayag.easel.GameCanvas;
import com.tayag.helpers.AssetHelper;
import com.tayag.helpers.DisplayHelper;
import com.tayag.helpers.Report;
import com.tayag.helpers.WindowContext;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameOverScene extends AbstractScene {

    // Mouse and key states
    private boolean isMouseClicked = false;
    private int pressedKeyCode = 0;

    // Assets
    private Image backgroundImage;
    private Font fontL;
    private Font fontXL;

    public GameOverScene() {

    }

    @Override
    public void update(GameCanvas canvas) {
        if (isMouseClicked) {
            notifySceneChange(canvas, SceneID.SCENE_MENU);
        }
    }

    @Override
    public void draw(GameCanvas canvas, Graphics g) {

        // --------------------------
        g.drawImage(backgroundImage, 0, 0, canvas);
        g.setColor(Color.WHITE);

        // aliases
        int wincenWidth = WindowContext.WIDTH / 2;

        // "Game Over!" label
        g.setFont(fontXL);
        DisplayHelper.displayStringCentered("Game Over!", wincenWidth, 64, g);

        //  "You got" label
        g.setFont(fontL);
        DisplayHelper.displayStringCentered("You got", wincenWidth, 200, g);

        // Score label
        g.setFont(fontXL);
        int score = Report.score;
        String scoreMsg = Integer.toString(score);
        String msgWithPadding = String.format("%6s", scoreMsg).replace(' ', '0');
        DisplayHelper.displayStringCentered(msgWithPadding, wincenWidth, 280, g);

        // "pts" label
        g.setFont(fontL);
        DisplayHelper.displayStringCentered("pts", wincenWidth, 340, g);

        // "Click to continue" label
        DisplayHelper.displayStringCentered("Click to Continue!", wincenWidth, (int) (WindowContext.HEIGHT * 0.8), g);

    }

    @Override
    public void loadAssets() {
        backgroundImage = AssetHelper.loadImage("resources/images/background.png");
        fontL = new Font("Comic Sans MS", Font.BOLD, 48);
        fontXL = new Font("Comic Sans MS", Font.BOLD, 60);
    }

    @Override
    public void initializeListeners() {
        keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeyCode = e.getKeyCode();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeyCode = 0;
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

    @Override
    public void initialize() {

    }
}

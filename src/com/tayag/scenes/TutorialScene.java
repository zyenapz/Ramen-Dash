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

public class TutorialScene extends AbstractScene {

    // Mouse state
    private boolean isMouseClicked = false;

    // Assets
    private Image backgroundImg;
    private Image ramenImg;
    private Image lifeImg;
    private Image shurikenImg;
    private Image boulderImg;

    public TutorialScene() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void update(GameCanvas canvas) {
        if (isMouseClicked) {
            notifySceneChange(canvas, SceneID.SCENE_INGAME);
        }
    }

    @Override
    public void draw(GameCanvas canvas, Graphics g) {
        g.drawImage(backgroundImg, 0, 0, canvas);

        // aliases
        int wincenWidth = WindowContext.WIDTH / 2;
        int itemsSize = 64;

        g.setColor(Color.WHITE);

        // "How To Play" label
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
        DisplayHelper.displayStringCentered("How To Play", wincenWidth, 48, g);

        // ------ Item explanations -------
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 28));

        // Ramen explanation
        int ramenY = 50;
        g.drawString("- collect to gain points!", (wincenWidth / 2) + 48, ramenY + 50);
        g.drawImage(ramenImg, 128, ramenY, itemsSize, itemsSize, canvas);

        // Life explanation
        int lifeY = ramenY + 75;
        g.drawString("- gives +1 life", (wincenWidth / 2) + 48, lifeY + 50);
        g.drawImage(lifeImg, 128, lifeY, itemsSize, itemsSize, canvas);

        // Shuriken explanation
        int shurikenY = lifeY + 75;
        g.drawString("- decreases life", (wincenWidth / 2) + 48, shurikenY + 50);
        g.drawImage(shurikenImg, 128, shurikenY, itemsSize, itemsSize, canvas);

        // Boulder explanation
        int boulderY = shurikenY + 75;
        g.drawString("- pushes you around", (wincenWidth / 2) + 48, boulderY + 50);
        g.drawImage(boulderImg, 128, boulderY, itemsSize, itemsSize, canvas);

        // Controls explanation
        int controlsY = boulderY + 100;
        int warningY = controlsY + 32;
        DisplayHelper.displayStringCentered("WASD/Arrow Keys to Move", wincenWidth, controlsY, g);
        DisplayHelper.displayStringCentered("Game Over when life reaches zero!", wincenWidth, warningY, g);

        int lineY = (int) (WindowContext.HEIGHT * 0.70);
        g.drawLine(0, lineY, WindowContext.WIDTH, lineY);

        // "Click To Play!" label
        String playMsg = "Click to Play!";
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
        DisplayHelper.displayStringCentered(playMsg, wincenWidth, (int) (WindowContext.HEIGHT * 0.8), g);
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

    @Override
    public void loadAssets() {
        backgroundImg = AssetHelper.loadImage("resources/images/background.png");
        ramenImg = AssetHelper.loadImage("resources/images/ramen.png");
        lifeImg = AssetHelper.loadImage("resources/images/life.png");
        shurikenImg = AssetHelper.loadImage("resources/images/shuriken.png");
        boulderImg = AssetHelper.loadImage("resources/images/boulder.png");
    }
}

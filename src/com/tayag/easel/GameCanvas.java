package com.tayag.easel;

import com.tayag.helpers.Report;
import com.tayag.helpers.WindowContext;
import com.tayag.scenes.*;

import java.awt.*;

public class GameCanvas extends Canvas {

    private Image bufferImg;
    private Graphics bufferGraphics;
    private AbstractScene currentScene;

    // Report
    private Report report;

    public GameCanvas() {
        SceneID initialScene = SceneID.SCENE_MENU;
        transitionTo(initialScene);
    }

    public void update() {
        currentScene.update(this);
    }

    public void draw(Graphics g) {
        // not using repaint() since it queues up frames causing flickering
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        resetBuffer();

        currentScene.draw(this, bufferGraphics);

        drawBuffer(g);
    }

    private void resetBuffer() {
        if (bufferImg == null || bufferGraphics == null) {
            bufferImg = createImage(WindowContext.WIDTH, WindowContext.HEIGHT);
            bufferGraphics = bufferImg.getGraphics();
        }

        bufferGraphics.clearRect(0, 0, WindowContext.WIDTH, WindowContext.HEIGHT);
    }

    private void drawBuffer(Graphics g) {
        g.drawImage(bufferImg, 0, 0, this);
    }

    private void transitionTo(SceneID sceneID) {
        if (currentScene != null) {
            currentScene.removeListeners(this);
        }

        switch (sceneID) {
            case SCENE_MENU:
                currentScene = new MenuScene();
                break;
            case SCENE_TUTORIAL:
                currentScene = new TutorialScene();
                break;
            case SCENE_INGAME:
                currentScene = new GameScene();
                break;
            case SCENE_GAMEOVER:
                currentScene = new GameOverScene();
                break;
        }

        currentScene.loadAssets();
        currentScene.initializeListeners();
        currentScene.bindListeners(this);
        currentScene.initialize();

    }

    public void notifySceneChange(SceneID sceneID) {
        transitionTo(sceneID);
    }

    public void updateReport(int score) {
        Report.score = score;
    }

    public Report getReport() {
        return report;
    }
}

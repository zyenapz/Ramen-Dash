package com.tayag.scenes;

import com.tayag.easel.GameCanvas;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public abstract class AbstractScene {
    public KeyListener keyListener;
    public MouseListener mouseListener;

    // * * * * *
    // Delegate
    // * * * * *

    public abstract void initialize();

    public abstract void update(GameCanvas canvas);

    public abstract void draw(GameCanvas canvas, Graphics g);

    // * * * * *
    // Listeners
    // * * * * *

    public abstract void initializeListeners();

    public void bindListeners(GameCanvas canvas) {
        canvas.addKeyListener(keyListener);
        canvas.addMouseListener(mouseListener);
    }

    public void removeListeners(GameCanvas canvas) {
        canvas.removeKeyListener(keyListener);
        canvas.removeMouseListener(mouseListener);
    }

    // * * * * * * *
    // Scene Changes
    // * * * * * * *

    public void notifySceneChange(GameCanvas canvas, SceneID sceneID) {
        canvas.notifySceneChange(sceneID);
    }

    // * * * * * * *
    // Asset loading
    // * * * * * * *

    public abstract void loadAssets();

}

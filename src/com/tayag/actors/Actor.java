package com.tayag.actors;

import com.tayag.easel.GameCanvas;
import com.tayag.helpers.Vector2D;

import java.awt.*;

public abstract class Actor {

    protected Image image;
    protected Rectangle rect;
    protected Vector2D velocity;

    public abstract void update(GameCanvas canvas);

    public abstract void draw(GameCanvas canvas, Graphics g);

    public Rectangle getRect() {
        return rect;
    }
}

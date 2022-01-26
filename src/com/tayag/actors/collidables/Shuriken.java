package com.tayag.actors.collidables;

import com.tayag.easel.GameCanvas;
import com.tayag.helpers.Vector2D;

import java.awt.*;

public class Shuriken extends Collidable {

    public Shuriken(Image image) {
        this.image = image;
        rect = new Rectangle(50, 50);
        velocity = new Vector2D(0, 0);

        placeIntoWorld(rect, velocity);
    }

    @Override
    public void update(GameCanvas canvas) {

        if (isOutOfBounds(rect)) {
            markToBeCleaned();
        }

        rect.x += velocity.x;
        rect.y += velocity.y;
    }

    @Override
    public void draw(GameCanvas canvas, Graphics g) {
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, canvas);
    }
}

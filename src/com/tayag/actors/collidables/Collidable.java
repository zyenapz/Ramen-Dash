package com.tayag.actors.collidables;

import com.tayag.actors.Actor;
import com.tayag.actors.SpawnLocation;
import com.tayag.helpers.Vector2D;
import com.tayag.helpers.WindowContext;

import java.awt.*;
import java.util.Random;

public abstract class Collidable extends Actor {
    protected boolean cleanUpFlag = false;

    protected void placeIntoWorld(Rectangle rect, Vector2D velocity) {

        Random rand = new Random();

        SpawnLocation spawnLoc = SpawnLocation.values()[rand.nextInt(SpawnLocation.values().length)];

        int xMin = 0;
        int xMax = WindowContext.WIDTH - rect.width;

        int yMin = WindowContext.INFOBAR_HEIGHT;
        int yMax = WindowContext.HEIGHT - (rect.height * 2);

        int speedMin = 2;
        int speedMax = 7;
        int speed = rand.nextInt(speedMax + speedMin) + speedMin;

        switch (spawnLoc) {
            case TOP:
                rect.x = rand.nextInt(xMax - xMin + 1) + xMin;
                rect.y = -rect.height;
                velocity.x = 0;
                velocity.y = speed;
                break;

            case BOTTOM:
                rect.x = rand.nextInt(xMax - xMin + 1) + xMin;
                rect.y = WindowContext.HEIGHT;
                velocity.x = 0;
                velocity.y = -speed;
                break;

            case LEFT:
                rect.x = -rect.width;
                rect.y = rand.nextInt(yMax - yMin + 1) + yMin;
                velocity.x = speed;
                velocity.y = 0;
                break;

            case RIGHT:
                rect.x = WindowContext.WIDTH;
                rect.y = rand.nextInt(yMax - yMin + 1) + yMin;
                velocity.x = -speed;
                velocity.y = 0;
                break;
        }
    }

    protected boolean isOutOfBounds(Rectangle rect) {
        // ov - out of view
        boolean ovx = rect.x < -rect.width || rect.x > WindowContext.WIDTH;
        boolean ovy = rect.y < -rect.height || rect.y > WindowContext.HEIGHT;

        return ovx || ovy;
    }

    protected void markToBeCleaned() {
        cleanUpFlag = true;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public boolean getCleanUpFlag() {
        return cleanUpFlag;
    }
}

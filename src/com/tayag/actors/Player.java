package com.tayag.actors;

import com.tayag.easel.GameCanvas;
import com.tayag.helpers.Vector2D;
import com.tayag.helpers.WindowContext;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;

public class Player extends Actor {

    // Constants
    private final double DEC_SPEED = 0.2;
    private final short MAX_FRAMES = 4;
    private final int SPEED = 9;
    private final double DIAGONAL_FIX = 0.7071;
    private final Hashtable<PlayerOrientation, ArrayList<Image>> imageTable;
    // keyPresses
    private ArrayList<Boolean> keypresses;
    // Animation
    private PlayerOrientation pOrientation = PlayerOrientation.DOWN;
    private short curFrame = 0;

    // Player state
    private boolean isStunned = false;

    public Player(Hashtable<PlayerOrientation, ArrayList<Image>> images) {
        imageTable = images;
        rect = new Rectangle(500, WindowContext.HEIGHT - 300, (int) (35 * 1.25), (int) (51 * 1.25));
        velocity = new Vector2D(0, 0);
        keypresses = new ArrayList<Boolean>();
    }

    // * * * *
    // Override
    // * * * *

    @Override
    public void update(GameCanvas canvas) {

        if (!isStunned) {
            velocity.multiply(0);
            move(keypresses);
            fixDiagonalSpeed();
        } else {
            decelerate();
        }

        keepWithinBounds();

        rect.x += velocity.x;
        rect.y += velocity.y;
    }

    @Override
    public void draw(GameCanvas canvas, Graphics g) {
        Image frame = imageTable.get(pOrientation).get(curFrame);
        g.drawImage(frame, rect.x, rect.y, rect.width, rect.height, canvas);
    }

    // * * * *
    // Custom
    // * * * *

    private void move(ArrayList<Boolean> keypresses) {

        boolean isPressedA = keypresses.get(KeyEvent.VK_A) || keypresses.get(KeyEvent.VK_LEFT);
        boolean isPressedD = keypresses.get(KeyEvent.VK_D) || keypresses.get(KeyEvent.VK_RIGHT);
        boolean isPressedW = keypresses.get(KeyEvent.VK_W) || keypresses.get(KeyEvent.VK_UP);
        boolean isPressedS = keypresses.get(KeyEvent.VK_S) || keypresses.get(KeyEvent.VK_DOWN);

        // --------------------------
        if (isPressedA || isPressedD || isPressedW || isPressedS) {
            nextFrame();
        } else {
            curFrame = 0;
        }

        // --------------------------
        if (isPressedA) {
            velocity.x -= SPEED;
            pOrientation = PlayerOrientation.LEFT;
        } else if (isPressedD) {
            velocity.x += SPEED;
            pOrientation = PlayerOrientation.RIGHT;
        }
        if (isPressedW) {
            velocity.y -= SPEED;
            pOrientation = PlayerOrientation.UP;
        } else if (isPressedS) {
            velocity.y += SPEED;
            pOrientation = PlayerOrientation.DOWN;
        }
    }

    public void updateKeyPresses(ArrayList<Boolean> keypresses) {
        this.keypresses = keypresses;
    }

    public void applyForce(Vector2D force) {
        velocity.multiply(0);
        velocity.x += force.x;
        velocity.y += force.y;
    }

    public void doStun() {
        isStunned = true;
    }

    public void undoStun() {
        isStunned = false;
    }

    private void keepWithinBounds() {

        // NOTE: rRight and rBottom doesn't really make sense. Change later if possible.
        // For example... rRight should just be rect.x + rect.width
        // but for some reason, that doesn't work properly for collision detection against the window's right edge
        // same story with the rBottom one.

        // alias
        int rLeft = rect.x;
        int rRight = rect.x + rect.width + (rect.width / 2);
        int rTop = rect.y;
        int rBottom = rect.y + rect.height + (rect.height / 2) + (rect.height / 4);

        // X-axis check
        if (rLeft + velocity.x < 0) {
            velocity.x = 0;
            rect.x = 0;
            undoStun();
        } else if (rRight + velocity.x > WindowContext.WIDTH) {
            velocity.x = 0;
            rect.x = WindowContext.WIDTH - rect.width - (rect.width / 2);
            isStunned = false;
            undoStun();

        }

        // Y-axis check
        if (rTop + velocity.y < WindowContext.INFOBAR_HEIGHT) {
            velocity.y = 0;
            rect.y = WindowContext.INFOBAR_HEIGHT;
            isStunned = false;
            undoStun();

        } else if (rBottom + velocity.y > WindowContext.HEIGHT) {
            velocity.y = 0;
            rect.y = WindowContext.HEIGHT - rect.height - (rect.height / 2) - (rect.height / 4);
            isStunned = false;
            undoStun();

        }

    }

    private void nextFrame() {
        curFrame = (short) ((curFrame + 1) % MAX_FRAMES);
    }

    private void decelerate() {
        // Velocity decrease when stunned

        if (velocity.x != 0) {
            if (velocity.x < 0) {
                velocity.x += DEC_SPEED;
            } else {
                velocity.x -= DEC_SPEED;
            }
        }

        if (velocity.y != 0) {
            if (velocity.y < 0) {
                velocity.y += DEC_SPEED;
            } else {
                velocity.y -= DEC_SPEED;
            }
        }

        if (Math.round(velocity.x) == 0 && Math.round(velocity.y) == 0) {
            undoStun();
        }
    }

    private void fixDiagonalSpeed() {
        if (velocity.x != 0 && velocity.y != 0) {
            velocity.multiply(DIAGONAL_FIX);
        }
    }

}

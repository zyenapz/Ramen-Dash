package com.tayag.scenes;

import com.tayag.actors.Player;
import com.tayag.actors.PlayerOrientation;
import com.tayag.actors.collidables.*;
import com.tayag.easel.GameCanvas;
import com.tayag.helpers.AssetHelper;
import com.tayag.helpers.Vector2D;
import com.tayag.helpers.WindowContext;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class GameScene extends AbstractScene {

    private final int ASCII_ENCODING_LIMIT = 128;
    private final int SCORE_INCREMENT = 100;
    private final short SPAWN_DELAY = 300;
    private final short MAX_COLLIDABLES = 12;
    private final short LIVES_MAX = 3;
    // Key list
    private ArrayList<Boolean> keypresses;
    // Mouse and key states
    private boolean isMouseClicked = false;
    private int pressedKeyCode = 0;
    // Assets
    private Hashtable<PlayerOrientation, ArrayList<Image>> narutoImages;
    private Image ramenImg;
    private Image lifeImg;
    private Image shurikenImg;
    private Image boulderImg;
    private Image backgroundImg;
    private Font fontLarge;
    // Player
    private Player player;
    // Actor groups
    private ArrayList<Collidable> groupCollidables;
    private ArrayList<Collidable> groupCollidablesToRemove;
    // Session variables
    private int lives = 3;
    private int score = 0;
    private long spawnTicks;

    public GameScene() {
        // Intentionally Blank. No need to initialize here.
        // initialize(), loadAssets(), and bindListeners() are automatically called.
    }

    // * * * *
    // Override
    // * * * *

    @Override
    public void initialize() {

        Date now = new Date();
        spawnTicks = now.getTime();

        // Set up keypress list
        keypresses = new ArrayList<Boolean>();
        for (int i = 0; i < ASCII_ENCODING_LIMIT; i++) {
            keypresses.add(false);
        }

        initializePlayer();

        groupCollidables = new ArrayList<Collidable>();
        groupCollidablesToRemove = new ArrayList<Collidable>();
    }

    @Override
    public void update(GameCanvas canvas) {

        if (pressedKeyCode == KeyEvent.VK_ESCAPE) {
            notifySceneChange(canvas, SceneID.SCENE_MENU);
        }

        spawnCollidables();
        detectCollision(canvas);

        if (lives <= 0) {
            canvas.updateReport(score);
            notifySceneChange(canvas, SceneID.SCENE_GAMEOVER);
        }

        player.updateKeyPresses(keypresses);
        player.update(canvas);
    }

    @Override
    public void draw(GameCanvas canvas, Graphics g) {
        g.drawImage(backgroundImg, 0, 0, canvas);
        g.setFont(fontLarge);

        for (Collidable collidable : groupCollidables) {
            collidable.draw(canvas, g);
        }

        // Draw info bar
        String scoreMsg = Integer.toString(score);
        String msgWithPadding = String.format("%6s", scoreMsg).replace(' ', '0');
        int msgWidth = g.getFontMetrics().stringWidth(msgWithPadding);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WindowContext.WIDTH, WindowContext.INFOBAR_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawString(msgWithPadding, (WindowContext.WIDTH / 2) - (msgWidth / 2), 36);

        for (int i = 0; i < lives; i++) {
            g.drawImage(lifeImg, 8 + (36 * i), 8, canvas);
        }

        player.draw(canvas, g);
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
                keypresses.set(pressedKeyCode, true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keypresses.set(e.getKeyCode(), false);
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
    public void loadAssets() {

        constructPlayerImageTable();
        ramenImg = AssetHelper.loadImage("resources/images/ramen.png");
        lifeImg = AssetHelper.loadImage("resources/images/life.png");
        shurikenImg = AssetHelper.loadImage("resources/images/shuriken.png");
        boulderImg = AssetHelper.loadImage("resources/images/boulder.png");
        backgroundImg = AssetHelper.loadImage("resources/images/background.png");
        fontLarge = new Font("Comic Sans MS", Font.BOLD, 32);
    }

    // * * * *
    // Custom
    // * * * *

    private void initializePlayer() {
        player = new Player(narutoImages);
    }

    private void constructPlayerImageTable() {
        narutoImages = new Hashtable<PlayerOrientation, ArrayList<Image>>();
        ArrayList<String> directions = new ArrayList<String>();
        directions.add("U");
        directions.add("D");
        directions.add("L");
        directions.add("R");
        final int FRAME_COUNT = 4;

        ArrayList<Image> upFrames = new ArrayList<Image>();
        ArrayList<Image> downFrames = new ArrayList<Image>();
        ArrayList<Image> leftFrames = new ArrayList<Image>();
        ArrayList<Image> rightFrames = new ArrayList<Image>();
        narutoImages.put(PlayerOrientation.UP, upFrames);
        narutoImages.put(PlayerOrientation.DOWN, downFrames);
        narutoImages.put(PlayerOrientation.LEFT, leftFrames);
        narutoImages.put(PlayerOrientation.RIGHT, rightFrames);

        for (String direction : directions) {
            for (int i = 1; i <= FRAME_COUNT; i++) {
                String path = "resources/images/naruto/" + direction + i + ".png";
                Image img = AssetHelper.loadImage(path);

                // Determine PlayerOrientation key from direction String object
                PlayerOrientation orientation;
                switch (direction) {
                    case "U":
                        orientation = PlayerOrientation.UP;
                        break;
                    case "D":
                        orientation = PlayerOrientation.DOWN;
                        break;
                    case "L":
                        orientation = PlayerOrientation.LEFT;
                        break;
                    case "R":
                        orientation = PlayerOrientation.RIGHT;
                        break;
                    default:
                        orientation = null;
                        break;
                }
                narutoImages.get(orientation).add(img);
            }
        }
    }

    private void spawnCollidables() {
        long now = new Date().getTime();
        if (now - spawnTicks > SPAWN_DELAY && groupCollidables.size() <= MAX_COLLIDABLES) {
            resetSpawnTicks();

            PickID collidableType = getCollidableType();
            Collidable toSpawn;

            switch (collidableType) {
                case RAMEN:
                    toSpawn = new Ramen(ramenImg);
                    break;
                case LIFE:
                    toSpawn = new Life(lifeImg);
                    break;
                case SHURIKEN:
                    toSpawn = new Shuriken(shurikenImg);
                    break;
                case BOULDER:
                    toSpawn = new Boulder(boulderImg);
                    break;
                default:
                    toSpawn = new Ramen(ramenImg);
                    break;
            }

            groupCollidables.add(toSpawn);
        }

    }

    private PickID getCollidableType() {
        int ramenWeight = 10;
        int lifeWeight = 1;
        int shurikenWeight = 5;
        int boulderWeight = 5;
        ArrayList<PickID> randomBag = new ArrayList<PickID>();

        for (int i = 0; i < ramenWeight; i++) {
            randomBag.add(PickID.RAMEN);
        }
        for (int i = 0; i < lifeWeight; i++) {
            randomBag.add(PickID.LIFE);
        }
        for (int i = 0; i < shurikenWeight; i++) {
            randomBag.add(PickID.SHURIKEN);
        }
        for (int i = 0; i < boulderWeight; i++) {
            randomBag.add(PickID.BOULDER);
        }

        Random rand = new Random();
        int indexPick = rand.nextInt(randomBag.size());
        Collections.shuffle(randomBag);
        PickID pick = randomBag.get(indexPick);

        return pick;
    }

    private void detectCollision(GameCanvas canvas) {
        for (Collidable collidable : groupCollidables) {
            collidable.update(canvas);

            if (collidable.getRect().intersects(player.getRect())) {
                groupCollidablesToRemove.add(collidable);

                if (collidable.getClass() == Ramen.class) {
                    score += SCORE_INCREMENT;
                } else if (collidable.getClass() == Life.class) {
                    lives++;
                } else if (collidable.getClass() == Shuriken.class) {
                    lives--;
                } else if (collidable.getClass() == Boulder.class) {
                    Vector2D appliedForce = new Vector2D(collidable.getVelocity().x * 2, collidable.getVelocity().y * 2);
                    player.applyForce(appliedForce);
                    player.doStun();
                }

                // Bound life within 0-3 lives only
                if (lives >= LIVES_MAX) {
                    lives = 3;
                } else if (lives <= 0) {
                    lives = 0;
                }
            }

            if (collidable.getCleanUpFlag()) {
                groupCollidablesToRemove.add(collidable);
            }
        }

        groupCollidables.removeAll(groupCollidablesToRemove);
    }

    private void resetSpawnTicks() {
        spawnTicks = new Date().getTime();
    }
}

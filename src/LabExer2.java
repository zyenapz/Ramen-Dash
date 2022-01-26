import com.tayag.easel.GameCanvas;
import com.tayag.helpers.WindowContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class LabExer2 {

    private static JFrame frame;
    private static Container pane;
    private static GameCanvas canvas;

    // ** ENTRY / DRIVER CODE **
    public static void main(String[] args) {
        initializeCanvas();
        initializeFrame();
        initializePane();
        runGame();
    }

    private static void initializeFrame() {
        frame = new JFrame();

        frame.setSize(WindowContext.WIDTH, WindowContext.HEIGHT);
        frame.setResizable(WindowContext.IS_RESIZABLE);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setTitle(WindowContext.TITLE);
    }

    private static void initializeCanvas() {
        canvas = new GameCanvas();

        canvas.setBounds(0, 0, WindowContext.WIDTH, WindowContext.HEIGHT);
        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    private static void initializePane() {
        if (frame != null && canvas != null) {
            pane = frame.getContentPane();
            pane.setLayout(null);
            pane.add(canvas);
        }
    }

    private static void runGame() {
        if (frame != null && canvas != null && pane != null) {
            Timer fpsTimer = new Timer();
            TimerTask runGame = new TimerTask() {
                public void run() {
                    Graphics g = canvas.getGraphics();
                    canvas.update();
                    canvas.draw(g);
                }
            };
            fpsTimer.schedule(runGame, 0, WindowContext.REFRESH_RATE);
        }
    }

}


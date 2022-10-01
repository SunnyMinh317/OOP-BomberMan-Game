package main.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import main.Entity.Bomber;
import main.Entity.Bomb;
import main.Game;
import main.Input.Keyboard;

public class GamePanel extends JPanel implements Runnable {
    final int WINDOW_WIDTH = 720;
    final int WINDOW_HEIGHT = 624;
    public Thread gameThread;
    public boolean isRunning;
    public Keyboard input = new Keyboard();
    public Game mainGame = new Game(this, input);


    public GamePanel() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(new Color(56, 135, 0));
        startGameThread();
        this.addKeyListener(input);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);

        isRunning = true;
        gameThread.start();
        requestFocus();
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                update();
                repaint();
                Thread.sleep(1000/60);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        mainGame.updateGame();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        mainGame.drawGame(g2);

        g2.dispose();
    }
}

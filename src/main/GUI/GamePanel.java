package main.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.Entity.Bomber;
import main.Entity.Bomb;
import main.Game;
import main.Input.Keyboard;
import main.Input.Mouse;

public class GamePanel extends JPanel implements Runnable {
    public static final int WINDOW_WIDTH = 720;
    public static final int WINDOW_HEIGHT = 624;
    static Sound sound = new Sound();
    public static Thread gameThread;
    public static boolean isRunning;
    public Keyboard input = new Keyboard();
    public Game mainGame = new Game(this, input);
    public int gameState;
    public final int MENU_SCREEN_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int GAME_OVER_STATE = 3;
    public UI ui;


    public GamePanel() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(new Color(56, 135, 0));
        startGameThread();
        this.addKeyListener(input);
        this.setFocusable(true);
        ui = new UI(this, mainGame);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameState = MENU_SCREEN_STATE;
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
                Thread.sleep(1000 / 60);
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
        ui.draw(g2);

        g2.dispose();
    }

    public static void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public static void stopMusic() {
        sound.stop();
    }

    public static void playSFX(int i) {
        sound.setFile(i);
        sound.play();
    }

    public static void isPlaying() {
        if(sound.isPlaying()) {
            System.out.println("PLAYING music");
        } else {
            System.out.println("MUSIC stop");
        }
    }

    public void restart() {
        gameState = MENU_SCREEN_STATE;
    }



    public void playMusicByState() {
        if (gameState == PLAY_STATE) {
            playMusic(0);
        } else if (gameState == GAME_OVER_STATE) {
            playSFX(4);
        }
    }
}

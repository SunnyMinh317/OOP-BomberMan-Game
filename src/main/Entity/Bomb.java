package main.Entity;

import main.Game;
import main.Input.Keyboard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class Bomb extends Entity {
    private int timer = 0;
    private boolean placed = false;
    private Keyboard kh;
    private static BufferedImage[] bombFrames;

    public Bomb(Keyboard kh) {
        timer = 0;
        x = 0;
        y = 0;
        placed = false;
        this.kh = kh;

    }

    public static void loadBombImage() {
        try {
            for (int i = 0; i < 3; i++) {
                bombFrames[i] = Game.gameTileSheet.getSubimage(i * 16, 3 * 16, 16, 16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBomb(int playX, int playY) {
        if (placed) {
            if (timer < 240) {
                timer++;
            } else {
                placed = false;
                x = 0;
                y = 0;
                timer = 0;
            }
        }

        if (kh.space && !placed) {
            placed = true;
            x = playX;
            y = playY;
        }
    }

    public void drawBomb(Graphics2D g) {
        if (placed) {
            g.drawImage(bombFrames[0], this.x, this.y, 48, 48, null);
        }
    }
}

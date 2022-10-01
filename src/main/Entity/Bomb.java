package main.Entity;

import main.Input.Keyboard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Bomb extends Entity {
    private int timer = 0;
    private boolean placed = false;
    private Keyboard kh;
    private BufferedImage blast;

    public Bomb(Keyboard kh) {
        timer = 0;
        x = 0;
        y = 0;
        placed = false;
        this.kh = kh;
        loadBombImage();
    }

    public void loadBombImage() {
        try {
            blast = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/bomb.png")));
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
            g.drawImage(blast, this.x, this.y, 75, 75, null);
        }
    }
}

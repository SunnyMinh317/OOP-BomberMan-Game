package main.Entity;

import javax.imageio.ImageIO;
import main.GUI.GamePanel;
import main.Input.Keyboard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Bomber extends Entity {

    public GamePanel gp;
    public Keyboard kh;
    public static BufferedImage sCal;

    public Bomber(GamePanel gp, Keyboard kh) {
        this.gp = gp;
        this.kh = kh;
        loadBomberSprite();
    }

    public void loadBomberSprite() {
        try {
            sCal = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBomber() {
        if (kh.left) {
            x -= 5;
//            System.out.println("Going left!");
        }
        if (kh.right) {
            x += 5;
//            System.out.println("Going right!");
        }
        if (kh.up) {
            y -= 5;
//            System.out.println("Going up!");
        }
        if (kh.down) {
            y += 5;
//            System.out.println("Going down!");
        }
    }

    public void drawBomber(Graphics2D g) {
        g.drawImage(sCal, x, y, 50, 50, null);
    }
}

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
        this.x = 48;
        this.y = 48;
        this.speed = 8;
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

    public boolean canMove(int nextX, int nextY, int[][] scene) {
        int size = 3 * 16;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 1) / size;

        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;

        return !((scene[nextY_1][nextX_1] == 1 || scene[nextY_1][nextX_1] == 2) ||
                (scene[nextY_2][nextX_2] == 1 || scene[nextY_2][nextX_2] == 2) ||
                (scene[nextY_3][nextX_3] == 1 || scene[nextY_3][nextX_3] == 2) ||
                (scene[nextY_4][nextX_4] == 1 || scene[nextY_4][nextX_4] == 2));
    }

    public void updateBomber(int[][] map) {
        if (kh.left && canMove(this.x - speed, this.y, map)) {
            x -= speed;
        }
        if (kh.right && canMove(this.x + speed, this.y, map)) {
            x += speed;
        }
        if (kh.up && canMove(this.x, this.y - speed, map)) {
            y -= speed;
        }
        if (kh.down && canMove(this.x, this.y + speed, map)) {
            y += speed;
        }
    }

    public void drawBomber(Graphics2D g) {
        g.drawImage(sCal, x, y, 48, 48, null);
    }
}

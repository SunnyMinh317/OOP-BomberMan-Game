package main.Entity;

import javax.imageio.ImageIO;
import main.GUI.GamePanel;
import main.Input.Keyboard;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Bomber extends Entity {
    public Keyboard kh;

    private int direction = 0; // 0 = SOUTH, 1 = EAST, 2 = NORTH, 3 = WEST
    private boolean isMoving = false;
    private static BufferedImage[] bomberSpriteLeft = new BufferedImage[3];
    private static BufferedImage[] bomberSpriteRight = new BufferedImage[3];
    private static BufferedImage[] bomberSpriteUp = new BufferedImage[3];
    private static BufferedImage[] bomberSpriteDown = new BufferedImage[3];

    private int currentPlayerTick = 0, playerFrameInterval = 5, currentPlayerFrameIndex = 0;

    public int maxBombs;

    public Bomber(GamePanel gp, Keyboard kh) {
        this.x = 48;
        this.y = 48;
        this.speed = 4;
        this.kh = kh;
        this.maxBombs = 50;
    }

    public static void loadBomberSprite() {
        try {
            for (int i = 0; i < 3; i++) {
                bomberSpriteLeft[i] = Game.gameTileSheet.getSubimage(i * 16, 0, 16, 16);
                bomberSpriteRight[i] = Game.gameTileSheet.getSubimage(i * 16, 16, 16, 16);
                bomberSpriteUp[i] = Game.gameTileSheet.getSubimage((i + 3) * 16, 16, 16, 16);
                bomberSpriteDown[i] = Game.gameTileSheet.getSubimage((i + 3) * 16, 0, 16, 16);
            }
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

    public void updateBomber(int[][] map, ArrayList<Bomb> activeBombs) {
        isMoving = false;

        if (kh.left && canMove(this.x - speed, this.y, map)) {
            direction = 3;
            isMoving = true;
            x -= speed;
        }
        if (kh.right && canMove(this.x + speed, this.y, map)) {
            direction = 1;
            isMoving = true;
            x += speed;
        }
        if (kh.up && canMove(this.x, this.y - speed, map)) {
            direction = 2;
            isMoving = true;
            y -= speed;
        }
        if (kh.down && canMove(this.x, this.y + speed, map)) {
            direction = 0;
            isMoving = true;
            y += speed;
        }
        if (kh.space && activeBombs.size() < maxBombs) {
            int bombX = (this.x + 24) / 48;
            int bombY = (this.y + 24) / 48;

            if (map[bombY][bombX] == 0) {
                Bomb newBomb = new Bomb(bombX * 48, bombY * 48);
                activeBombs.add(newBomb);
                map[bombY][bombX] = 3;
            }
        }

        if (isMoving) {
            currentPlayerTick++;
            if (currentPlayerTick == playerFrameInterval) {
                currentPlayerTick = 0;
                currentPlayerFrameIndex++;
                if (currentPlayerFrameIndex == 3) {
                    currentPlayerFrameIndex = 0;
                }
            }
        }

        if (!isMoving) {
            currentPlayerFrameIndex = 1;
        }
    }

    public void drawBomber(Graphics2D g) {
        switch (direction) {
            case 0:
                g.drawImage(bomberSpriteDown[currentPlayerFrameIndex], x, y, 48, 48, null);
                break;
            case 1:
                g.drawImage(bomberSpriteRight[currentPlayerFrameIndex], x, y, 48, 48, null);
                break;
            case 2:
                g.drawImage(bomberSpriteUp[currentPlayerFrameIndex], x, y, 48, 48, null);
                break;
            case 3:
                g.drawImage(bomberSpriteLeft[currentPlayerFrameIndex], x, y, 48, 48, null);
                break;
            default:
                g.drawImage(bomberSpriteDown[currentPlayerFrameIndex], x, y, 48, 48, null);
                break;
        }
    }
}

package main.Entity;

import javax.imageio.ImageIO;

import main.GUI.GamePanel;
import main.Input.Keyboard;
import main.Game;
import main.Level.GameMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Bomber extends Entity {

    public GamePanel gp;
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
        this.gp = gp;
        this.kh = kh;
        this.maxBombs = 2;
    }

    public static void loadBomberSprite() {
        try {
            for (int i = 0; i < 3; i++) {
                bomberSpriteLeft[i] = Game.gameTileSheet.getSubimage(i * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
                bomberSpriteRight[i] = Game.gameTileSheet.getSubimage(i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
                bomberSpriteUp[i] = Game.gameTileSheet.getSubimage((i + 3) * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
                bomberSpriteDown[i] = Game.gameTileSheet.getSubimage((i + 3) * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
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

    // Check collision between 2 rectangle objects
    private boolean isOverlapping(Rectangle a, Rectangle b) {
        if (a == null || b == null) return false;
        double x1 = a.getX();
        double y1 = a.getY() - a.getHeight();
        double x2 = a.getX() + a.getWidth();
        double y2 = a.getY();
        double x3 = b.getX();
        double y3 = b.getY() - b.getHeight();
        double x4 = b.getX() + b.getWidth();
        double y4 = b.getY();

        return (x1 < x4) && (x3 < x2) && (y1 < y4) && (y3 < y2);
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

        // Store index of speedItems
        int speedI = 0;
        int speedJ = 0;

        // Store index of flareItems
        int flareI = 0;
        int flareJ = 0;

        // Store index of bombItems
        int bombI = 0;
        int bombJ = 0;

        // Rectangle of each item, for collision detection
        Rectangle speedItemRect = null;
        Rectangle flareItemRect = null;
        Rectangle bombItemRect = null;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 4) {
                    speedI = i;
                    speedJ = j;

                    // Somehow only works when mixing up j and i? Needs checking.
                    speedItemRect = new Rectangle(speedJ * SPRITE_SIZE, speedI * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
                }

                if (map[i][j] == 5) {
                    flareI = i;
                    flareJ = j;

                    // Somehow only works when mixing up j and i? Needs checking.
                    flareItemRect = new Rectangle(flareJ * SPRITE_SIZE, flareI * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
                }

                if (map[i][j] == 6) {
                    bombI = i;
                    bombJ = j;

                    // Somehow only works when mixing up j and i? Needs checking.
                    bombItemRect = new Rectangle(bombJ * SPRITE_SIZE, bombI * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
                }
            }
        }

        // Rectangle of bomber, for collision checking
        Rectangle bomberRect = new Rectangle(x, y, SPRITE_SIZE, SPRITE_SIZE);

        // Detect collision with speed item
        if (isOverlapping(bomberRect, speedItemRect)) {
            map[speedI][speedJ] = 0;
            System.out.println("Collected speed item!");

            // Speed up
            speed = 8;

            // Reset position for movement
            this.x = (this.x / 8) * 8;
            this.y = (this.y / 8) * 8;
        }

        // Detect collision with flare item
        if (isOverlapping(bomberRect, flareItemRect)) {
            map[flareI][flareJ] = 0;
            System.out.println("Collected flare item!");

            // Effect of flare item
            // TO DO!
        }

        // Detect collision with bomb item
        if (isOverlapping(bomberRect, bombItemRect)) {
            map[bombI][bombJ] = 0;
            System.out.println("Collected bomb item!");

            // Increase maxBombs variable, bomber able to set more bomb at the same time
            maxBombs++;
            System.out.println("Max bomb = "+maxBombs);
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
            case 1 -> g.drawImage(bomberSpriteRight[currentPlayerFrameIndex], x, y, TILE_SIZE * 3, TILE_SIZE * 3, null);
            case 2 -> g.drawImage(bomberSpriteUp[currentPlayerFrameIndex], x, y, TILE_SIZE * 3, TILE_SIZE * 3, null);
            case 3 -> g.drawImage(bomberSpriteLeft[currentPlayerFrameIndex], x, y, TILE_SIZE * 3, TILE_SIZE * 3, null);
            default -> g.drawImage(bomberSpriteDown[currentPlayerFrameIndex], x, y, TILE_SIZE * 3, TILE_SIZE * 3, null);
        }
//        g.drawImage(sCal, x, y, 48, 48, null);
    }
}

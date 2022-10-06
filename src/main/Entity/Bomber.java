package main.Entity;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;

import main.Entity.Tiles.Flame;
import main.GUI.GamePanel;
import main.Input.Keyboard;
import main.Game;
import main.Level.GameMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Bomber extends Entity {
    public Keyboard kh;

    private int bomberState = 0; // 0 = SOUTH, 1 = EAST, 2 = NORTH, 3 = WEST, 4 = DEAD, 5 = DEFAULT
    private boolean isMoving = false;
    static boolean isDead = false;
    private boolean gameOver = false;
    private static BufferedImage[] bomberSpriteLeft = new BufferedImage[3];
    private static BufferedImage[] bomberSpriteRight = new BufferedImage[3];
    private static BufferedImage[] bomberSpriteUp = new BufferedImage[3];
    private static BufferedImage[] bomberSpriteDown = new BufferedImage[3];
    private static BufferedImage[] bomberSpriteDie = new BufferedImage[8];

    private int currentPlayerTick = 0, playerFrameInterval = 5, currentPlayerFrameIndex = 0;


    public int maxBombs;

    public Bomber(GamePanel gp, Keyboard kh) {
        this.x = 48;
        this.y = 48;
        this.speed = 4;
        this.kh = kh;
        this.maxBombs = 10;
    }

    public static void loadBomberSprite() {
        try {
            for (int i = 0; i < 3; i++) {
                bomberSpriteLeft[i] = Game.gameTileSheet.getSubimage(i * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
                bomberSpriteRight[i] = Game.gameTileSheet.getSubimage(i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
                bomberSpriteUp[i] = Game.gameTileSheet.getSubimage((i + 3) * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
                bomberSpriteDown[i] = Game.gameTileSheet.getSubimage((i + 3) * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
            }

            for (int i = 0; i < 8; i++) {
                bomberSpriteDie[i] = Game.gameTileSheet.getSubimage(i * TILE_SIZE, 2 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean canMove(int nextX, int nextY, int[][] map) {
        int size = 3 * 16;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 1) / size;

        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;

        return !((map[nextY_1][nextX_1] == 1 || map[nextY_1][nextX_1] == 2) ||
                (map[nextY_2][nextX_2] == 1 || map[nextY_2][nextX_2] == 2) ||
                (map[nextY_3][nextX_3] == 1 || map[nextY_3][nextX_3] == 2) ||
                (map[nextY_4][nextX_4] == 1 || map[nextY_4][nextX_4] == 2));
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

    public void updateBomber(int[][] map, int[][] itemLayer, ArrayList<Bomb> activeBombs) {
        isMoving = false;
        if (kh.left && canMove(this.x - speed, this.y, map)) {
            bomberState = 3;
            isMoving = true;
            x -= speed;
        }
        if (kh.right && canMove(this.x + speed, this.y, map)) {
            bomberState = 1;
            isMoving = true;
            x += speed;
        }
        if (kh.up && canMove(this.x, this.y - speed, map)) {
            bomberState = 2;
            isMoving = true;
            y -= speed;
        }
        if (kh.down && canMove(this.x, this.y + speed, map)) {
            bomberState = 0;
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
        for (int i = 0; i < itemLayer.length; i++) {
            for (int j = 0; j < itemLayer[i].length; j++) {
                if (itemLayer[i][j] == 4) {
                    speedI = i;
                    speedJ = j;

                    // Somehow only works when mixing up j and i? Needs checking.
                    speedItemRect = new Rectangle(speedJ * SPRITE_SIZE, speedI * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
                }

                if (itemLayer[i][j] == 5) {
                    flareI = i;
                    flareJ = j;

                    // Somehow only works when mixing up j and i? Needs checking.
                    flareItemRect = new Rectangle(flareJ * SPRITE_SIZE, flareI * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
                }

                if (itemLayer[i][j] == 6) {
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
            itemLayer[speedI][speedJ] = 0;
            System.out.println("Collected speed item!");

            // Speed up
            speed = 8;

            // Reset position for movement
            this.x = (this.x / 8) * 8;
            this.y = (this.y / 8) * 8;
        }

        // Detect collision with flare item
        if (isOverlapping(bomberRect, flareItemRect)) {
            itemLayer[flareI][flareJ] = 0;
            System.out.println("Collected flare item!");
            Bomb.bombStrength++;
            // Effect of flare item
            // TO DO!
        }

        // Detect collision with bomb item
        if (isOverlapping(bomberRect, bombItemRect)) {
            itemLayer[bombI][bombJ] = 0;
            System.out.println("Collected bomb item!");

            // Increase maxBombs variable, bomber able to set more bomb at the same time
            maxBombs++;
            System.out.println("Max bomb = " + maxBombs);
        }

        int flameX;
        int flameY;
        Rectangle flameRect = null;
        boolean touched = false;

        // If the player is not yet dead, process movement, and check for fire hazard??
        if (!isDead) {
            for (Bomb activeBomb : activeBombs) {
                for (Flame spreadFlame : activeBomb.getSpreadFlame()) {
                    flameX = spreadFlame.getX();
                    flameY = spreadFlame.getY();
                    flameRect = new Rectangle(flameX, flameY, SPRITE_SIZE, SPRITE_SIZE);
                    if (isOverlapping(bomberRect, flameRect)) {
                        System.out.println("You're dead meat");
                        touched = true;
                        break;
                    }
                }
                if(touched) {
                    System.out.println("Loop?");
                    break;
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
        } else { // If he's dead, process death animation
            currentPlayerTick++;
            if (currentPlayerTick == playerFrameInterval) {
                if (!gameOver) {
                    currentPlayerTick = 0;
                    currentPlayerFrameIndex++;
                }

                if (currentPlayerFrameIndex == 7) {
                    gameOver = true;
                }
                System.out.println(currentPlayerFrameIndex);
            }
        }

    }

    public void drawBomber(Graphics2D g) {
        switch (bomberState) {
            case 1 -> g.drawImage(bomberSpriteRight[currentPlayerFrameIndex], x, y, TILE_SIZE * 3, TILE_SIZE * 3, null);
            case 2 -> g.drawImage(bomberSpriteUp[currentPlayerFrameIndex], x, y, TILE_SIZE * 3, TILE_SIZE * 3, null);
            case 3 -> g.drawImage(bomberSpriteLeft[currentPlayerFrameIndex], x, y, TILE_SIZE * 3, TILE_SIZE * 3, null);
            case 4 -> g.drawImage(bomberSpriteDie[currentPlayerFrameIndex], x, y, TILE_SIZE * 3, TILE_SIZE * 3, null);
            default -> g.drawImage(bomberSpriteDown[currentPlayerFrameIndex], x, y, TILE_SIZE * 3, TILE_SIZE * 3, null);
        }
    }
}

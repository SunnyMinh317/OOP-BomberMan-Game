package main.Entity.Enemies;

import main.Entity.Bomb;
import main.Entity.Enemies.AI.AI;
import main.Entity.Entity;
import main.Entity.Tiles.Flame;
import main.Game;
import main.Level.GameMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {
    protected GameMap map;
    protected boolean lookingLeft = true;
    // enemy state
    protected AI ai;
    protected int movingDirection = -1;
    protected boolean isShocked = false, isFading = false;

    // texture
    protected BufferedImage[] enemySpriteLeft = new BufferedImage[3];
    protected BufferedImage[] enemySpriteRight = new BufferedImage[3];
    protected BufferedImage[] enemyDeathSprite = new BufferedImage[4];

    // animating purpose
    protected int enemyUpdateTick = 0, enemyUpdateInterval = 8, enemyFrameIndex = 0;
    protected int enemyShockTick = 0, enemyShockInterval = 90;
    protected int enemyFadeTick = 0, enemyFadeInterval = 15, enemyFadeFrameIndex = 0;

    // moving the enemy purpose

    public Enemy(int x, int y, GameMap map) {
        super(x, y);
        this.map = map;

        enemyDeathSprite[1] = Game.gameTileSheet.getSubimage(16 * 10, 16 * 0, 16, 16);
        enemyDeathSprite[2] = Game.gameTileSheet.getSubimage(16 * 10, 16 * 1, 16, 16);
        enemyDeathSprite[3] = Game.gameTileSheet.getSubimage(16 * 10, 16 * 2, 16, 16);
    }

    private boolean isOverlapping(Rectangle a, Rectangle b) {
        if (a == null || b == null)
            return false;
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

    public void updateEnemy() {
        if (isShocked) {
            enemyShockTick++;
            if (enemyShockTick == enemyShockInterval) {
                isShocked = false;
                isFading = true;
                enemyFadeFrameIndex++;
            }
        } else if (isFading) {
            enemyFadeTick++;
            if (enemyFadeTick == enemyFadeInterval) {
                enemyFadeFrameIndex++;
                enemyFadeTick = 0;
                if (enemyFadeFrameIndex == 4) {
                    isFading = false;
                    map.removeEnemy(this);
                }
            }
        } else {
            enemyUpdateTick++;
            if (enemyUpdateTick == enemyUpdateInterval) {
                enemyUpdateTick = 0;
                enemyFrameIndex++;
                if (enemyFrameIndex == 3) {
                    enemyFrameIndex = 0;
                }
            }

            beginMove();

            for (Bomb activeBomb : map.activeBombs) {
                if (activeBomb.isExploded() && isOverlapping(this.getEntityRect(), activeBomb.getEntityRect())) {
                    isShocked = true;
                    break;
                }
                for (Flame spreadFlame : activeBomb.getSpreadFlame()) {
                    if (isOverlapping(this.getEntityRect(), spreadFlame.getEntityRect())) {
                        isShocked = true;
                        break;
                    }
                }
            }
        }
    }

    public void beginMove() {
        if (movingDirection == -1) {
            int determinedDirection = ai.chooseDirection();
            switch (determinedDirection) {
                case 0:
                    if (canMove(this.x, this.y + this.speed, map.map)) {
                        movingDirection = 0;
                        break;
                    }
                case 1:
                    if (canMove(this.x + this.speed, this.y, map.map)) {
                        movingDirection = 1;
                        lookingLeft = false;
                        break;
                    }
                case 2:
                    if (canMove(this.x, this.y - this.speed, map.map)) {
                        movingDirection = 2;
                        break;
                    }
                case 3:
                    if (canMove(this.x - this.speed, this.y, map.map)) {
                        movingDirection = 3;
                        lookingLeft = true;
                        break;
                    }
                default:
                    break;
            }
            move();
        } else {
            move();
        }
    }

    public void move() {
        if (movingDirection == -1) {
            return;
        } else if (movingDirection == 0) {
            this.y += this.speed;
            if (this.y % 48 == 0) {
                movingDirection = -1;
            }
        } else if (movingDirection == 1) {
            this.x += this.speed;
            if (this.x % 48 == 0) {
                movingDirection = -1;
            }
        } else if (movingDirection == 2) {
            this.y -= this.speed;
            if (this.y % 48 == 0) {
                movingDirection = -1;
            }
        } else if (movingDirection == 3) {
            this.x -= this.speed;
            if (this.x % 48 == 0) {
                movingDirection = -1;
            }
        }
    }

    public void drawEnemy(Graphics2D g) {
        if (isShocked || isFading) {
            g.drawImage(enemyDeathSprite[enemyFadeFrameIndex], this.x - Game.gameCam.getCamX(), this.y - Game.gameCam.getCamY(), 48, 48, null);
        } else {
            if (lookingLeft) {
                g.drawImage(enemySpriteLeft[enemyFrameIndex], this.x - Game.gameCam.getCamX(), this.y - Game.gameCam.getCamY(), 48, 48, null);
            } else {
                g.drawImage(enemySpriteRight[enemyFrameIndex], this.x - Game.gameCam.getCamX(), this.y - Game.gameCam.getCamY(), 48, 48, null);
            }
        }
    }

    protected boolean canMove(int nextX, int nextY, int[][] map) {
        int size = 3 * 16;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 1) / size;

        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;

        return !((map[nextY_1][nextX_1] == 1 || map[nextY_1][nextX_1] == 2 || map[nextY_1][nextX_1] == 3) ||
                (map[nextY_2][nextX_2] == 1 || map[nextY_2][nextX_2] == 2 || map[nextY_2][nextX_2] == 3) ||
                (map[nextY_3][nextX_3] == 1 || map[nextY_3][nextX_3] == 2 || map[nextY_3][nextX_3] == 3) ||
                (map[nextY_4][nextX_4] == 1 || map[nextY_4][nextX_4] == 2 || map[nextY_4][nextX_4] == 3));
    }

    public boolean isShocked() {
        return isShocked;
    }

    public int getMovingDirection() {
        return movingDirection;
    }

    public boolean isFading() {
        return isFading;
    }
}

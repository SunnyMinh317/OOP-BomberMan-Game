package main.Entity.Enemies;

import main.Entity.Bomb;
import main.Entity.Entity;
import main.Entity.Tiles.Flame;
import main.Game;
import main.Level.GameMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {
    protected GameMap map;
    protected int lookingDirection = 0;
    protected boolean isShocked = false, isFading = false;
    protected boolean isMoving;
    protected BufferedImage[] enemySpriteLeft = new BufferedImage[3];
    protected BufferedImage[] enemySpriteRight = new BufferedImage[3];
    protected BufferedImage[] enemyDeathSprite = new BufferedImage[4];

    protected int enemyUpdateTick = 0, enemyUpdateInterval = 5, enemyFrameIndex = 0;
    protected int enemyShockTick = 0, enemyShockInterval = 60;
    protected int enemyFadeTick = 0, enemyFadeInterval = 15, enemyFadeFrameIndex = 0;

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

    public void drawEnemy(Graphics2D g) {
        if (isShocked || isFading) {
            g.drawImage(enemyDeathSprite[enemyFadeFrameIndex], this.x, this.y, 48, 48, null);
        } else {
            g.drawImage(enemySpriteLeft[0], this.x, this.y, 48, 48, null);
        }
    }

    public boolean isMoving() {
        return isMoving;
    }
}

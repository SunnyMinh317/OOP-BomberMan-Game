package main.Entity.Tiles;

import main.Game;
import main.Entity.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Flame extends Tile {
    private int direction; // 0 = SOUTH, 1 = EAST, 2 = NORTH, 3 = WEST
    private boolean isEnd;
    private BufferedImage[] flameTexture = new BufferedImage[4];
    private int currentExplosionTick = 0, explosionAnimInterval = 10, currentExplosionFrameIndex = 0;

    public Flame(int x, int y, boolean isEnd, int direction) {
        super(x, y);
        this.isEnd = isEnd;
        this.direction = direction;

        if (!isEnd) {
            if (direction == 0) {
                flameTexture[0] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[1] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[2] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE * 12, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[3] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE * 12, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
            } else if (direction == 1) {
                flameTexture[0] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[1] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 8, Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[2] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE * 11, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[3] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 8, Game.TILESHEET_BLOCK_SIZE * 11, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
            } else if (direction == 2) {
                flameTexture[0] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE * 5, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[1] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE * 5, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[2] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE * 10, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[3] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE * 10, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
            } else if (direction == 3) {
                flameTexture[0] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 1, Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[1] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[2] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 1, Game.TILESHEET_BLOCK_SIZE * 11, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[3] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE * 11, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
            }
        } else {
            if (direction == 0) {
                flameTexture[0] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE * 8, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[1] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE * 8, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[2] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE * 13, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[3] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE * 13, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
            } else if (direction == 1) {
                flameTexture[0] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 4, Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[1] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 9, Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[2] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 4, Game.TILESHEET_BLOCK_SIZE * 11, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[3] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 9, Game.TILESHEET_BLOCK_SIZE * 11, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
            } else if (direction == 2) {
                flameTexture[0] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE * 4, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[1] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE * 4, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[2] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE * 9, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[3] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE * 9, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
            } else if (direction == 3) {
                flameTexture[0] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 0, Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[1] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 5, Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[2] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 0, Game.TILESHEET_BLOCK_SIZE * 11, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
                flameTexture[3] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 5, Game.TILESHEET_BLOCK_SIZE * 11, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
            }
        }
    }

    public void updateFlame() {
        currentExplosionTick++;
        if (currentExplosionTick == explosionAnimInterval) {
            currentExplosionTick = 0;
            currentExplosionFrameIndex++;
        }
    }

    public void drawFlame(Graphics2D g) {
        g.drawImage(flameTexture[currentExplosionFrameIndex], this.x, this.y, 48, 48, null);
    }
}

package main.Entity.Tiles;

import main.Entity.Tiles.Tile;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DestroyedBrick extends Tile {
    private BufferedImage[] brickShatterTexture = new BufferedImage[7];
    private int currentBrickBreakingTick = 0, brickBreakingAnimInterval = 8, currentBrickBreakingFrame = 0;

    public DestroyedBrick(int x, int y) {
        super(x, y);

        for (int i = 0; i < 6; i++) {
            brickShatterTexture[i] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * (5 + i), Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
            brickShatterTexture[6] = Game.gameTileSheet.getSubimage(Game.TILESHEET_BLOCK_SIZE * 5, Game.TILESHEET_BLOCK_SIZE * 4, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
        }
    }

    public void updateDestroyedBrick() {
        currentBrickBreakingTick++;
        if (currentBrickBreakingTick == brickBreakingAnimInterval) {
            currentBrickBreakingTick = 0;
            currentBrickBreakingFrame++;
            if (currentBrickBreakingFrame == 7) {
                currentBrickBreakingFrame = 6;
            }
        }
    }

    public void drawDestroyedBrick(Graphics2D g) {
        g.drawImage(brickShatterTexture[currentBrickBreakingFrame], this.x - Game.gameCam.getCamX(), this.y - Game.gameCam.getCamY(), 48, 48, null);
    }
}

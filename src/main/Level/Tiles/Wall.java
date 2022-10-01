package main.Level.Tiles;

import main.Game;
import main.Level.Tile;

public class Wall extends Tile {
    public Wall() {
        tileTexture = Game.gameTileSheet.getSubimage
                (Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
    }

    public Wall(int x, int y, boolean isSolid) {
        tileTexture = Game.gameTileSheet.getSubimage
                (Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
        this.x = x;
        this.y = y;
        this.isSolid = isSolid;
    }
}

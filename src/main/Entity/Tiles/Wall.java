package main.Entity.Tiles;

import main.Game;

public class Wall extends Tile {
    public Wall() {
        tileTexture = Game.gameTileSheet.getSubimage
                (Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
    }

    public Wall(int x, int y) {
        super(x,y);
        tileTexture = Game.gameTileSheet.getSubimage
                (Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
    }
}

package main.Entity.Tiles;

import main.Game;

public class Brick extends Tile {
    public Brick() {
        tileTexture = Game.gameTileSheet.getSubimage
                (Game.TILESHEET_BLOCK_SIZE * 4, Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
    }

    public Brick(int x, int y) {
        super(x, y);
        tileTexture = Game.gameTileSheet.getSubimage
                (Game.TILESHEET_BLOCK_SIZE * 4, Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE);
    }
}

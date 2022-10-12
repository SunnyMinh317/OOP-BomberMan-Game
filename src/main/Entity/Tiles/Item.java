package main.Entity.Tiles;

import main.Game;

public class Item extends Tile {
    // Speed Item = 4
    // Flare Item = 5
    // Bomb Item = 6
    public int itemType;

    public Item(int x, int y, int itemType) {
        super(x, y);
        if (itemType == 4) {
            tileTexture = Game.gameTileSheet.getSubimage(
                    Game.TILESHEET_BLOCK_SIZE * 8, Game.TILESHEET_BLOCK_SIZE * 0, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE
            );
        } else if (itemType == 5) {
            tileTexture = Game.gameTileSheet.getSubimage(
                    Game.TILESHEET_BLOCK_SIZE * 7, Game.TILESHEET_BLOCK_SIZE * 0, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE
            );
        } else if (itemType == 6) {
            tileTexture = Game.gameTileSheet.getSubimage(
                    Game.TILESHEET_BLOCK_SIZE * 6, Game.TILESHEET_BLOCK_SIZE * 0, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE
            );
        } else if (itemType == 7) {
            tileTexture = Game.gameTileSheet.getSubimage(
                    Game.TILESHEET_BLOCK_SIZE * 9, Game.TILESHEET_BLOCK_SIZE * 0, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE
            );
        }
    }
}

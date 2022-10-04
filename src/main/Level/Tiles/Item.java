package main.Level.Tiles;

import main.Game;
import main.Level.Tile;

public class Item extends Tile {
    // Speed Item = 4
    // Flare Item = 5
    // Bomb Item = 6
    public int itemType;

    public Item(int x, int y, int itemType) {
        super(x, y);
        if (itemType == 4) {
            tileTexture = Game.gameTileSheet.getSubimage(
                    // Just a dummy texture, needs fixing
                    Game.TILESHEET_BLOCK_SIZE * 4, Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE
            );
        } else if (itemType == 5) {
            tileTexture = Game.gameTileSheet.getSubimage(
                    // Just a dummy texture, needs fixing
                    Game.TILESHEET_BLOCK_SIZE * 3, Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE
            );
        } else if (itemType == 6) {
            tileTexture = Game.gameTileSheet.getSubimage(
                    // Just a dummy texture, needs fixing
                    Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE * 2, Game.TILESHEET_BLOCK_SIZE, Game.TILESHEET_BLOCK_SIZE
            );
        }
    }
}

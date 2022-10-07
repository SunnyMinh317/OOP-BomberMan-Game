package main.Entity.Enemies;

import main.Game;
import main.Level.GameMap;

public class Oneal extends Enemy {
    public Oneal(int x, int y, GameMap map) {
        super(x, y, map);

        enemyDeathSprite[0] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 3, 16, 16);

        enemySpriteLeft[0] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 0, 16, 16);
        enemySpriteLeft[1] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 1, 16, 16);
        enemySpriteLeft[2] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 2, 16, 16);

        enemySpriteRight[0] = Game.gameTileSheet.getSubimage(16 * 12, 16 * 0, 16, 16);
        enemySpriteRight[1] = Game.gameTileSheet.getSubimage(16 * 12, 16 * 1, 16, 16);
        enemySpriteRight[2] = Game.gameTileSheet.getSubimage(16 * 12, 16 * 2, 16, 16);
    }
}

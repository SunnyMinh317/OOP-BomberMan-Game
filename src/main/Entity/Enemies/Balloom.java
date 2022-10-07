package main.Entity.Enemies;

import main.Game;
import main.Level.GameMap;

public class Balloom extends Enemy {
    public Balloom(int x, int y, GameMap map) {
        super(x, y, map);

        enemyDeathSprite[0] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 7, 16, 16);

        enemySpriteLeft[0] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 4, 16, 16);
        enemySpriteLeft[1] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 5, 16, 16);
        enemySpriteLeft[2] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 6, 16, 16);

        enemySpriteRight[0] = Game.gameTileSheet.getSubimage(16 * 12, 16 * 4, 16, 16);
        enemySpriteRight[1] = Game.gameTileSheet.getSubimage(16 * 12, 16 * 5, 16, 16);
        enemySpriteRight[2] = Game.gameTileSheet.getSubimage(16 * 12, 16 * 6, 16, 16);
    }
}

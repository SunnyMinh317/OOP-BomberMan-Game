package main.Entity.Enemies;

import main.Entity.Enemies.AI.AIMedium;
import main.Game;
import main.Level.GameMap;

public class Minvo extends Enemy {
    public Minvo(int x, int y, GameMap map) {
        super(x, y, map);

        speed = 3;

        enemyDeathSprite[0] = Game.gameTileSheet.getSubimage(16 * 13, 16 * 3, 16, 16);

        enemySpriteLeft[0] = Game.gameTileSheet.getSubimage(16 * 13, 16 * 0, 16, 16);
        enemySpriteLeft[1] = Game.gameTileSheet.getSubimage(16 * 13, 16 * 1, 16, 16);
        enemySpriteLeft[2] = Game.gameTileSheet.getSubimage(16 * 13, 16 * 2, 16, 16);

        enemySpriteRight[0] = Game.gameTileSheet.getSubimage(16 * 14, 16 * 0, 16, 16);
        enemySpriteRight[1] = Game.gameTileSheet.getSubimage(16 * 14, 16 * 1, 16, 16);
        enemySpriteRight[2] = Game.gameTileSheet.getSubimage(16 * 14, 16 * 2, 16, 16);

        ai = new AIMedium(this);
    }
}

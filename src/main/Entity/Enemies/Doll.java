package main.Entity.Enemies;

import main.Entity.Enemies.AI.AILow;
import main.Game;
import main.Level.GameMap;

public class Doll extends Enemy {
    public Doll(int x, int y, GameMap map) {
        super(x, y, map);

        speed = 4;

        enemyDeathSprite[0] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 11, 16, 16);

        enemySpriteLeft[0] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 8, 16, 16);
        enemySpriteLeft[1] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 9, 16, 16);
        enemySpriteLeft[2] = Game.gameTileSheet.getSubimage(16 * 11, 16 * 10, 16, 16);

        enemySpriteRight[0] = Game.gameTileSheet.getSubimage(16 * 12, 16 * 8, 16, 16);
        enemySpriteRight[1] = Game.gameTileSheet.getSubimage(16 * 12, 16 * 9, 16, 16);
        enemySpriteRight[2] = Game.gameTileSheet.getSubimage(16 * 12, 16 * 10, 16, 16);

        ai = new AILow();
    }
}

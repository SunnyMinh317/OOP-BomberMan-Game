package main.Entity.Enemies;

import main.Entity.Enemies.AI.AILow;
import main.Entity.Enemies.AI.AIMedium;
import main.Game;
import main.Level.GameMap;

public class Kondoria extends Enemy {
    public Kondoria(int x, int y, GameMap map) {
        super(x, y, map);

        speed = Game.player.getSpeed() / 2;

        enemyDeathSprite[0] = Game.gameTileSheet.getSubimage(16 * 13, 16 * 11, 16, 16);

        enemySpriteLeft[0] = Game.gameTileSheet.getSubimage(16 * 13, 16 * 8, 16, 16);
        enemySpriteLeft[1] = Game.gameTileSheet.getSubimage(16 * 13, 16 * 9, 16, 16);
        enemySpriteLeft[2] = Game.gameTileSheet.getSubimage(16 * 13, 16 * 10, 16, 16);

        enemySpriteRight[0] = Game.gameTileSheet.getSubimage(16 * 14, 16 * 8, 16, 16);
        enemySpriteRight[1] = Game.gameTileSheet.getSubimage(16 * 14, 16 * 9, 16, 16);
        enemySpriteRight[2] = Game.gameTileSheet.getSubimage(16 * 14, 16 * 10, 16, 16);

        ai = new AIMedium(this);
    }

    @Override
    protected boolean canMove(int nextX, int nextY, int[][] map) {
        int size = 3 * 16;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 1) / size;

        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;

        return !((map[nextY_1][nextX_1] == 1 || map[nextY_1][nextX_1] == 3) ||
                (map[nextY_2][nextX_2] == 1 || map[nextY_2][nextX_2] == 3) ||
                (map[nextY_3][nextX_3] == 1 || map[nextY_3][nextX_3] == 3) ||
                (map[nextY_4][nextX_4] == 1 || map[nextY_4][nextX_4] == 3));
    }
}

package main.Level;

import main.Level.Tiles.Brick;
import main.Level.Tiles.Wall;

import java.awt.*;
import java.util.Random;

public class GameMap {
    public int[][] map;

    public GameMap() {
        map = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 15; j++) {
                if (map[i][j] == 0) {
                    if (new Random().nextInt(10) < 5) {
                        map[i][j] = 2;
                    }
                }
            }
        }

        map[1][1] = 0;
        map[1][2] = 0;
        map[2][1] = 0;
    }

    public void drawMap(Graphics2D g) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 15; j++) {
                if (map[i][j] == 1) {
                    Wall drawnWall = new Wall(j * 48, i * 48, true);
                    drawnWall.drawTile(g);
                } else if (map[i][j] == 2) {
                    Brick drawnBrick = new Brick(j * 48, i * 48, true);
                    drawnBrick.drawTile(g);
                }
            }
        }
    }
}

package main.Level;

import main.Level.Tiles.Brick;
import main.Level.Tiles.Item;
import main.Level.Tiles.Wall;

import java.awt.*;
import java.util.Random;

public class GameMap {
    // Grass = 0
    // Hard brick = 1
    // Brick = 2
    // Bomb = 3
    // Speed Item = 4
    // Flare Item = 5
    // Bomb Item = 6
    public int[][] map;

    public GameMap() {
        map = new int[][]{
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

        // Boolean values for limiting number of each item at 1
        boolean hasSpeedItem = false;
        boolean hasFlareItem = false;
        boolean hasBombItem = false;

        // Generate speed item at random place on map
        while (!hasSpeedItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(13);
            int randomJ = rand.nextInt(15);

            if (map[randomI][randomJ] == 0) {
                map[randomI][randomJ] = 4;
                hasSpeedItem = true;
            }
        }

        // Generate flare item at random place on map
        while (!hasFlareItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(13);
            int randomJ = rand.nextInt(15);

            if (map[randomI][randomJ] == 0) {
                map[randomI][randomJ] = 5;
                hasFlareItem = true;
            }
        }

        // Generate bomb item at random place on map
        while (!hasBombItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(13);
            int randomJ = rand.nextInt(15);

            if (map[randomI][randomJ] == 0) {
                map[randomI][randomJ] = 6;
                hasBombItem = true;
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
                    Wall drawnWall = new Wall(j * 48, i * 48);
                    drawnWall.drawTile(g);
                } else if (map[i][j] == 2) {
                    Brick drawnBrick = new Brick(j * 48, i * 48);
                    drawnBrick.drawTile(g);
                } else if (map[i][j] == 4) {
                    Item drawnSpeedItem = new Item(j * 48, i * 48, 4);
                    drawnSpeedItem.drawTile(g);
                } else if (map[i][j] == 5) {
                    Item drawnFlareItem = new Item(j * 48, i * 48, 5);
                    drawnFlareItem.drawTile(g);
                } else if (map[i][j] == 6) {
                    Item drawnFlareItem = new Item(j * 48, i * 48, 6);
                    drawnFlareItem.drawTile(g);
                }
            }
        }
    }

    public void regenerateMap() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 15; j++) {
                if (map[i][j] != 0 && map[i][j] != 1) {
                    map[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 15; j++) {
                if (map[i][j] == 0) {
                    if (new Random().nextInt(10) < 5) {
                        map[i][j] = 2;
                    }
                }
            }
        }

        boolean hasSpeedItem = false;
        while (!hasSpeedItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(13);
            int randomJ = rand.nextInt(15);

            if (map[randomI][randomJ] == 0) {
                map[randomI][randomJ] = 4;
                hasSpeedItem = true;
            }
        }

        boolean hasFlareItem = false;
        while (!hasFlareItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(13);
            int randomJ = rand.nextInt(15);

            if (map[randomI][randomJ] == 0) {
                map[randomI][randomJ] = 5;
                hasFlareItem = true;
            }
        }

        boolean hasBombItem = false;
        while (!hasBombItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(13);
            int randomJ = rand.nextInt(15);

            if (map[randomI][randomJ] == 0) {
                map[randomI][randomJ] = 6;
                hasBombItem = true;
            }
        }

        map[1][1] = 0;
        map[1][2] = 0;
        map[2][1] = 0;
    }
}
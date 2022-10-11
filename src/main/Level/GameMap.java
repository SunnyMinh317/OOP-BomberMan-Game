package main.Level;

import main.Entity.Bomb;
import main.Entity.Enemies.Balloom;
import main.Entity.Enemies.Enemy;
import main.Entity.Enemies.Oneal;
import main.Entity.Tiles.Brick;
import main.Entity.Tiles.Item;
import main.Entity.Tiles.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameMap {
    // Grass = 0
    // Hard brick = 1
    // Brick = 2
    // Bomb = 3
    // Speed Item = 4
    // Flare Item = 5
    // Bomb Item = 6

    // Balloom = 10
    // Oneal = 11
    public int mapX, mapY;
    public int[][] map;
    public int[][] itemLayer;
    private int levelWidth, levelHeight;
    public ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    public ArrayList<Bomb> activeBombs = new ArrayList<Bomb>();

    public GameMap() {
        map = MapLoader.loadLevel("res/levels/Level1.txt");
        levelWidth = MapLoader.getLevelData("res/levels/Level1.txt", 1);
        levelHeight = MapLoader.getLevelData("res/levels/Level1.txt", 2);

        itemLayer = new int[levelHeight][levelWidth];

        for (int i = 0; i < levelHeight; i++) {
            for (int j = 0; j < levelWidth; j++) {
                itemLayer[i][j] = 0;
                if (map[i][j] == 10) {
                    enemyList.add(new Balloom(j * 48, i * 48, this));
                    map[i][j] = 0;
                } else if (map[i][j] == 11) {
                    enemyList.add(new Oneal(j * 48, i * 48, this));
                    map[i][j] = 0;
                } else if (map[i][j] == 4) {
                    itemLayer[i][j] = 4;
                    map[i][j] = 2;
                } else if (map[i][j] == 5) {
                    itemLayer[i][j] = 5;
                    map[i][j] = 2;
                } else if (map[i][j] == 6) {
                    itemLayer[i][j] = 6;
                    map[i][j] = 2;
                }
            }
        }

        map[1][1] = 0;
        map[1][2] = 0;
        map[2][1] = 0;

        // Boolean values for limiting number of each item at 1
//        boolean hasSpeedItem = false;
//        boolean hasFlareItem = false;
//        boolean hasBombItem = false;
//
//        // Generate speed item at random place on map
//        while (!hasSpeedItem) {
//            Random rand = new Random();
//            int randomI = rand.nextInt(13);
//            int randomJ = rand.nextInt(15);
//
//            if (map[randomI][randomJ] == 2) {
//                itemLayer[randomI][randomJ] = 4;
//                hasSpeedItem = true;
//            }
//        }
//
//        // Generate flare item at random place on map
//        while (!hasFlareItem) {
//            Random rand = new Random();
//            int randomI = rand.nextInt(13);
//            int randomJ = rand.nextInt(15);
//
//            if (map[randomI][randomJ] == 2) {
//                itemLayer[randomI][randomJ] = 5;
//                hasFlareItem = true;
//            }
//        }
//
//        // Generate bomb item at random place on map
//        while (!hasBombItem) {
//            Random rand = new Random();
//            int randomI = rand.nextInt(13);
//            int randomJ = rand.nextInt(15);
//
//            if (map[randomI][randomJ] == 2) {
//                itemLayer[randomI][randomJ] = 6;
//                hasBombItem = true;
//            }
//        }
    }

    public void drawMap(Graphics2D g) {
        for (int i = 0; i < levelHeight; i++) {
            for (int j = 0; j < levelWidth; j++) {
                if (itemLayer[i][j] == 4) {
                    Item drawnSpeedItem = new Item(j * 48, i * 48, 4);
                    drawnSpeedItem.drawTile(g);
                } else if (itemLayer[i][j] == 5) {
                    Item drawnFlareItem = new Item(j * 48, i * 48, 5);
                    drawnFlareItem.drawTile(g);
                } else if (itemLayer[i][j] == 6) {
                    Item drawnFlareItem = new Item(j * 48, i * 48, 6);
                    drawnFlareItem.drawTile(g);
                }

                if (map[i][j] == 1) {
                    Wall drawnWall = new Wall(j * 48, i * 48);
                    drawnWall.drawTile(g);
                } else if (map[i][j] == 2) {
                    Brick drawnBrick = new Brick(j * 48, i * 48);
                    drawnBrick.drawTile(g);
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
                itemLayer[i][j] = 0;
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

        map[1][1] = 0;
        map[1][2] = 0;
        map[2][1] = 0;

        boolean hasSpeedItem = false;
        while (!hasSpeedItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(13);
            int randomJ = rand.nextInt(15);

            if (map[randomI][randomJ] == 2) {
                itemLayer[randomI][randomJ] = 4;
                hasSpeedItem = true;
            }
        }

        boolean hasFlareItem = false;
        while (!hasFlareItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(13);
            int randomJ = rand.nextInt(15);

            if (map[randomI][randomJ] == 2) {
                itemLayer[randomI][randomJ] = 5;
                hasFlareItem = true;
            }
        }

        boolean hasBombItem = false;
        while (!hasBombItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(13);
            int randomJ = rand.nextInt(15);

            if (map[randomI][randomJ] == 2) {
                itemLayer[randomI][randomJ] = 6;
                hasBombItem = true;
            }
        }
    }

    public void removeEnemy(Enemy e) {
        System.out.println("Func called!");
        for (int i = 0; i < enemyList.size(); i++) {
            if (e.getX() == enemyList.get(i).getX() && e.getY() == enemyList.get(i).getY()) {
                enemyList.remove(i);
                System.out.println("enemy removed!");
                return;
            }
        }
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }
}

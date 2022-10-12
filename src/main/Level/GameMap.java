package main.Level;

import main.Entity.Bomb;
import main.Entity.Enemies.*;
import main.Entity.Tiles.Brick;
import main.Entity.Tiles.Item;
import main.Entity.Tiles.Wall;
import main.GUI.GamePanel;
import main.GUI.Sound;
import main.Game;

import java.awt.*;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Random;

public class GameMap {
    // Grass = 0, Hard brick = 1, Brick = 2, Bomb = 3, Speed Item = 4, Flare Item = 5, Bomb Item = 6
    // Balloom = 10, Oneal = 11, Doll = 12, Minvo = 13, Ghost = 14, Kondoria = 15

    public int mapX, mapY;
    public int[][] map;
    public int[][] itemLayer;
    private int levelWidth, levelHeight;
    public ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    public ArrayList<Bomb> activeBombs = new ArrayList<Bomb>();
    public boolean levelComplete = false;
    public static int level;
    public static final int MAX_LEVEL = 2;
    public static int currentLevel;
    public static boolean infinityActivated = false;

    public GameMap() {
        level = 1;
        loadMap(level);
    }

    /**Pass in -10 for a random map. */
    public void loadMap(int levelNo) {
        if (levelNo == -10) {
            Random rand = new Random();

            levelWidth = 15 + rand.nextInt(30 - 15);
            levelHeight = 13 + rand.nextInt(30 - 13);

            while (levelWidth % 2 == 0 || levelHeight % 2 == 0) {
                levelWidth = 15 + rand.nextInt(30 - 15);
                levelHeight = 13 + rand.nextInt(30 - 13);
            }

            map = generateRandomMap(levelWidth, levelHeight);
        } else {
            currentLevel = levelNo;
            String levelPath = "res/levels/Level" + levelNo + ".txt";

            map = MapLoader.loadLevel(levelPath);
            levelWidth = MapLoader.getLevelData(levelPath, 1);
            levelHeight = MapLoader.getLevelData(levelPath, 2);
        }

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
                } else if (map[i][j] == 12) {
                    enemyList.add(new Doll(j * 48, i * 48, this));
                    map[i][j] = 0;
                } else if (map[i][j] == 13) {
                    enemyList.add(new Minvo(j * 48, i * 48, this));
                    map[i][j] = 0;
                } else if (map[i][j] == 14) {
                    enemyList.add(new Ghost(j * 48, i * 48, this));
                    map[i][j] = 0;
                } else if (map[i][j] == 15) {
                    enemyList.add(new Kondoria(j * 48, i * 48, this));
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
                } else if (map[i][j] == 7) {
                    itemLayer[i][j] = 7;
                    map[i][j] = 2;
                }
            }
        }

        map[1][1] = 0;
        map[1][2] = 0;
        map[2][1] = 0;
    }

    public void reloadMap() {
        enemyList.clear();
        activeBombs.clear();
        if(!infinityActivated) {
            loadMap(currentLevel);
        } else {
            nextRandomMap();
        }

        levelComplete = false;
        Game.gameCam = new Camera(levelWidth, levelHeight);
    }

    public void nextMap() {
        enemyList.clear();
        activeBombs.clear();
        if(!infinityActivated) {
            loadMap(level);
        } else {
            nextRandomMap();
        }

        levelComplete = false;
        Game.gameCam = new Camera(levelWidth, levelHeight);
    }

    public void nextRandomMap() {
        enemyList.clear();
        activeBombs.clear();
        loadMap(-10);
        levelComplete = false;
        Game.gameCam = new Camera(levelWidth, levelHeight);
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
                } else if (itemLayer[i][j] == 7) {
                    Item drawnFlareItem = new Item(j * 48, i * 48, 7);
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

    public int[][] generateRandomMap(int randomMapWidth, int randomMapHeight) {
        Random random = new Random();

        int[][] newMap = new int[randomMapHeight][randomMapWidth];

        for (int i = 0; i < randomMapWidth; i++) {
            newMap[0][i] = 1;
            newMap[randomMapHeight - 1][i] = 1;
        }
        for (int i = 0; i < randomMapHeight; i++) {
            newMap[i][0] = 1;
            newMap[i][randomMapWidth - 1] = 1;
        }
        for (int i = 1; i < randomMapHeight - 1; i++) {
            for (int j = 1; j < randomMapWidth - 1; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    newMap[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < randomMapHeight; i++) {
            for (int j = 0; j < randomMapWidth; j++) {
                if (newMap[i][j] == 0) {
                    if (new Random().nextInt(6) < 1) {
                        newMap[i][j] = 2;
                    }
                }
            }
        }

        newMap[1][1] = 0;
        newMap[1][2] = 0;
        newMap[2][1] = 0;

        boolean hasSpeedItem = false;
        while (!hasSpeedItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(randomMapHeight);
            int randomJ = rand.nextInt(randomMapWidth);

            if (newMap[randomI][randomJ] == 2) {
                newMap[randomI][randomJ] = 4;
                hasSpeedItem = true;
            }
        }

        boolean hasFlareItem = false;
        while (!hasFlareItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(randomMapHeight);
            int randomJ = rand.nextInt(randomMapWidth);

            if (newMap[randomI][randomJ] == 2) {
                newMap[randomI][randomJ] = 5;
                hasFlareItem = true;
            }
        }

        boolean hasBombItem = false;
        while (!hasBombItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(randomMapHeight);
            int randomJ = rand.nextInt(randomMapWidth);

            if (newMap[randomI][randomJ] == 2) {
                newMap[randomI][randomJ] = 6;
                hasBombItem = true;
            }
        }

        boolean hasPortalItem = false;
        while (!hasPortalItem) {
            Random rand = new Random();
            int randomI = rand.nextInt(randomMapHeight);
            int randomJ = rand.nextInt(randomMapWidth);

            if (newMap[randomI][randomJ] == 2) {
                newMap[randomI][randomJ] = 7;
                hasPortalItem = true;
            }
        }

        for (int i = 0; i < randomMapHeight; i++) {
            for (int j = 0; j < randomMapWidth; j++) {
                if (newMap[i][j] == 0 && i > 5 && j > 5) {
                    int decider = new Random().nextInt(200);
                    if (decider == 0) {
                        newMap[i][j] = 10;
                    } else if (decider == 1) {
                        newMap[i][j] = 11;
                    } else if (decider == 2) {
                        newMap[i][j] = 12;
                    } else if (decider == 3) {
                        newMap[i][j] = 13;
                    } else if (decider == 4) {
                        newMap[i][j] = 14;
                    } else if (decider == 5) {
                        newMap[i][j] = 15;
                    }
                }
            }
        }

        return newMap;
    }

    public void removeEnemy(Enemy e) {
        for (int i = 0; i < enemyList.size(); i++) {
            if (e.getX() == enemyList.get(i).getX() && e.getY() == enemyList.get(i).getY()) {
                enemyList.remove(i);
                if (enemyList.isEmpty()) {
                    GamePanel.playSFX(7);
                }
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

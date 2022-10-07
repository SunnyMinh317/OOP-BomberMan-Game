package main;

import main.Entity.Bomb;
import main.Entity.Bomber;
import main.Entity.Enemies.Balloom;
import main.Entity.Enemies.Enemy;
import main.GUI.GamePanel;
import main.Input.Keyboard;
import main.Level.GameMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Game {
    public static final int TILESHEET_BLOCK_SIZE = 16;

    GamePanel gp;
    Keyboard kh;
    public static BufferedImage gameTileSheet;

    Bomber player;
    GameMap gameMap;

    public Game(GamePanel gp, Keyboard kh) {
        this.gp = gp;
        this.kh = kh;
        loadGameAssets();
        Bomber.loadBomberSprite();
        gameMap = new GameMap();
        player = new Bomber(gp, kh);
        Bomb.loadBombImage();
    }

    // Load the tilesheet
    private void loadGameAssets() {
        try {
            gameTileSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sheets.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGame() {
        if (!player.isGameOver()) {
            // Update the player
            player.updateBomber(gameMap.map, gameMap.itemLayer, gameMap.activeBombs);

            // Update the bombs
            for (int i = 0; i < gameMap.activeBombs.size(); i++) {
                gameMap.activeBombs.get(i).updateBomb(gameMap.activeBombs, gameMap.map);
            }

            // Update the enemies
            for (int i = 0; i < gameMap.enemyList.size(); i++) {
                gameMap.enemyList.get(i).updateEnemy();
            }

            // Testing only
            if (kh.pPress) {
                gameMap.regenerateMap();
            }
        }
    }

    public void drawGame(Graphics2D g) {
        this.gameMap.drawMap(g);
        for (Bomb activeBomb : gameMap.activeBombs) {
            activeBomb.drawBomb(g);
        }
        for (Enemy selectedEnemy : gameMap.enemyList) {
            selectedEnemy.drawEnemy(g);
        }
        player.drawBomber(g);
    }
}

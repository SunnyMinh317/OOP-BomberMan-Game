package main;

import main.Entity.Bomb;
import main.Entity.Bomber;
import main.Entity.Enemies.Balloom;
import main.Entity.Enemies.Enemy;
import main.GUI.GamePanel;
import main.GUI.UI;
import main.Input.Keyboard;
import main.Input.Mouse;
import main.Level.Camera;
import main.Level.GameMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class Game {
    public static final int TILESHEET_BLOCK_SIZE = 16;

    GamePanel gp;
    Keyboard kh;
    public static BufferedImage gameTileSheet;
    public static Camera gameCam;
    public static Bomber player;
    GameMap gameMap;
    Graphics2D g2;

    public Game(GamePanel gp, Keyboard kh) {
        this.gp = gp;
        this.kh = kh;
        loadGameAssets();
        Bomber.loadBomberSprite();
        player = new Bomber(gp, kh);
        gameMap = new GameMap();
        gameCam = new Camera(gameMap.getLevelWidth(), gameMap.getLevelHeight());
        Bomb.loadBombImage();

    }

    // Load the tilesheet
    private void loadGameAssets() {
        GamePanel.playSFX(6);
        try {
            gameTileSheet = ImageIO.read(new File("res/sheets.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartGame() {
        GameMap.level = 1;
        GameMap.currentLevel = 1;
        player.reviveBomber();
        gameMap.reloadMap();
        gp.restart();
    }

    public void startInfinityMode() {
        GameMap.infinityActivated = true;
        GameMap.currentLevel = 999;
        gameMap.nextRandomMap();
        gp.restart();
    }

    public void restartLevel() {
        GameMap.level = GameMap.currentLevel;
        player.reviveBomber();
        gameMap.reloadMap();
        gp.restart();
    }

    public void updateGame() {
        if (gp.gameState == gp.PLAY_STATE) {

            // Update the player
            player.updateBomber(gameMap, gameMap.itemLayer);
            gameCam.updateCamera(player.getX(), player.getY());

            // Update the bombs
            for (int i = 0; i < gameMap.activeBombs.size(); i++) {
                gameMap.activeBombs.get(i).updateBomb(gameMap.activeBombs, gameMap.map);
            }

            // Update the enemies
            for (int i = 0; i < gameMap.enemyList.size(); i++) {
                gameMap.enemyList.get(i).updateEnemy();
            }

            if (gameMap.enemyList.isEmpty()) {
                gameMap.levelComplete = true;
            }
        }

        // Press P to pause game
        if (kh.pPress) {
            if (gp.gameState == gp.PLAY_STATE) {
                gp.gameState = gp.PAUSE_STATE;
            } else if (gp.gameState == gp.PAUSE_STATE) {
                gp.gameState = gp.PLAY_STATE;
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

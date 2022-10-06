package main;

import main.Entity.Bomb;
import main.Entity.Bomber;
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

    private ArrayList<Bomb> activeBombs = new ArrayList<Bomb>();

    GamePanel gp;
    Keyboard kh;
    public static BufferedImage gameTileSheet;

    Bomber player;
    GameMap gameMap = new GameMap();

    public Game(GamePanel gp, Keyboard kh) {
        this.gp = gp;
        this.kh = kh;
        loadGameAssets();
        Bomber.loadBomberSprite();
        player = new Bomber(gp, kh);
        Bomb.loadBombImage();
    }

    private void loadGameAssets() {
        try {
            gameTileSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sheets.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGame() {
        player.updateBomber(gameMap.map, gameMap.itemLayer, activeBombs);
        for (int i = 0; i < activeBombs.size(); i++) {
            activeBombs.get(i).updateBomb(activeBombs, gameMap.map);
        }
        if (kh.pPress) {
            gameMap.regenerateMap();
        }
    }

    public void drawGame(Graphics2D g) {
        this.gameMap.drawMap(g);
        for (int i = 0; i < activeBombs.size(); i++) {
            activeBombs.get(i).drawBomb(g);
        }
        player.drawBomber(g);
    }
}

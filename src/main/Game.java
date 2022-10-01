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
    public ArrayList<Bomb> bombs;

    GamePanel gp;
    Keyboard kh;
    public static BufferedImage gameTileSheet;

    Bomber player;
    GameMap gameMap = new GameMap();

    public Game(GamePanel gp, Keyboard kh) {
        this.gp = gp;
        this.kh = kh;
        loadGameAssets();
        player = new Bomber(gp, kh);
    }

    private void loadGameAssets() {
        try {
            gameTileSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sheets.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGame() {
        player.updateBomber(gameMap.map);
    }

    public void drawGame(Graphics2D g) {
        this.gameMap.drawMap(g);
        player.drawBomber(g);
    }
}

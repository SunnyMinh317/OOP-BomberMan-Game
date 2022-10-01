package main;

import main.Level.GameMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Game {
    public static final int TILESHEET_BLOCK_SIZE = 16;
    public static BufferedImage gameTileSheet;
    public GameMap gameMap = new GameMap();
    public Game() {
        loadGameTileSheet();
    }

    private void loadGameTileSheet() {
        try {
            gameTileSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sheets.png")));
            System.out.println("Tilesheet loaded!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

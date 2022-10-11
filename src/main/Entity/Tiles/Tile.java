package main.Entity.Tiles;

import main.Entity.Entity;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile extends Entity {
    protected BufferedImage tileTexture;

    protected Tile() {
    }

    protected Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawTile(Graphics2D g) {

        g.drawImage(tileTexture, this.x - Game.gameCam.getCamX(), this.y - Game.gameCam.getCamY(), 48, 48, null);
    }
}

package main.Level;

import main.Entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends Entity {
    protected BufferedImage tileTexture;
    protected boolean isSolid;

    protected Tile() {
    }

    protected Tile(int x, int y, boolean isSolid) {
        this.x = x;
        this.y = y;
        this.isSolid = isSolid;
    }

    public void drawTile(Graphics2D g) {
        g.drawImage(tileTexture, this.x, this.y, 48, 48, null);
    }
}

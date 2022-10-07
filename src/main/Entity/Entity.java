package main.Entity;

import java.awt.*;

public class Entity {
    protected int x, y;
    protected int speed;
    static final int TILE_SIZE = 16;
    static final int SPRITE_SIZE = 3*TILE_SIZE;

    public Entity() {
        this.x = 0;
        this.y = 0;
        this.speed = 0;
    }

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getEntityRect() {
        return new Rectangle(x, y, 48, 48);
    }
}

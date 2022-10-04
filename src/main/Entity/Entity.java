package main.Entity;

public class Entity {
    public int x, y;
    public int speed;
    static final int TILE_SIZE = 16;
    static final int SPRITE_SIZE = 3*TILE_SIZE;

    public Entity() {
        this.x = 0;
        this.y = 0;
        this.speed = 0;
    }

    public Entity(int x, int y) {
        this.x = 0;
        this.y = 0;
        this.speed = 0;
    }
}

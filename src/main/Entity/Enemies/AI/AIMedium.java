package main.Entity.Enemies.AI;

import main.Entity.Bomber;
import main.Entity.Enemies.Enemy;
import main.Game;

public class AIMedium extends AI {
    Bomber player = Game.player;
    Enemy enemy;

    public AIMedium(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public int chooseDirection() {
        if (Bomber.isDead()) {
            return random.nextInt(4);
        }

        int goingVertical = random.nextInt(2);

        if (goingVertical == 1) {
            int v = calculateRowDirection();
            if (v != -1) {
                return v;
            } else {
                return calculateColDirection();
            }
        } else {
            int h = calculateColDirection();
            if (h != -1) {
                return h;
            } else {
                return calculateRowDirection();
            }
        }
    }

    protected int calculateColDirection() {
        if (player.getX() < enemy.getX())
            return 3;
        else if (player.getX() > enemy.getX())
            return 1;
        return -1;
    }

    protected int calculateRowDirection() {
        if (player.getY() < enemy.getY())
            return 2;
        else if (player.getY() > enemy.getY())
            return 0;
        return -1;
    }
}

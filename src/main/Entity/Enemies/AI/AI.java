package main.Entity.Enemies.AI;

import java.util.Random;

public abstract class AI {
    protected Random random = new Random();
    public abstract int chooseDirection();
}

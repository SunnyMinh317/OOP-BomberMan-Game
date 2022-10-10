package main.Entity.Enemies.AI;

public class AILow extends AI {
    @Override
    public int chooseDirection() {
        return random.nextInt(4);
    }
}

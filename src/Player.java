import java.awt.*;

public class Player extends Entity {
    KeyHandler keyHandler;
    GameFrame gameFrame;
    static final int PLAYER_WIDTH = 30;
    static final int PLAYER_HEIGHT = 30;
    static final int SPEED = 5;

    public Player(KeyHandler kh, GameFrame gf) {
        super();
        keyHandler = kh;
        gameFrame = gf;
    }
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void update() {
        if (keyHandler.leftPressed) {
            System.out.println("PLAYER LEFT");
            x -= SPEED;
        } else if (keyHandler.upPressed) {
            y -= SPEED;
        } else if (keyHandler.rightPressed) {
            x += SPEED;
        } else if (keyHandler.downPressed) {
            y += SPEED;
        }
        x=Math.max(x,0);
        x=Math.min(x,GameFrame.WIDTH-PLAYER_WIDTH);
        y=Math.max(y,0);
        y=Math.min(y,GameFrame.HEIGHT-PLAYER_HEIGHT);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
    }
}

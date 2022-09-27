import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Bomb extends Entity {
    BufferedImage bombSprite = new BufferedImage(BOMB_WIDTH, BOMB_HEIGHT, 3);
    KeyHandler keyHandler;
    GameFrame gameFrame;
    Player player;
    private boolean set;
    public boolean exploded = false;
    static final int BOMB_WIDTH = 20;
    static final int BOMB_HEIGHT = 20;
    static final int BOMB_EXPLODE = 100;
    public int countToBoom;

    public Bomb(Player p, KeyHandler kh, GameFrame gf) {
        super();
        player = p;
        keyHandler = kh;
        gameFrame = gf;
    }

    public void setExploded(boolean ex) {
        exploded = ex;
    }

    public void update() {
        int countToExplode = 0;
        if (keyHandler.spacePressed) {
            set = true;
            x = player.getX();
            y = player.getY();
        }
    }

    public boolean getSet() {
        return set;
    }

    public void draw(Graphics2D g2, GameFrame gameFrame) throws IOException {
        if (!exploded) {
            bombSprite = ImageIO.read(new File("./resources/bomberman.jpg"));
        } else {
            bombSprite = ImageIO.read(new File("./resources/bomb_ex.jpg"));
        }

        if (set) {
            g2.drawImage(bombSprite, x, y, 20, 20, gameFrame);
        }

        exploded=false;
    }
}

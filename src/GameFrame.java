import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GameFrame extends JPanel implements Runnable {
    Thread gameThread;
    static final int WIDTH = 640;
    static final int HEIGHT = 480;
    static final int FPS = 60;
    KeyHandler keyHandler = new KeyHandler();
    Player player;
    Bomb bomb;

    GameFrame() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.addKeyListener(keyHandler);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        player = new Player(keyHandler, this);
        bomb = new Bomb(player, keyHandler, this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
            try {
                Thread.sleep((long) 1000/FPS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();
        bomb.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(bomb!=null) {
            try {
                bomb.draw(g2,this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        player.draw(g2);
        g2.dispose();
    }
}


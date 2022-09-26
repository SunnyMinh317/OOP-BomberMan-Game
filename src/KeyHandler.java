import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean leftPressed;
    public boolean upPressed;
    public boolean rightPressed;
    public boolean downPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                System.out.println("LEFT");
                break;
            case KeyEvent.VK_UP:
                upPressed = true;
                System.out.println("UP");
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                System.out.println("RIGHT");
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                System.out.println("DOWN");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                System.out.println("RELEASED LEFT");
                break;
            case KeyEvent.VK_UP:
                upPressed = false;
                System.out.println("RELEASED UP");
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                System.out.println("RELEASED RIGHT");
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                System.out.println("RELEASED DOWN");
                break;
        }
    }
}

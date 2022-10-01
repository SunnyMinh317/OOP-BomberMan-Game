package main.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    public boolean up, down, left, right, space, pPress;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            space = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            pPress = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            space = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            pPress = false;
        }
    }
}

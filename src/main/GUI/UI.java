package main.GUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    JLabel textLabel;
    Font pauseScreen;
    Font subPauseScreen;

    public UI(GamePanel gp) {
        this.gp = gp;
        pauseScreen = new Font("Helvetica", Font.BOLD, 40);
        subPauseScreen = new Font("Helvetica", Font.BOLD, 30);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (gp.gameState == gp.PAUSE_STATE) {
            drawPauseScreen();
        }

        if (gp.gameState == gp.GAME_OVER_STATE) {
            drawGameOverScreen();
        }
    }

    private int getCenter(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.WINDOW_WIDTH / 2 - length / 2;
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        String subText = "Press P to continue";
        g2.setFont(pauseScreen);
        g2.setColor(Color.red);
        int x = getCenter(text);
        int y = gp.WINDOW_HEIGHT / 2 + 10;
        g2.drawString(text, x, y);
        g2.setFont(subPauseScreen);
        g2.setColor(Color.black);
        int subX = getCenter(subText);
        int subY = y + 40;
        g2.drawString(subText, subX, subY);
    }

    public void drawGameOverScreen() {
        String text = "YOU ARE DEAD";
        String subText = "Press R to replay";
        g2.setFont(pauseScreen);
        g2.setColor(Color.red);
        int x = getCenter(text);
        int y = gp.WINDOW_HEIGHT / 2 + 10;
        g2.drawString(text, x, y);
        g2.setFont(subPauseScreen);
        g2.setColor(Color.black);
        int subX = getCenter(subText);
        int subY = y + 40;
        g2.drawString(subText, subX, subY);
    }
}

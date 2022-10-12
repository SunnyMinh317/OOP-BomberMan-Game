package main.GUI;

import main.Game;
import main.Input.Mouse;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UI implements MouseListener {
    GamePanel gp;
    Game mainGame;
    Graphics2D g2;
    JLabel playButton = new JLabel();
    JLabel quitButton = new JLabel();
    JLabel continueButton = new JLabel();
    JLabel tryAgainButton = new JLabel();
    Font pauseScreen;
    Font subPauseScreen;
    Font menuScreen;
    Font subMenuScreen;
    boolean hoverPlay = false, hoverQuit = false, hoverContinue = false;
    Font custom;

    public UI(GamePanel gp, Game mainGame) {
        this.mainGame = mainGame;
        this.gp = gp;
        try {
            pauseScreen = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/PixelGameFont.ttf")).deriveFont(60f).deriveFont(Font.BOLD);
            subPauseScreen = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/PixelGameFont.ttf")).deriveFont(25f).deriveFont(Font.BOLD);
            menuScreen = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/PixelGameFont.ttf")).deriveFont(30f).deriveFont(Font.BOLD);
            subMenuScreen = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/PixelGameFont.ttf")).deriveFont(15f).deriveFont(Font.BOLD);
        } catch (IOException | FontFormatException e) {
            System.out.println("NULL FONT");
        }
    }

    private void createButton(JLabel label, String text, int x, int y) {
        int buttonW = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int buttonH = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        label.setBounds(x, y - buttonH, buttonW, buttonH);
        label.addMouseListener(this);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gp.add(label);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (gp.gameState == gp.PAUSE_STATE) {
            g2.setColor(new Color(0, 0, 0, 90));
            g2.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);
            drawPauseScreen();
        }

        if (gp.gameState == gp.GAME_OVER_STATE) {
            drawGameOverScreen();
        }

        if (gp.gameState == gp.MENU_SCREEN_STATE) {
            drawMenuScreen();
        }
    }

    private int getCenter(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return GamePanel.WINDOW_WIDTH / 2 - length / 2;
    }

    public void drawPauseScreen() {
        // PAUSED title
        String pause = "PAUSED";
        g2.setFont(pauseScreen);
        g2.setColor(Color.red);
        int x = getCenter(pause);
        int y = GamePanel.WINDOW_HEIGHT / 2 + 10;
        g2.drawString(pause, x, y);

        // Continue button
        String pause_continue = "CONTINUE";
        g2.setFont(subPauseScreen);
        g2.setColor(Color.white);
        int subX = getCenter(pause_continue);
        int subY = y + 60;
        createButton(continueButton, pause_continue, subX, subY);
        if (hoverContinue) {
            g2.setColor(new Color(225, 225, 200));
            g2.drawString(pause_continue, subX, subY);
        } else {
            g2.setColor(Color.white);
            g2.drawString(pause_continue, subX, subY);
        }

        // Create quit button
        String quit = "QUIT";
        subX = getCenter(quit);
        subY += 40;
        createButton(quitButton, quit, subX, subY);
        if (hoverQuit) {
            g2.setColor(new Color(225, 225, 200));
            g2.drawString(quit, subX, subY);
        } else {
            g2.setColor(Color.white);
            g2.drawString(quit, subX, subY);
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);
        String text = "YOU ARE DEAD";
        g2.setFont(pauseScreen);
        g2.setColor(Color.red);
        int x = getCenter(text);
        int y = GamePanel.WINDOW_HEIGHT / 2 + 10;
        g2.drawString(text, x, y);

        String subText = "TRY AGAIN";
        g2.setFont(subPauseScreen);
        g2.setColor(Color.white);
        int subX = getCenter(subText);
        int subY = y + 50;
        g2.drawString(subText, subX, subY);
        createButton(tryAgainButton, subText, subX, subY);
    }

    public void drawMenuScreen() {

        // Fill screen with black
        g2.setColor(Color.black);
        g2.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);

        // Add logo on screen
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(new File("res/BombermanLogo.png"));
        } catch (IOException e) {
            System.out.println("NULL");
        }
        int w = logo.getWidth();
        int x = GamePanel.WINDOW_WIDTH / 2 - w / 2;
        g2.drawImage(logo, x, GamePanel.WINDOW_HEIGHT / 2 - 200, null);

        //Create play button
        String play = "PLAY";
        g2.setFont(menuScreen);
        g2.setColor(Color.white);
        int subX = getCenter(play);
        int subY = GamePanel.WINDOW_HEIGHT / 2 + 50;
        g2.drawString(play, subX, subY);
        createButton(playButton, play, subX, subY);

        // Play hover indicator
        String indicator = ">";
        int indicatorX = subX - playButton.getWidth() + 30;
        if (hoverPlay) {
            g2.drawString(indicator, indicatorX, subY);
        } else {
            g2.setColor(Color.black);
            g2.drawString(indicator, indicatorX, subY);
        }


        // Create quit button
        String quit = "QUIT";
        subX = getCenter(quit);
        subY += 60;
        g2.setColor(Color.white);
        g2.drawString(quit, subX, subY);
        createButton(quitButton, quit, subX, subY);

        // Quit hover indicator
        if (hoverQuit) {
            g2.drawString(indicator, indicatorX, subY);
        }


        // Subtitle line
        String subTitle = "BY VU NHAT MINH & LE VU MINH";
        g2.setColor(new Color(225, 225, 200));
        g2.setFont(subMenuScreen);
        subX = getCenter(subTitle);
        g2.drawString(subTitle, subX, subY + 120);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == playButton) {
            GamePanel.stopMusic();
//            GamePanel.playMusic(0);
            gp.gameState = gp.PLAY_STATE;
            gp.remove(playButton);
        }

        if (e.getSource() == continueButton) {
            gp.gameState = gp.PLAY_STATE;
            gp.remove(continueButton);
        }

        if (e.getSource() == quitButton) {
            System.exit(1);
            gp.remove(quitButton);
        }

        if (e.getSource() == tryAgainButton) {
            hoverPlay = false;
            hoverQuit = false;
            mainGame.restartGame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == playButton) {
            hoverPlay = true;
        }

        if (e.getSource() == quitButton) {
            hoverQuit = true;
        }

        if (e.getSource() == continueButton) {
            hoverContinue = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == playButton) {
            hoverPlay = false;
        }

        if (e.getSource() == quitButton) {
            hoverQuit = false;
        }

        if (e.getSource() == continueButton) {
            hoverContinue = false;
        }
    }
}

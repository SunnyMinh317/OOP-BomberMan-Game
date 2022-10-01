package main.GUI;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        JFrame window = new JFrame("Bomberman");
        GamePanel panel = new GamePanel();

        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}

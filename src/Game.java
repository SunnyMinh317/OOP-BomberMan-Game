import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bomberman");
        JPanel panel = new JPanel();
        ImageIcon icon = new ImageIcon("./resources/bomberman.jpg");
        JLabel label = new JLabel(icon);
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}

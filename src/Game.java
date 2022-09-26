import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame("Bomberman");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        GameFrame frame = new GameFrame();
        window.add(frame);
        frame.startGameThread();
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}

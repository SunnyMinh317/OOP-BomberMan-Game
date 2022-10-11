package main.Level;

import main.GUI.GamePanel;
import main.Game;

public class Camera {
    private int camX;
    private int camY;
    private int levelWidth;
    private int levelHeight;

    public Camera(int levelWidth, int levelHeight) {
        camX = 0;
        camY = 0;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }

    public void updateCamera(int bomberX, int bomberY) {
        this.camX = (bomberX + 24) - GamePanel.WINDOW_WIDTH / 2;
        this.camY = (bomberY + 24) - GamePanel.WINDOW_HEIGHT / 2;

        if (camX < 0) {
            camX = 0;
        }
        if (camY < 0) {
            camY = 0;
        }
        if (camX > 48 * levelWidth - GamePanel.WINDOW_WIDTH) {
            camX = 48 * levelWidth - GamePanel.WINDOW_WIDTH;
        }
        if (camY > 48 * levelHeight - GamePanel.WINDOW_HEIGHT) {
            camY = 48 * levelHeight - GamePanel.WINDOW_HEIGHT;
        }
    }

    public int getCamX() {
        return camX;
    }

    public int getCamY() {
        return camY;
    }
}

package main.Entity;

import main.Game;
import main.Input.Keyboard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Bomb extends Entity {
    public static int bombStrength = 10;

    private int bombInterval = 10, currentBombTick = 0, currentBombFrameIndex = 0; // ANIMATING THE BOMB
    private int countToExplosion = 0, explosionInterval = 5; // BOMB TICKING DOWN
    private int currentExplosionTick = 0, explosionAnimInterval = 10, currentExplosionFrameIndex = 0;
    private boolean placed, exploded;

    private static BufferedImage[] bombFrames = new BufferedImage[3];
    private static BufferedImage[] middleExplosionFrames = new BufferedImage[4];
    private static BufferedImage[] leftExplosionFrames = new BufferedImage[4];
    private static BufferedImage[] rightExplosionFrames = new BufferedImage[4];
    private static BufferedImage[] upExplosionFrames = new BufferedImage[4];
    private static BufferedImage[] downExplosionFrames = new BufferedImage[4];

    private ArrayList<Flame> spreadFlame = new ArrayList<Flame>();

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        placed = true;
    }

    public static void loadBombImage() {
        try {
            for (int i = 0; i < 3; i++) {
                bombFrames[i] = Game.gameTileSheet.getSubimage(i * 16, 3 * 16, 16, 16);

                middleExplosionFrames[0] = Game.gameTileSheet.getSubimage(2 * 16, 6 * 16, 16, 16);
                middleExplosionFrames[1] = Game.gameTileSheet.getSubimage(7 * 16, 6 * 16, 16, 16);
                middleExplosionFrames[2] = Game.gameTileSheet.getSubimage(2 * 16, 11 * 16, 16, 16);
                middleExplosionFrames[3] = Game.gameTileSheet.getSubimage(7 * 16, 11 * 16, 16, 16);

                leftExplosionFrames[0] = Game.gameTileSheet.getSubimage(0, 6 * 16, 16, 16);
                leftExplosionFrames[1] = Game.gameTileSheet.getSubimage(5 * 16, 6 * 16, 16, 16);
                leftExplosionFrames[2] = Game.gameTileSheet.getSubimage(0, 11 * 16, 16, 16);
                leftExplosionFrames[3] = Game.gameTileSheet.getSubimage(5 * 16, 11 * 16, 16, 16);

                rightExplosionFrames[0] = Game.gameTileSheet.getSubimage(4 * 16, 6 * 16, 16, 16);
                rightExplosionFrames[1] = Game.gameTileSheet.getSubimage(9 * 16, 6 * 16, 16, 16);
                rightExplosionFrames[2] = Game.gameTileSheet.getSubimage(4 * 16, 11 * 16, 16, 16);
                rightExplosionFrames[3] = Game.gameTileSheet.getSubimage(9 * 16, 11 * 16, 16, 16);

                upExplosionFrames[0] = Game.gameTileSheet.getSubimage(2 * 16, 4 * 16, 16, 16);
                upExplosionFrames[1] = Game.gameTileSheet.getSubimage(7 * 16, 4 * 16, 16, 16);
                upExplosionFrames[2] = Game.gameTileSheet.getSubimage(2 * 16, 9 * 16, 16, 16);
                upExplosionFrames[3] = Game.gameTileSheet.getSubimage(7 * 16, 9 * 16, 16, 16);

                downExplosionFrames[0] = Game.gameTileSheet.getSubimage(2 * 16, 8 * 16, 16, 16);
                downExplosionFrames[1] = Game.gameTileSheet.getSubimage(7 * 16, 8 * 16, 16, 16);
                downExplosionFrames[2] = Game.gameTileSheet.getSubimage(2 * 16, 13 * 16, 16, 16);
                downExplosionFrames[3] = Game.gameTileSheet.getSubimage(7 * 16, 13 * 16, 16, 16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assessExplodingTiles(int[][] map) {
        // SOUTH ASSESSMENT
        for (int i = 1; i <= bombStrength; i++) {
            if (map[this.y / 48 + i][this.x / 48] == 0) {
                if (i != bombStrength) {
                    spreadFlame.add(new Flame(this.x, this.y + i * 48, false, 0));
                } else {
                    spreadFlame.add(new Flame(this.x, this.y + i * 48, true, 0));
                }
            } else if (map[this.y / 48 + i][this.x / 48] == 1) {
                if (i != 1) {
                    int flameAmount = spreadFlame.size() - 1;
                    if (flameAmount >= 0) {
                        spreadFlame.remove(flameAmount);
                    }
                    spreadFlame.add(new Flame(this.x, this.y + (i - 1) * 48, true, 0));
                }
                break;
            } else if (map[this.y / 48 + i][this.x / 48] == 2) {
                if (i != 1) {
                    int flameAmount = spreadFlame.size() - 1;
                    if (flameAmount >= 0) {
                        spreadFlame.remove(flameAmount);
                    }
                    spreadFlame.add(new Flame(this.x, this.y + (i - 1) * 48, true, 0));
                }
                map[this.y / 48 + i][this.x / 48] = 0;
                break;
            }
        }

        // NORTH ASSESSMENT
        for (int i = 1; i <= bombStrength; i++) {
            if (map[this.y / 48 - i][this.x / 48] == 0) {
                if (i != bombStrength) {
                    spreadFlame.add(new Flame(this.x, this.y - i * 48, false, 2));
                } else {
                    spreadFlame.add(new Flame(this.x, this.y - i * 48, true, 2));
                }
            } else if (map[this.y / 48 - i][this.x / 48] == 1) {
                if (i != 1) {
                    int flameAmount = spreadFlame.size() - 1;
                    if (flameAmount >= 0) {
                        spreadFlame.remove(flameAmount);
                    }
                    spreadFlame.add(new Flame(this.x, this.y - (i - 1) * 48, true, 2));
                }
                break;
            } else if (map[this.y / 48 - i][this.x / 48] == 2) {
                if (i != 1) {
                    int flameAmount = spreadFlame.size() - 1;
                    if (flameAmount >= 0) {
                        spreadFlame.remove(flameAmount);
                    }
                    spreadFlame.add(new Flame(this.x, this.y - (i - 1) * 48, true, 2));
                }
                map[this.y / 48 - i][this.x / 48] = 0;
                break;
            }
        }

        // WEST ASSESSMENT
        for (int i = 1; i <= bombStrength; i++) {
            if (map[this.y / 48][this.x / 48 - i] == 0) {
                if (i != bombStrength) {
                    spreadFlame.add(new Flame(this.x - i * 48, this.y, false, 3));
                } else {
                    spreadFlame.add(new Flame(this.x - i * 48, this.y, true, 3));
                }
            } else if (map[this.y / 48][this.x / 48 - i] == 1) {
                if (i != 1) {
                    int flameAmount = spreadFlame.size() - 1;
                    if (flameAmount >= 0) {
                        spreadFlame.remove(flameAmount);
                    }
                    spreadFlame.add(new Flame(this.x - (i - 1) * 48, this.y, true, 3));
                }
                break;
            } else if (map[this.y / 48][this.x / 48 - i] == 2) {
                if (i != 1) {
                    int flameAmount = spreadFlame.size() - 1;
                    if (flameAmount >= 0) {
                        spreadFlame.remove(flameAmount);
                    }
                    spreadFlame.add(new Flame(this.x - (i - 1) * 48, this.y, true, 3));
                }
                map[this.y / 48][this.x / 48 - i] = 0;
                break;
            }
        }

        // EAST ASSESSMENT
        for (int i = 1; i <= bombStrength; i++) {
            if (map[this.y / 48][this.x / 48 + i] == 0) {
                if (i != bombStrength) {
                    spreadFlame.add(new Flame(this.x + i * 48, this.y, false, 1));
                } else {
                    spreadFlame.add(new Flame(this.x + i * 48, this.y, true, 1));
                }
            } else if (map[this.y / 48][this.x / 48 + i] == 1) {
                if (i != 1) {
                    int flameAmount = spreadFlame.size() - 1;
                    if (flameAmount >= 0) {
                        spreadFlame.remove(flameAmount);
                    }
                    spreadFlame.add(new Flame(this.x + (i - 1) * 48, this.y, true, 1));
                }
                break;
            } else if (map[this.y / 48][this.x / 48 + i] == 2) {
                if (i != 1) {
                    int flameAmount = spreadFlame.size() - 1;
                    if (flameAmount >= 0) {
                        spreadFlame.remove(flameAmount);
                    }
                    spreadFlame.add(new Flame(this.x + (i - 1) * 48, this.y, true, 1));
                }
                map[this.y / 48][this.x / 48 + i] = 0;
                break;
            }
        }
    }

    public void updateBomb(ArrayList<Bomb> activeBombs, int[][] map) {
        if (placed) {
            currentBombTick++;
            if (currentBombTick == bombInterval) {
                currentBombTick = 0;
                currentBombFrameIndex++;
                if (currentBombFrameIndex > 2) {
                    currentBombFrameIndex = 0;
                    countToExplosion++;
                    if (countToExplosion == explosionInterval) {
                        placed = false;
                        exploded = true;
                        assessExplodingTiles(map);
                    }
                }
            }
        }

        if (exploded) {
            for (int i = 0; i < spreadFlame.size(); i++) {
                spreadFlame.get(i).updateFlame();
            }
            currentExplosionTick++;
            if (currentExplosionTick == explosionAnimInterval) {
                currentExplosionTick = 0;
                currentExplosionFrameIndex++;
                if (currentExplosionFrameIndex == 4) {
                    exploded = false;
                    activeBombs.remove(0);
                    spreadFlame.clear();
                    map[this.y / 48][this.x / 48] = 0;
                }
            }
        }
    }

    public void drawBomb(Graphics2D g) {
        if (placed) {
            g.drawImage(bombFrames[currentBombFrameIndex], this.x, this.y, 48, 48, null);
        }

        if (exploded) {
            g.drawImage(middleExplosionFrames[currentExplosionFrameIndex], this.x, this.y, 48, 48, null);
            for (int i = 0; i < spreadFlame.size(); i++) {
                spreadFlame.get(i).drawFlame(g);
            }
        }
    }
}

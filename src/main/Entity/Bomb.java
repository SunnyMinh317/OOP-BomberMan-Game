package main.Entity;

import main.Entity.Tiles.DestroyedBrick;
import main.Entity.Tiles.Flame;
import main.GUI.GamePanel;
import main.Game;
import main.Input.Keyboard;
import main.Level.GameMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Bomb extends Entity {
    public static int bombStrength = 1;

    private int bombInterval = 10, currentBombTick = 0, currentBombFrameIndex = 0; // ANIMATING THE BOMB
    private int countToExplosion = 0, explosionInterval = 5; // BOMB TICKING DOWN
    private int currentExplosionTick = 0, explosionAnimInterval = 5, currentExplosionFrameIndex = 0;
    private boolean placed, exploded = false;

    private static BufferedImage[] bombFrames = new BufferedImage[3];
    private static BufferedImage[] middleExplosionFrames = new BufferedImage[7];
    private ArrayList<DestroyedBrick> brokenBrick = new ArrayList<DestroyedBrick>();
    public ArrayList<Flame> spreadFlame = new ArrayList<Flame>();

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        placed = true;
    }

    public ArrayList<Flame> getSpreadFlame() {
        return spreadFlame;
    }

    public static void loadBombImage() {
        try {
            for (int i = 0; i < 3; i++) {
                bombFrames[i] = Game.gameTileSheet.getSubimage(i * 16, 3 * 16, 16, 16);

                middleExplosionFrames[0] = Game.gameTileSheet.getSubimage(2 * 16, 6 * 16, 16, 16);
                middleExplosionFrames[1] = Game.gameTileSheet.getSubimage(7 * 16, 6 * 16, 16, 16);
                middleExplosionFrames[2] = Game.gameTileSheet.getSubimage(2 * 16, 11 * 16, 16, 16);
                middleExplosionFrames[3] = Game.gameTileSheet.getSubimage(7 * 16, 11 * 16, 16, 16);
                middleExplosionFrames[4] = Game.gameTileSheet.getSubimage(2 * 16, 11 * 16, 16, 16);
                middleExplosionFrames[5] = Game.gameTileSheet.getSubimage(7 * 16, 6 * 16, 16, 16);
                middleExplosionFrames[6] = Game.gameTileSheet.getSubimage(2 * 16, 6 * 16, 16, 16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assessExplodingTiles(int[][] map, ArrayList<Bomb> activeBomb) {
        // SOUTH ASSESSMENT
        for (int i = 1; i <= bombStrength; i++) {
            if (map[this.y / 48 + i][this.x / 48] == 1) {
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
                brokenBrick.add(new DestroyedBrick(this.x, this.y + i * 48));
                map[this.y / 48 + i][this.x / 48] = 0;
                break;
            } else {
                if (i != bombStrength) {
                    spreadFlame.add(new Flame(this.x, this.y + i * 48, false, 0));
                } else {
                    spreadFlame.add(new Flame(this.x, this.y + i * 48, true, 0));
                }
            }

            // INSTANTLY IGNITE THE NEIGHBORING BOMBS
            if (map[this.y / 48 + i][this.x / 48] == 3) {
                Bomb selectedBomb = getBomb(this.x / 48, this.y / 48 + i, activeBomb);
                if (selectedBomb != null && !selectedBomb.isExploded()) {
                    selectedBomb.instantIgnite();
                }
            }
        }

        // NORTH ASSESSMENT
        for (int i = 1; i <= bombStrength; i++) {
            if (map[this.y / 48 - i][this.x / 48] == 1) {
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
                brokenBrick.add(new DestroyedBrick(this.x, this.y - i * 48));
                map[this.y / 48 - i][this.x / 48] = 0;
                break;
            } else {
                if (i != bombStrength) {
                    spreadFlame.add(new Flame(this.x, this.y - i * 48, false, 2));
                } else {
                    spreadFlame.add(new Flame(this.x, this.y - i * 48, true, 2));
                }
            }

            // INSTANTLY IGNITE THE NEIGHBORING BOMBS
            if (map[this.y / 48 - i][this.x / 48] == 3) {
                Bomb selectedBomb = getBomb(this.x / 48, this.y / 48 - i, activeBomb);
                if (selectedBomb != null && !selectedBomb.isExploded()) {
                    selectedBomb.instantIgnite();
                }
            }
        }

        // WEST ASSESSMENT
        for (int i = 1; i <= bombStrength; i++) {
            if (map[this.y / 48][this.x / 48 - i] == 1) {
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
                brokenBrick.add(new DestroyedBrick(this.x - i * 48, this.y));
                map[this.y / 48][this.x / 48 - i] = 0;
                break;
            } else {
                if (i != bombStrength) {
                    spreadFlame.add(new Flame(this.x - i * 48, this.y, false, 3));
                } else {
                    spreadFlame.add(new Flame(this.x - i * 48, this.y, true, 3));
                }
            }

            // INSTANTLY IGNITE THE NEIGHBORING BOMBS
            if (map[this.y / 48][this.x / 48 - i] == 3) {
                Bomb selectedBomb = getBomb(this.x / 48 - i, this.y / 48, activeBomb);
                if (selectedBomb != null && !selectedBomb.isExploded()) {
                    selectedBomb.instantIgnite();
                }
            }
        }

        // EAST ASSESSMENT
        for (int i = 1; i <= bombStrength; i++) {
            if (map[this.y / 48][this.x / 48 + i] == 1) {
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
                brokenBrick.add(new DestroyedBrick(this.x + i * 48, this.y));
                map[this.y / 48][this.x / 48 + i] = 0;
                break;
            } else {
                if (i != bombStrength) {
                    spreadFlame.add(new Flame(this.x + i * 48, this.y, false, 1));
                } else {
                    spreadFlame.add(new Flame(this.x + i * 48, this.y, true, 1));
                }
            }

            // INSTANTLY IGNITE THE NEIGHBORING BOMBS
            if (map[this.y / 48][this.x / 48 + i] == 3) {
                Bomb selectedBomb = getBomb(this.x / 48 + i, this.y / 48, activeBomb);
                if (selectedBomb != null && !selectedBomb.isExploded()) {
                    selectedBomb.instantIgnite();
                }
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
                        GamePanel.playSFX(3);
                        placed = false;
                        exploded = true;
                        assessExplodingTiles(map, activeBombs);
                    }
                }
            }
        }

        if (exploded) {
            for (Flame flame : spreadFlame) {
                flame.updateFlame();
            }
            for (DestroyedBrick brick : brokenBrick) {
                brick.updateDestroyedBrick();
            }
            currentExplosionTick++;
            if (currentExplosionTick == explosionAnimInterval) {
                currentExplosionTick = 0;
                currentExplosionFrameIndex++;
                if (currentExplosionFrameIndex == 7) {
                    exploded = false;
                    activeBombs.remove(0);
                    spreadFlame.clear();
                    brokenBrick.clear();
                    map[this.y / 48][this.x / 48] = 0;
                }
            }
        }
    }

    public void drawBomb(Graphics2D g) {
        if (placed) {
            g.drawImage(bombFrames[currentBombFrameIndex], this.x - Game.gameCam.getCamX(), this.y - Game.gameCam.getCamY(), 48, 48, null);
        }

        if (exploded) {
            g.drawImage(middleExplosionFrames[currentExplosionFrameIndex], this.x - Game.gameCam.getCamX(), this.y - Game.gameCam.getCamY(), 48, 48, null);
            for (Flame flame : spreadFlame) {
                flame.drawFlame(g);
            }
            for (DestroyedBrick brick : brokenBrick) {
                brick.drawDestroyedBrick(g);
            }
        }
    }

    public void instantIgnite() {
        currentBombTick = 9;
        currentBombFrameIndex = 1;
        countToExplosion = 4;
    }

    public boolean isExploded() {
        return exploded;
    }

    private Bomb getBomb(int x, int y, ArrayList<Bomb> activeBomb) {
        for (Bomb selectedBomb : activeBomb) {
            if (selectedBomb.x / 48 == x && selectedBomb.y / 48 == y) {
                return selectedBomb;
            }
        }
        return null;
    }
}

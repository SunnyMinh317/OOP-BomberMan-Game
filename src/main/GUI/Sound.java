package main.GUI;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/audio/Bomberman_MainBGM.wav");
        soundURL[1] = getClass().getResource("/audio/Bomberman_BombSet.wav");
        soundURL[2] = getClass().getResource("/audio/Bomberman_CollectItem.wav");
        soundURL[3] = getClass().getResource("/audio/Bomberman_BombExplode.wav");
        soundURL[4] = getClass().getResource("/audio/Bomberman_GameOver.wav");
        soundURL[5] = getClass().getResource("/audio/Bomberman_Walk.wav");
        soundURL[6] = getClass().getResource("/audio/Bomberman_TitleScreen.wav");
        soundURL[8] = getClass().getResource("/audio/Bomberman_Win.wav");
        soundURL[9] = getClass().getResource("/audio/Bomberman_LevelUp.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (Exception ignored) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public boolean isPlaying() {
        return clip.isRunning();
    }
}

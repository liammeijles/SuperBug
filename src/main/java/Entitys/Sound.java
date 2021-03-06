package Entitys;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[300];
    private float currentVolume = 0;
    private float prevVolume = 0;
    FloatControl fc;
    Boolean mute = false;

    static boolean allSounds = true;

    public Sound() {
        //Main sound file / Background music
        soundURL[0] = getClass().getResource("/assets/sounds/background-music.wav");
        //Power up sound file / Rip and Tear
        soundURL[1] = getClass().getResource("/assets/sounds/rip-tear.wav");
        //Movement sound
        soundURL[2] = getClass().getResource("/assets/sounds/walking.wav");
        //Shooting sound
        soundURL[3] = getClass().getResource("/assets/sounds/machine-gun.wav");
        //Kill sound
        soundURL[4] = getClass().getResource("/assets/sounds/death.wav");
        //Enemy sound
        soundURL[5] = getClass().getResource("/assets/sounds/enemy.wav");
    }

    public void playSound(int i) {
        setFile(i);
        play();
        loop();
    }

    //Stop sound
    public void stopSound() {
        stop();
    }

    //Play 1 time sound effect
    public void playSE(int i) {
        if (allSounds) {
            setFile(i);
            play();
        }

    }

    public void setFile(int i) {

        //Set file constructor with indexing
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
        }
    }

    //Play audio file
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    //Loop audio file
    public void loop() {
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    //Stop audio file
    public void stop() {
        clip.stop();
    }

    public void volumeDown() {
        currentVolume -= 1.0f;
        if (currentVolume < -80.0f) {
            currentVolume = - 80.0f;
        }
        fc.setValue(currentVolume);
    }

    public void volumeUp() {
        currentVolume += 1.0f;
        if (currentVolume > 6.0f) {
            currentVolume = 6.0f;
        }
        fc.setValue(currentVolume);
    }
    public void volumeMute() {
        if (!mute) {
            prevVolume = currentVolume;
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            mute = true;
        } else if (mute) {
            currentVolume = prevVolume;
            fc.setValue(currentVolume);
            mute = false;
        }
    }

    public void stopAllSound(boolean stop) {
        this.allSounds = stop;
    }
}

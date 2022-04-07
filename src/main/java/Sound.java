import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[300];

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
    
    public void setFile(int i) {
        //Set file constructor with indexing
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
        }
    }

    //Play audio file
    public void play() {
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
}

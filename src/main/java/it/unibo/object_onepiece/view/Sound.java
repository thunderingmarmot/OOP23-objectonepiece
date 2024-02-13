package it.unibo.object_onepiece.view;

import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;
import java.net.URL;

public class Sound {
    enum SoundTypes {
        AMBIENCE,
        CANNON_SHOT,
        SHIP_COLLIDE,
        SHIP_DESTROY,
        SHIP_HEAL,
        BARRELL_DESTROY,
        MINE_DESTROY
    }

    private final String soundFolder = "sound/";

    protected final Map<SoundTypes, URL> soundTypesToFile = Map.of(
        SoundTypes.AMBIENCE, this.getURL(soundFolder + "ocean.wav"),
        SoundTypes.CANNON_SHOT, this.getURL(soundFolder + "cannon_shot.wav"),
        SoundTypes.SHIP_COLLIDE, this.getURL(soundFolder + "cannon_shot.wav"),
        SoundTypes.SHIP_DESTROY, this.getURL(soundFolder + "ship_destroy.wav"),
        SoundTypes.SHIP_HEAL, this.getURL(soundFolder + "cannon_shot.wav"),
        SoundTypes.BARRELL_DESTROY, this.getURL(soundFolder + "cannon_shot.wav"),
        SoundTypes.MINE_DESTROY, this.getURL(soundFolder + "mine_explode.wav")
    );

    protected void playSound(SoundTypes sound) {
        this.play(soundTypesToFile.get(sound), 0, false);
    }

    protected void playAmbienceSound() {
        this.play(soundTypesToFile.get(SoundTypes.AMBIENCE), -10, true);
    }

    private URL getURL(String url) {
        return this.getClass().getClassLoader().getResource(url);
    }

    private void play(URL url, float volume, boolean loop) {
        try {
            AudioInputStream audioIN = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();

            clip.open(audioIN);
            setVolume(clip, volume);
    
            audioIN.close();
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println(e);
        }
    }

    private void setVolume(Clip clip, float volume) {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
    }
}

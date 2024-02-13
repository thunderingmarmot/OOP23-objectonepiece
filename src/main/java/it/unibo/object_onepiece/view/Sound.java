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
        CANNON_SHOT,
        SHIP_DESTROY,
        SHIP_HEAL,
        BARRELL_DESTROY
    }

    private final String soundFolder = "sound/";

    protected final Map<SoundTypes, URL> soundTypesToFile = Map.of(
        SoundTypes.CANNON_SHOT, this.getClass().getClassLoader().getResource(soundFolder + "cannon_shot.wav"),
        SoundTypes.SHIP_DESTROY, this.getClass().getClassLoader().getResource(soundFolder + "cannon_shot.wav"),
        SoundTypes.SHIP_HEAL,  this.getClass().getClassLoader().getResource(soundFolder + "cannon_shot.wav"),
        SoundTypes.BARRELL_DESTROY,  this.getClass().getClassLoader().getResource(soundFolder + "cannon_shot.wav")
    );

    public void playSound(SoundTypes sound) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundTypesToFile.get(sound));
            Clip clip = AudioSystem.getClip();
            
            clip.open(audioInputStream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-20);

            audioInputStream.close();
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println(e);
        }
    }

    
}

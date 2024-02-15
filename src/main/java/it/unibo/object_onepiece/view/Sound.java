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

/**
 * This class is used to play sounds in the view.
 * It defines methods to play a sound and play ambience sound.
 */
public final class Sound {
    enum SoundTypes {
        CANNON_SHOT,
        SHIP_COLLIDE,
        SHIP_DESTROY,
        REPAIR_COLLECT,
        MINE_DESTROY
    }

    private static final String SOUND_FOLDER = "sound/";

    private final Map<SoundTypes, URL> soundTypesToFile = Map.of(
        SoundTypes.CANNON_SHOT, this.getURL(SOUND_FOLDER + "cannon_shot.wav"),
        SoundTypes.SHIP_COLLIDE, this.getURL(SOUND_FOLDER + "ship_collide.wav"),
        SoundTypes.SHIP_DESTROY, this.getURL(SOUND_FOLDER + "ship_destroy.wav"),
        SoundTypes.REPAIR_COLLECT, this.getURL(SOUND_FOLDER + "repair_collect.wav"),
        SoundTypes.MINE_DESTROY, this.getURL(SOUND_FOLDER + "mine_explode.wav")
    );

    private final URL ambienceSoundURL = this.getURL(SOUND_FOLDER + "franky_theme.wav");
    private Clip ambienceClip;

    /**
     * This method play a specific sound based on the received SoundTypes.
     * 
     * @param  sound the type of sound to play
     */
    void playSound(final SoundTypes sound) {
        final AudioInputStream audioIN;
        final Clip clip;
        try {
            audioIN = AudioSystem.getAudioInputStream(soundTypesToFile.get(sound));
            clip = AudioSystem.getClip();
            this.play(audioIN, clip, false);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            
        }
    }

    /**
     * This method play the ambience sound in loop.
     */
    void playAmbienceSound() {
        final AudioInputStream ambienceAudioIN;
        try {
            ambienceAudioIN = AudioSystem.getAudioInputStream(ambienceSoundURL);
            this.ambienceClip = AudioSystem.getClip();
            this.play(ambienceAudioIN, this.ambienceClip, false);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the volume of a Clip.
     * 
     * @param  clip   the clip to set
     * @param  volume the volume to set the clip
     */
    void setVolume(final Clip clip, final float volume) {
        final FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
    }

    /**
     * This method pause the sound of a clip.
     * 
     * @param  clip the to pause
     */
    void pauseSound(final Clip clip) {
        if (clip.isRunning()) {
            clip.stop();
        } else {
            clip.start();
        }
    }

    /**
     * Getter for the ambience clip.
     * 
     * @return the ambience clip.
     */
    Clip getAmbienceClip() {
        return this.ambienceClip;
    }

    private URL getURL(final String url) {
        return this.getClass().getClassLoader().getResource(url);
    }

    private void play(final AudioInputStream audioIN, final Clip clip, final boolean loop) 
    throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        clip.open(audioIN);

        audioIN.close();
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.start();
        }
    }
}

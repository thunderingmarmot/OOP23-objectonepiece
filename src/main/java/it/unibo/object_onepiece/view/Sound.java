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
        AMBIENCE,
        CANNON_SHOT,
        SHIP_COLLIDE,
        SHIP_DESTROY,
        SHIP_REPAIR,
        BARRELL_DESTROY,
        MINE_DESTROY
    }

    private static final String SOUND_FOLDER = "sound/";

    private final Map<SoundTypes, URL> soundTypesToFile = Map.of(
        SoundTypes.AMBIENCE, this.getURL(SOUND_FOLDER + "franky_theme.wav"),
        SoundTypes.CANNON_SHOT, this.getURL(SOUND_FOLDER + "cannon_shot.wav"),
        SoundTypes.SHIP_COLLIDE, this.getURL(SOUND_FOLDER + "ship_collide.wav"),
        SoundTypes.SHIP_DESTROY, this.getURL(SOUND_FOLDER + "ship_destroy.wav"),
        SoundTypes.SHIP_REPAIR, this.getURL(SOUND_FOLDER + "ship_repair.wav"),
        SoundTypes.BARRELL_DESTROY, this.getURL(SOUND_FOLDER + "cannon_shot.wav"),
        SoundTypes.MINE_DESTROY, this.getURL(SOUND_FOLDER + "mine_explode.wav")
    );

    /**
     * This method play a specific sound based on the received SoundTypes.
     * 
     * @param  sound the type of sound to play
     * @throws       UnsupportedAudioFileException
     * @throws       IOException
     * @throws       LineUnavailableException
     */
    void playSound(final SoundTypes sound) 
    throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.play(soundTypesToFile.get(sound), 0, false);
    }

    /**
     * This method play the ambience sound in loop.
     * 
     * @param   volume the volume of the sound 
     * @throws         UnsupportedAudioFileException
     * @throws         IOException
     * @throws         LineUnavailableException
     */
    void playAmbienceSound(final float volume)
    throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.play(soundTypesToFile.get(SoundTypes.AMBIENCE), volume, true);
    }

    private URL getURL(final String url) {
        return this.getClass().getClassLoader().getResource(url);
    }

    private void play(final URL url, final float volume, final boolean loop) 
    throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        final AudioInputStream audioIN = AudioSystem.getAudioInputStream(url);
        final Clip clip = AudioSystem.getClip();

        clip.open(audioIN);
        setVolume(clip, volume);

        audioIN.close();
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.start();
        }
    }

    private void setVolume(final Clip clip, final float volume) {
        final FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
    }
}

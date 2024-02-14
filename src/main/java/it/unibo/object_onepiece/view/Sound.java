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
        SHIP_REPAIR,
        BARRELL_DESTROY,
        MINE_DESTROY
    }

    private static final String SOUND_FOLDER = "sound/";

    private final Map<SoundTypes, URL> soundTypesToFile = Map.of(
        SoundTypes.CANNON_SHOT, this.getURL(SOUND_FOLDER + "cannon_shot.wav"),
        SoundTypes.SHIP_COLLIDE, this.getURL(SOUND_FOLDER + "ship_collide.wav"),
        SoundTypes.SHIP_DESTROY, this.getURL(SOUND_FOLDER + "ship_destroy.wav"),
        SoundTypes.SHIP_REPAIR, this.getURL(SOUND_FOLDER + "ship_repair.wav"),
        SoundTypes.BARRELL_DESTROY, this.getURL(SOUND_FOLDER + "cannon_shot.wav"),
        SoundTypes.MINE_DESTROY, this.getURL(SOUND_FOLDER + "mine_explode.wav")
    );

    private final URL ambienceSoundURL = this.getURL(SOUND_FOLDER + "franky_theme.wav");
    private AudioInputStream ambienceAudioIN;
    private Clip ambienceClip;

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
        final AudioInputStream audioIN = AudioSystem.getAudioInputStream(soundTypesToFile.get(sound));
        final Clip clip = AudioSystem.getClip();
        this.play(audioIN, clip, false);
    }

    /**
     * This method play the ambience sound in loop.
     * 
     * @param   volume the volume of the sound 
     * @throws         UnsupportedAudioFileException
     * @throws         IOException
     * @throws         LineUnavailableException
     */
    void playAmbienceSound()
    throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.ambienceAudioIN = AudioSystem.getAudioInputStream(ambienceSoundURL);
        this.ambienceClip = AudioSystem.getClip();
        this.play(this.ambienceAudioIN, this.ambienceClip, false);
    }

    void setVolume(final Clip clip, final float volume) {
        final FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
    }

    void pauseSound(final Clip clip) {
        if(clip.isRunning()) {
            clip.stop();
        } else {
            clip.start();
        }
    }

    public Clip getAmbienceClip() {
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

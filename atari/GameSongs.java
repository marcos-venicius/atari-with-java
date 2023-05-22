import javax.sound.sampled.*;
import java.io.IOException;

public class GameSongs {
    public void play(String audioPath) {
        play(audioPath, false);
    }

    public void play(String audioPath, boolean loop) {
        try {
            Clip clip = AudioSystem.getClip();

            var resource = this.getClass().getResource("./assets/songs/music.wav");

            if (resource != null) {
                AudioInputStream is = AudioSystem.getAudioInputStream(resource);

                clip.open(is);
                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                clip.start();
            }
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }
}

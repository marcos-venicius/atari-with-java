import javax.sound.sampled.*;
import java.io.IOException;

public class GameSongs {
    public Clip load(String audioPath) {
        try {
            Clip clip = AudioSystem.getClip();

            var resource = this.getClass().getResource(audioPath);

            if (resource == null) {
                throw new NullPointerException("resource " + audioPath + "not found");
            }

            AudioInputStream is = AudioSystem.getAudioInputStream(resource);

            clip.open(is);

            return clip;
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }
}

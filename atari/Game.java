import javax.swing.*;
import java.awt.*;

public class Game extends Canvas implements Runnable {
    private static boolean _running = true;
    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = 900;

    public static void main(String[] args) {
        var game = new Game();

        JFrame window = new JFrame("Atari - AWJ");

        window.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        new Thread(game).start();
    }

    public void update() {

    }

    public void render() {
    }

    private void pause() {
        _running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime(), timer = System.currentTimeMillis();
        final double ticks = 60.0;
        double ns = 1000000000 / ticks, delta = 0;
        int updates = 0, frames = 0;

        while (_running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if ((System.currentTimeMillis() - timer) > 1000) {
                timer += 1000;
                System.out.println(updates + " ticks, FPS " + frames);
                updates = 0;
                frames = 0;
            }
        }
    }
}

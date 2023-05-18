import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private static boolean _running = true;
    private static final int GAME_WIDTH = 700;
    private static final int GAME_HEIGHT = 900;
    private BufferStrategy bufferStrategy;
    private final Ball ball;
    private final Bar bar;

    public Game() {
        ball = new Ball(GAME_WIDTH, GAME_HEIGHT, 0, (int) (GAME_HEIGHT * 0.4), 10, 10);
        bar = new Bar(GAME_WIDTH, GAME_HEIGHT, 350, 750, 100, 10);
    }

    public static void main(String[] args) {
        var game = new Game();

        JFrame window = new JFrame("Atari - AWJ");

        window.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);

        new Thread(game).start();
    }

    public void update() {
    }

    public void render() {
        bufferStrategy.show();

        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        graphics.setColor(Color.WHITE);
        graphics.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

        graphics.setColor(Color.WHITE);
        graphics.fillRect(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight());
    }

    private void pause() {
        _running = false;
    }

    @Override
    public void run() {
        createBufferStrategy(2);

        bufferStrategy = getBufferStrategy();

        long lastTime = System.nanoTime();
        final double ticks = 60.0;
        double ns = 1000000000 / ticks, delta = 0;

        while (_running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                update();
                render();
                delta--;
            }
        }
    }
}

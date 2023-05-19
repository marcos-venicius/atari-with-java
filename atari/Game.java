import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable, KeyListener {
    private static boolean _running = true;
    private static boolean _gameOver = false;
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 700;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private final Ball ball;
    private final Bar bar;

    public Game() {
        bar = new Bar(200, 600, 100, 10);
        ball = new Ball(bar, 0, (int) (GAME_HEIGHT * 0.4), 5);
    }

    public static void main(String[] args) {
        var game = new Game();

        JFrame window = new JFrame("Atari - AWJ");

        window.addKeyListener(game);
        window.setPreferredSize(new Dimension(GAME_WIDTH + 10, GAME_HEIGHT + 30));
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);

        new Thread(game).start();
    }

    public void gameOver() {
        pause();

        Graphics2D g = (Graphics2D)this.graphics;

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        int textWidth = g.getFontMetrics().stringWidth("GAME OVER");

        g.drawString("GAME OVER", GAME_WIDTH / 2 - textWidth / 2, GAME_HEIGHT / 2);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        int textWidth2 = g.getFontMetrics().stringWidth("Press \"R\" to restart");

        g.drawString("Press \"R\" to restart", GAME_WIDTH / 2 - textWidth2 / 2, GAME_HEIGHT / 2 + 50);
    }

    public void update() {
        this.ball.checkCollisionWithWalls();
        this.ball.checkCollisionWithBar();
        this.ball.move();

        if (this.ball.overflowsTheBar()) {
            gameOver();
        }
    }

    public void render() {
        bufferStrategy.show();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        graphics.setColor(Color.WHITE);
        graphics.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

        graphics.setColor(Color.WHITE);
        graphics.fillRect(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight());
    }

    public static void pause() {
        _running = false;
    }

    @Override
    public void run() {
        createBufferStrategy(2);

        this.bufferStrategy = getBufferStrategy();
        this.graphics = this.bufferStrategy.getDrawGraphics();

        long lastTime = System.nanoTime();
        final double ticks = 60.0;
        double ns = 1000000000 / ticks, delta = 0;

        while (_running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                delta--;
                update();
                render();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                this.bar.moveRight();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                this.bar.moveLeft();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

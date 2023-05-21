import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable, KeyListener {
    public static final int GAME_WIDTH = Wall.getNecessaryDisplayWidthToRenderWall();
    public static final int GAME_HEIGHT = (int) (Wall.getNecessaryDisplayWidthToRenderWall() * 1.1);

    private static boolean _running = true;
    private boolean _gameOver = false;

    public static final Game game = new Game();

    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    private final Wall wall;
    private final Ball ball;
    private final Bar bar;

    public Game() {
        this.wall = new Wall();
        this.ball = new Ball((int) (GAME_HEIGHT * 0.4), 5);
        this.bar = new Bar((int) (GAME_HEIGHT * 0.9), 100, 10);
    }

    public static void main(String[] args) {
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
        _gameOver = true;

        pause();

        Graphics2D g = (Graphics2D) this.graphics;

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
        this.ball.checkCollisionsWithWallBlocks(this.wall);
        this.ball.checkCollisionWithWalls();
        this.ball.checkCollisionWithBar(this.bar);
        this.ball.move();

        if (this.ball.overflowsTheBar(this.bar)) {
            gameOver();
        }
    }

    private void restart() {
        this.ball.randomizeX();
        this.ball.resetY();
        this.bar.randomizeX();
        this.wall.rebuild();

        _gameOver = false;
        _running = true;

        new Thread(game).start();
    }

    public void render() {
        bufferStrategy.show();

        this.graphics.setColor(Color.BLACK);
        this.graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        this.graphics.setColor(Color.WHITE);
        this.graphics.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

        this.graphics.setColor(Color.WHITE);
        this.graphics.fillRect(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight());

        this.wall.render(this.graphics);
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
        if (this._gameOver) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_R) {
                restart();

                return;
            }
        }

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

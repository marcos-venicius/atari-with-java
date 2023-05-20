import java.util.Random;

public class Bar {
    private final int width;
    private final int height;
    private int x = 0;
    private final int y;
    private final int velocity = 10;

    public Bar(int y, int width, int height) {
        this.randomizeX();

        this.y = Math.max(0, Math.min(y, Game.GAME_HEIGHT - height));

        this.width = width;
        this.height = height;
    }

    public void randomizeX() {
        Random random = new Random();

        this.x = random.nextInt(Game.GAME_WIDTH - this.getWidth());
    }

    public void moveRight() {
        this.setX(this.x + velocity);
    }

    public void moveLeft() {
        this.setX(this.x - velocity);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = Math.max(0, Math.min(x, Game.GAME_WIDTH - this.width));
    }

    public int getY() {
        return y;
    }
}

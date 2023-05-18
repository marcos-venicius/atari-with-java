public class Ball {
    private final int width;
    private final int height;
    private final int gameWidth;
    private final int gameHeight;
    private int x;
    private int y;

    public Ball(int gameWidth, int gameHeight, int x, int y, int width, int height) {
        this.x = Math.max(0, Math.min(x, gameWidth - width));
        this.y = Math.max(0, Math.min(y, gameHeight - height));

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        this.width = width;
        this.height = height;
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
        this.x = Math.max(0, Math.min(x, this.gameWidth - this.width));
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = Math.max(0, Math.min(y, this.gameHeight - this.height));
    }
}

public class Ball {
    private final int width;
    private final int height;
    private int x;
    private int y;
    private final int velocity = 2;
    private int xDirection = 1;
    private int yDirection = 1;
    private final Bar bar;

    public Ball(Bar bar, int x, int y, int ray) {
        this.bar = bar;
        this.x = Math.max(0, Math.min(x, Game.GAME_WIDTH - ray * 2));
        this.y = Math.max(0, Math.min(y, Game.GAME_HEIGHT - ray * 2));

        this.width = ray * 2;
        this.height = ray * 2;
    }

    public void checkCollisionWithWalls() {
        if (this.x >= Game.GAME_WIDTH - this.width) {
            this.xDirection = -1;
        } else if (this.x <= 0) {
            this.xDirection = 1;
        }

        if (this.y >= Game.GAME_HEIGHT - this.height) {
            this.yDirection = -1;
        } else if (this.y <= 0) {
            this.yDirection = 1;
        }
    }

    public void checkCollisionWithBar() {
        if (this.yDirection == 1 &&
                this.y >= this.bar.getY() - this.height &&
                this.y <= this.bar.getY() + this.bar.getHeight() &&
                this.x >= this.bar.getX() && this.x <= this.bar.getX() + this.bar.getWidth() - this.width) {
            this.yDirection = -1;
        }
    }

    public boolean overflowsTheBar() {
        return this.y >= this.bar.getY();
    }

    public void move() {
        this.setX(this.x + this.velocity * this.xDirection);
        this.setY(this.y + this.velocity * this.yDirection);
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

    public void setY(int y) {
        this.y = Math.max(0, Math.min(y, Game.GAME_HEIGHT - this.height));
    }
}

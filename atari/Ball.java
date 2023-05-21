import java.util.ArrayList;
import java.util.Random;

public class Ball {
    private final int width;
    private final int height;
    private int x = 0;
    private int y;
    private final int initialY;
    private int xDirection = 1;
    private int yDirection = 1;
    private final Bar bar;

    public Ball(Bar bar, int y, int ray) {
        this.randomizeX();

        this.bar = bar;
        this.y = Math.max(0, Math.min(y, Game.GAME_HEIGHT - ray * 2));
        this.initialY = this.y;

        this.width = ray * 2;
        this.height = ray * 2;
    }

    public void randomizeX() {
        Random random = new Random();

        this.x = random.nextInt(Game.GAME_WIDTH - this.getWidth());
    }

    public void resetY() {
        this.y = this.initialY;
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

    public void checkCollisionsWithWallBlocks(Wall wall) {
        for (ArrayList<WallBlock> row : wall.getWall()) {
            for (WallBlock col : row) {
                if (this.y <= col.getY() + col.getHeight()) {
                    if (this.x >= col.getX() - this.width - 1 && this.x <= col.getX() + col.getWidth() - 1) {
                        row.remove(col);
                        this.yDirection = 1;
                        break;
                    }
                }
            }
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
        boolean overflowBarY = this.y + this.height > this.bar.getY();

        if (overflowBarY) {
            boolean overflowToTheLeftOfTheBar = this.x + this.width < this.bar.getX();
            boolean overflowToTheRightOfTheBar = this.x > this.bar.getX() + this.bar.getWidth();

            return overflowToTheLeftOfTheBar || overflowToTheRightOfTheBar;
        }

        return false;
    }

    public void move() {
        int velocity = 2;
        this.setX(this.x + velocity * this.xDirection);
        this.setY(this.y + velocity * this.yDirection);
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

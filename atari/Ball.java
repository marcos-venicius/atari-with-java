import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.Random;

public class Ball {
    private final int width;
    private final int height;

    private int x;
    private int y;
    private final int initialY;

    private final int ray;
    private int xDirection = 1;
    private int yDirection = 1;

    private final Clip breakBlockSong;
    private final Clip collisionSong;

    public Ball(int y, int ray) {
        var gameSongs = new GameSongs();

        this.breakBlockSong = gameSongs.load("break-block.wav");
        this.collisionSong = gameSongs.load("click.wav");

        this.width = ray * 2;
        this.height = ray * 2;

        this.randomizeX();

        this.ray = ray;
        this.y = Math.max(0, Math.min(y, Game.GAME_HEIGHT - ray * 2));
        this.initialY = this.y;
    }

    public void randomizeX() {
        Random random = new Random();

        this.setX(random.nextInt(Game.GAME_WIDTH - this.getWidth()));
    }

    public void resetY() {
        this.y = this.initialY;
    }

    public void checkCollisionWithWalls() {
        if (this.x >= Game.GAME_WIDTH - this.width) {
            this.collisionSong.setMicrosecondPosition(0);
            this.collisionSong.start();
            this.xDirection = -1;
        } else if (this.x <= 0) {
            this.collisionSong.setMicrosecondPosition(0);
            this.collisionSong.start();
            this.xDirection = 1;
        }

        if (this.y >= Game.GAME_HEIGHT - this.height) {
            this.collisionSong.setMicrosecondPosition(0);
            this.collisionSong.start();
            this.yDirection = -1;
        } else if (this.y <= 0) {
            this.collisionSong.setMicrosecondPosition(0);
            this.collisionSong.start();
            this.yDirection = 1;
        }
    }

    private boolean ballCollideWithBlock(WallBlock block) {
        float closestX = clamp(this.x, block.getX(), block.getX() + block.getWidth());
        float closestY = clamp(this.y, block.getY() - block.getHeight(), block.getY());

        float distanceX = this.x - closestX;
        float distanceY = this.y - closestY;

        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(this.ray * 2, 2);
    }

    public void checkCollisionsWithWallBlocks(Wall wall) {
        for (ArrayList<WallBlock> row : wall.getWall()) {
            for (WallBlock block : row) {
                if (this.y <= block.getY() + block.getHeight()) {
                    if (this.ballCollideWithBlock(block)) {
                        this.yDirection = 1;
                        row.remove(block);
                        this.breakBlockSong.start();
                        break;
                    }
                }
            }
        }
    }

    private static float clamp(float value, float min, float max) {
        float x = value;

        if (x < min) {
            x = min;
        } else if (x > max) {
            x = max;
        }

        return x;
    }

    public void checkCollisionWithBar(Bar bar) {
        if (this.yDirection == 1) {
            float closestX = clamp(this.x, bar.getX(), bar.getX() + bar.getWidth());
            float closestY = clamp(this.y, bar.getY() - (int) (bar.getHeight() / 2), bar.getY());

            float distanceX = this.x - closestX;
            float distanceY = this.y - closestY;

            if (Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(this.ray, 2)) {
                this.yDirection = -1;

                this.collisionSong.setMicrosecondPosition(0);
                this.collisionSong.start();
                this.breakBlockSong.setFramePosition(0);
                this.breakBlockSong.stop();
            }
        }
    }

    public boolean overflowsTheBar(Bar bar) {
        boolean overflowBarY = this.y + this.height > bar.getY();

        if (overflowBarY) {
            boolean overflowToTheLeftOfTheBar = this.x + this.width < bar.getX();
            boolean overflowToTheRightOfTheBar = this.x > bar.getX() + bar.getWidth();

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

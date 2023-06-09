import java.awt.Color;
import java.awt.Graphics;

public class WallBlock {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Color color;

    public WallBlock(int width, int height, int x, int y, Color color) {
        this.width = width;
        this.height = height;

        this.x = x;
        this.y = y;

        this.color = color;
    }

    public void render(Graphics graphics) {
        graphics.setColor(this.color);
        graphics.fillRect(this.x, this.y, this.width, this.height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

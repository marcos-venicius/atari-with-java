import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Wall {
    private static final int WALL_COLUMNS_COUNT = 10;
    private static final int WALL_ROWS_COUNT = 4;
    private static final int WALL_BLOCK_WIDTH = 50;
    private static final int WALL_BLOCK_HEIGHT = 10;
    private static final int WALL_BLOCK_SPACING = 5;
    private static final int INITIAL_Y = 100;

    private final ArrayList<ArrayList<WallBlock>> wall = new ArrayList<>();

    public Wall() {
        this.build();
    }

    private void build() {
        for (int row = 0; row < WALL_ROWS_COUNT; row++) {
            var rowBlocks = new ArrayList<WallBlock>();

            for (int col = 0; col < WALL_COLUMNS_COUNT; col++) {
                int i = row * WALL_ROWS_COUNT + row * WALL_BLOCK_SPACING;
                Color color = Color.getHSBColor((float) i / (float) WALL_COLUMNS_COUNT, 0.85f, 1.0f);

                int x = WALL_BLOCK_SPACING + col * WALL_BLOCK_WIDTH + col * WALL_BLOCK_SPACING;
                int y = INITIAL_Y + WALL_BLOCK_SPACING + row * WALL_BLOCK_HEIGHT + row * WALL_BLOCK_SPACING;

                rowBlocks.add(new WallBlock(WALL_BLOCK_WIDTH, WALL_BLOCK_HEIGHT, x, y, color));
            }

            wall.add(rowBlocks);
        }
    }

    public void rebuild() {
        wall.clear();

        this.build();
    }

    public static int getNecessaryDisplayWidthToRenderWall() {
        return WALL_COLUMNS_COUNT * WALL_BLOCK_WIDTH + (WALL_COLUMNS_COUNT + 1) * WALL_BLOCK_SPACING;
    }

    public void render(Graphics graphics) {
        for (ArrayList<WallBlock> row : this.wall) {
            for (WallBlock block : row) {
                block.render(graphics);
            }
        }
    }

    public ArrayList<ArrayList<WallBlock>> getWall() {
        return wall;
    }
}

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Wall {
    private static final int WALL_COLUMNS_COUNT = 7;
    private static final int WALL_ROWS_COUNT = 3;
    private static final int WALL_BLOCK_WIDTH = 50;
    private static final int WALL_BLOCK_HEIGHT = 10;
    private static final int WALL_BLOCK_SPACING = 5;
    private static final int INITIAL_Y = 100;

    private final ArrayList<ArrayList<WallBlock>> wall = new ArrayList<>();

    public Wall() {
        int id = 0;

        for (int row = 0; row < WALL_ROWS_COUNT; row++) {
            var rowBlocks = new ArrayList<WallBlock>();

            for (int col = 0; col < WALL_COLUMNS_COUNT; col++) {
                System.out.printf("adding row: %d, col: %d, id %d\n", row, col, id + 1);
                rowBlocks.add(new WallBlock(
                        id++,
                        WALL_BLOCK_WIDTH,
                        WALL_BLOCK_HEIGHT,
                        WALL_BLOCK_SPACING + col * WALL_BLOCK_WIDTH + col * WALL_BLOCK_SPACING,
                        INITIAL_Y + WALL_BLOCK_SPACING + row * WALL_BLOCK_HEIGHT + row * WALL_BLOCK_SPACING,
                        Color.WHITE
                ));
            }

            wall.add(rowBlocks);
        }
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cell {
    // content of this cell (empty, cross, nought)
    Player content;
    // row and column of this cell
    int row, col;

    /** Constructor to initialise this cell with the specified row and column */
    public Cell(int row, int col) {
        this.row = row; // Initialise row
        this.col = col; // Initialise column
        this.content = Player.Empty; // Set the default content of the cell to empty
    }

    /** Paint itself on the graphics canvas, given the Graphics context g */
    public void paint(Graphics g) {
        // Graphics2D allows setting of pen's stroke size
        Graphics2D graphic2D = (Graphics2D) g;
        graphic2D.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Draw the symbol in the position
        int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
        int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
        if (content == Player.Cross) {
            graphic2D.setColor(Color.RED);
            int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            graphic2D.drawLine(x1, y1, x2, y2); // Draw X
            graphic2D.drawLine(x2, y1, x1, y2); // Draw X
        } else if (content == Player.Nought) {
            graphic2D.setColor(Color.BLUE);
            graphic2D.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE); // Draw O
        }
    }

    /** Set this cell's content to empty */
    public void clear() {
        content = Player.Empty; // Set content to empty
    }
}

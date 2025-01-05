import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Player.java;

public class GameMain extends JPanel implements MouseListener {
    // Constants for game 
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";

    // Constants for dimensions used for drawing
    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    /* Declare game object variables */
    private Board board;
    private GameState currentState;
    private Player currentPlayer;
    private JLabel statusBar;

    /** Constructor to set up the UI and game components on the panel */
    public GameMain() {
        // Add MouseListener for mouse click events
        addMouseListener(this);

        // Set up the status bar (JLabel) to display the status message
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        // Layout of the panel is in border layout
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);

        // Account for the statusBar height in overall height
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        // Create a new instance of the game "Board" class
        board = new Board();

        // Initialise the game board
        initGame();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(GameMain.TITLE);
                GameMain panel = new GameMain();
                frame.setContentPane(panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /** Custom painting code on this JPanel */
    public void paintComponent(Graphics g) {
        // Fill the background and set colour to white
        super.paintComponent(g);
        setBackground(Color.WHITE);

        // Ask the game board to paint itself
        board.paint(g);

        // Set status bar message
        if (currentState == GameState.Playing) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == Player.Cross) {
                statusBar.setText("X's Turn");
            } else {
                statusBar.setText("O's Turn");
            }
        } else if (currentState == GameState.Draw) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    /** Initialise the game-board contents and the current status of GameState and Player */
    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                // All cells empty
                board.cells[row][col].content = Player.Empty;
            }
        }
        currentState = GameState.Playing;
        currentPlayer = Player.Cross;
    }

    /** After each turn check to see if the current player has won by putting their symbol in that position,
     * If they have the GameState is set to won for that player.
     * If no winner, then isDraw is called to see if deadlock, if not GameState stays as PLAYING.
     */
    public void updateGame(Player thePlayer, int row, int col) {
        // Check for win after play
        if (board.hasWon(thePlayer, row, col)) {
            if (thePlayer == Player.Cross) {
                currentState = GameState.Cross_won; // Set game state to Cross won
            } else {
                currentState = GameState.Nought_won; // Set game state to Nought won
            }
        } else if (board.isDraw()) {
            currentState = GameState.Draw; // Set game state to Draw
        }
    }

    /** Event handler for the mouse click on the JPanel. If the selected cell is valid and empty,
     * then the current player is added to the cell content.
     * UpdateGame is called which will check for a winner or a draw.
     * If a win or draw, the game restarts.
     */
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        if (currentState == GameState.Playing) {
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
                    && board.cells[rowSelected][colSelected].content == Player.Empty) {
                // Move
                board.cells[rowSelected][colSelected].content = currentPlayer;
                // Update game state
                updateGame(currentPlayer, rowSelected, colSelected);
                // Switch player
                if (currentPlayer == Player.Cross) {
                    currentPlayer = Player.Nought;
                } else {
                    currentPlayer = Player.Cross;
                }
            }
        } else {
            // Game over and restart
            initGame();
        }

        // Redraw the graphics on the UI
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Auto-generated, event not used
    }
}

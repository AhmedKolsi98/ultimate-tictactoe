import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[81]; // 9x9 grid
    boolean player1_turn;
    int nextSubGrid = -1; // Tracks the sub-grid where the next player must play
    boolean[] subGridFull = new boolean[9]; // Tracks if a sub-grid is full
    String[] gridWinners = new String[9]; // Tracks the winner of each 3x3 grid

    TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(new Color(25,25,25));
        textfield.setForeground(new Color(25,255,0));
        textfield.setFont(new Font("Ink Free",Font.BOLD,75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0,0,800,100);

        button_panel.setLayout(new GridLayout(9,9)); // 9x9 grid
        button_panel.setBackground(new Color(150,150,150));

        for(int i=0;i<81;i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli",Font.BOLD,40));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        title_panel.add(textfield);
        frame.add(title_panel,BorderLayout.NORTH);
        frame.add(button_panel);

        initializeSubGridFull(); // Initialize sub-grid full states
        initializeGridWinners(); // Initialize grid winners
        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 81; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1_turn) {
                    if (isMoveValid(i)) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        player1_turn = false;
                        textfield.setText("O turn");

                        // Determine the next sub-grid
                        int row = i / 9;
                        int col = i % 9;
                        int newSubGrid = (col % 3) + (row % 3) * 3;
                        nextSubGrid = newSubGrid;

                        // Update valid moves based on the new sub-grid
                        updateValidMoves(nextSubGrid);

                        check();
                    }
                } else {
                    if (isMoveValid(i)) {
                        buttons[i].setForeground(new Color(0, 0, 255));
                        buttons[i].setText("O");
                        player1_turn = true;
                        textfield.setText("X turn");

                        // Determine the next sub-grid
                        int row = i / 9;
                        int col = i % 9;
                        int newSubGrid = (col % 3) + (row % 3) * 3;
                        nextSubGrid = newSubGrid;

                        // Update valid moves based on the new sub-grid
                        updateValidMoves(nextSubGrid);

                        check();
                    }
                }
            }
        }
    }

    public void firstTurn() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("X turn");
        } else {
            player1_turn = false;
            textfield.setText("O turn");
        }
        updateValidMoves(-1); // Initialize valid moves for the first turn
    }

    public void initializeSubGridFull() {
        for (int i = 0; i < 9; i++) {
            subGridFull[i] = false; // No sub-grid is full initially
        }
    }

    public void initializeGridWinners() {
        for (int i = 0; i < 9; i++) {
            gridWinners[i] = ""; // No winners initially
        }
    }

    public void updateNextSubGrid(int index) {
        int row = index / 9;
        int col = index % 9;
        int subGrid = (col / 3) + (row / 3) * 3;

        nextSubGrid = (col % 3) + (row % 3) * 3;
        subGridFull[subGrid] = true; // Mark the current sub-grid as having been played

        // Update valid moves
        updateValidMoves(nextSubGrid);
    }

    public void updateValidMoves(int subGrid) {
        System.out.println("Updating valid moves. Next SubGrid: " + nextSubGrid);
        for (int i = 0; i < 81; i++) {
            int row = i / 9;
            int col = i % 9;
            int currentSubGrid = (col / 3) + (row / 3) * 3;

            // Enable buttons if:
            // 1. It's the initial move (nextSubGrid is -1).
            // 2. The current button is in the subGrid specified by nextSubGrid.
            // 3. The subGrid has not been won yet.
            if (nextSubGrid == -1 || currentSubGrid == nextSubGrid) {
                if (!subGridFull[currentSubGrid]) {
                    buttons[i].setEnabled(true);
                } else {
                    buttons[i].setEnabled(false);
                }
            } else {
                buttons[i].setEnabled(false);
            }
        }
    }

    public boolean isMoveValid(int index) {
        int row = index / 9;
        int col = index % 9;
        int subGrid = (col / 3) + (row / 3) * 3;
        System.out.println("Checking move validity. Index: " + index + ", SubGrid: " + subGrid);
        // Check if the move is valid:
        // 1. The nextSubGrid is either -1 (initial move) or matches the current move's subGrid.
        // 2. The subGrid should not be full.
        return (nextSubGrid == -1 || nextSubGrid == subGrid) && !subGridFull[subGrid] && buttons[index].isEnabled();
    }

    public void check() {
        // Check each 3x3 grid for a win
        for (int i = 0; i < 9; i++) {
            checkGrid(i);
        }
        // Check if there is a winner in the larger 3x3 grid of grids
        checkBigGrid();
    }

    public void checkGrid(int gridIndex) {
        int startRow = (gridIndex / 3) * 3;
        int startCol = (gridIndex % 3) * 3;

        // Check rows, columns, and diagonals in this 3x3 grid
        if (checkLine(startRow * 9 + startCol, startRow * 9 + startCol + 1, startRow * 9 + startCol + 2) ||
                checkLine((startRow + 1) * 9 + startCol, (startRow + 1) * 9 + startCol + 1, (startRow + 1) * 9 + startCol + 2) ||
                checkLine((startRow + 2) * 9 + startCol, (startRow + 2) * 9 + startCol + 1, (startRow + 2) * 9 + startCol + 2) ||
                checkLine(startRow * 9 + startCol, (startRow + 1) * 9 + startCol, (startRow + 2) * 9 + startCol) ||
                checkLine(startRow * 9 + startCol + 1, (startRow + 1) * 9 + startCol + 1, (startRow + 2) * 9 + startCol + 1) ||
                checkLine(startRow * 9 + startCol + 2, (startRow + 1) * 9 + startCol + 2, (startRow + 2) * 9 + startCol + 2) ||
                checkLine(startRow * 9 + startCol, (startRow + 1) * 9 + startCol + 1, (startRow + 2) * 9 + startCol + 2) ||
                checkLine(startRow * 9 + startCol + 2, (startRow + 1) * 9 + startCol + 1, (startRow + 2) * 9 + startCol)) {
            gridWinners[gridIndex] = "X"; // Mark this grid as won by X
        }
    }

    public void checkBigGrid() {
        // Check for a winner in the larger 3x3 grid of grids
        for (int i = 0; i < 3; i++) {
            // Check rows and columns
            if (gridWinners[i * 3].equals("X") && gridWinners[i * 3 + 1].equals("X") && gridWinners[i * 3 + 2].equals("X")) {
                declareWinner("X");
                return;
            }
            if (gridWinners[i].equals("X") && gridWinners[i + 3].equals("X") && gridWinners[i + 6].equals("X")) {
                declareWinner("X");
                return;
            }
        }
        // Check diagonals
        if (gridWinners[0].equals("X") && gridWinners[4].equals("X") && gridWinners[8].equals("X")) {
            declareWinner("X");
            return;
        }
        if (gridWinners[2].equals("X") && gridWinners[4].equals("X") && gridWinners[6].equals("X")) {
            declareWinner("X");
            return;
        }
        // Repeat the above checks for "O"
        for (int i = 0; i < 3; i++) {
            if (gridWinners[i * 3].equals("O") && gridWinners[i * 3 + 1].equals("O") && gridWinners[i * 3 + 2].equals("O")) {
                declareWinner("O");
                return;
            }
            if (gridWinners[i].equals("O") && gridWinners[i + 3].equals("O") && gridWinners[i + 6].equals("O")) {
                declareWinner("O");
                return;
            }
        }
        if (gridWinners[0].equals("O") && gridWinners[4].equals("O") && gridWinners[8].equals("O")) {
            declareWinner("O");
            return;
        }
        if (gridWinners[2].equals("O") && gridWinners[4].equals("O") && gridWinners[6].equals("O")) {
            declareWinner("O");
        }
    }

    public boolean checkLine(int a, int b, int c) {
        if (buttons[a].getText().equals("X") && buttons[b].getText().equals("X") && buttons[c].getText().equals("X")) {
            xWins(a, b, c);
            return true;
        }
        if (buttons[a].getText().equals("O") && buttons[b].getText().equals("O") && buttons[c].getText().equals("O")) {
            oWins(a, b, c);
            return true;
        }
        return false;
    }

    public void xWins(int a, int b, int c) {
        buttons[a].setBackground(Color.RED);
        buttons[b].setBackground(Color.RED);
        buttons[c].setBackground(Color.RED);

        int gridIndex = (a / 9 / 3) * 3 + (a % 9 / 3);
        gridWinners[gridIndex] = "X";

        colorWinningGrid(gridIndex, Color.RED); // Color the winning grid

        for (int i = 0; i < 81; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("X wins");
    }

    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(Color.BLUE);
        buttons[b].setBackground(Color.BLUE);
        buttons[c].setBackground(Color.BLUE);

        int gridIndex = (a / 9 / 3) * 3 + (a % 9 / 3);
        gridWinners[gridIndex] = "O";

        colorWinningGrid(gridIndex, Color.BLUE); // Color the winning grid

        for (int i = 0; i < 81; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("O wins");
    }
    public void colorWinningGrid(int gridIndex, Color color) {
        int startRow = (gridIndex / 3) * 3;
        int startCol = (gridIndex % 3) * 3;

        // Color all cells in the 3x3 grid where the win happened
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                buttons[row * 9 + col].setBackground(color);
            }
        }
    }


    public void declareWinner(String winner) {
        textfield.setText(winner + " wins the game!");
        for (int i = 0; i < 81; i++) {
            buttons[i].setEnabled(false);
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}

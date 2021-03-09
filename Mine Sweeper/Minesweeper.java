import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Minesweeper extends JFrame implements ActionListener, MouseListener {

    JToggleButton[][] board;
    JPanel boardPanel;
    boolean firstClick;
    int numMines;

    public Minesweeper() {
        firstClick = true;
        numMines = 10;
        createBoard(10, 20);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void createBoard(int row, int col) {
        if (boardPanel != null)
            this.remove(boardPanel);
        boardPanel = new JPanel();
        board = new JToggleButton[row][col];
        boardPanel.setLayout(new GridLayout(row, col));

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                board[r][c] = new JToggleButton();
                board[r][c].putClientProperty("row", r);
                board[r][c].putClientProperty("column", c);
                board[r][c].putClientProperty("state", 0); // what constitutes a mine? -1
                // board[r][c].setBorder(BorderFactory.createBevelBorder(0));
                board[r][c].setFocusPainted(false);
                board[r][c].addMouseListener(this);
                boardPanel.add(board[r][c]);
            }
        }
        // set size is width by height
        this.setSize(40 * col, 40 * row);
        this.add(boardPanel);
        this.revalidate();
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        int row = (int) ((JToggleButton) e.getComponent()).getClientProperty("row");
        int col = (int) ((JToggleButton) e.getComponent()).getClientProperty("column");

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (firstClick) {
                setMinesAndCounts(row, col);
                firstClick = false;
            }
        }
    }

    public void setMinesAndCounts(int currRow, int currCol) {
        int count = numMines;
        int dimR = board.length;
        int dimC = board[0].length;

        while (count > 0) {
            int randR = (int) (Math.random() * dimR);
            int randC = (int) (Math.random() * dimC);
            int state = (int) ((JToggleButton) board[randR][randC]).getClientProperty("state");
            if (state == 0 && (Math.abs(randR - currRow) > 1 || Math.abs(randC - currCol) > 1)) {
                board[randR][randC].putClientProperty("state", -1);
                count--;
            }
        }
        // find counts

        // comment this out when necessary
        for (int r = 0; r < dimR; r++) {
            for (int c = 0; c < dimC; c++) {
                int state = (int) ((JToggleButton) board[r][c]).getClientProperty("state");
                board[r][c].setText("" + state);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        new Minesweeper();

    }
}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Minesweeper extends JFrame implements ActionListener, MouseListener {

    JToggleButton[][] board;
    JPanel boardPanel;
    boolean firstClick;
    int numMines;

    ImageIcon mineIcon;
    GraphicsEnvironment ge;
    Font mineFont;

    public Minesweeper() {
        firstClick = true;
        numMines = 40;

        try {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            mineFont = Font.createFont(Font.TRUETYPE_FONT, new File("mine-sweeper.ttf"));
            ge.registerFont(mineFont);
        } catch (IOException | FontFormatException e) {

        }

        System.out.println(mineFont); // to check if font shows up

        mineIcon = new ImageIcon("mine.png");
        mineIcon = new ImageIcon(mineIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

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
                board[r][c].setBorder(BorderFactory.createBevelBorder(0));
                board[r][c].setFont(mineFont.deriveFont(16f));
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

    public void write(int row, int col, int state) {
        Color color;
        switch (state) {
        case 1:
            color = Color.BLUE;
            break;
        case 2:
            color = Color.GREEN;
            break;
        case 3:
            color = Color.RED;
            break;
        case 4:
            color = new Color(128, 0, 128);
            break;
        case 5:
            color = new Color(0, 128, 0);
            break;
        case 6:
            color = Color.MAGENTA;
            break;
        case 7:
            color = Color.CYAN;
            break;
        default:
            color = Color.ORANGE;
            break;
        }
        if (state > 0) {
            board[row][col].setForeground(color);
            board[row][col].setText("" + state);
        }
    }

    public void mouseReleased(MouseEvent e) {
        int row = (int) ((JToggleButton) e.getComponent()).getClientProperty("row");
        int col = (int) ((JToggleButton) e.getComponent()).getClientProperty("column");

        if (e.getButton() == MouseEvent.BUTTON1) {
            board[row][col].setBackground(Color.LIGHT_GRAY);
            board[row][col].setOpaque(true);
            if (firstClick) {
                setMinesAndCounts(row, col);
                firstClick = false;
            }
            if ((int) board[row][col].getClientProperty("state") == -1) {
                for (int r = 0; r < board.length; r++) {
                    for (int c = 0; c < board[0].length; c++) {
                        if ((int) board[r][c].getClientProperty("state") == -1) {
                            board[r][c].setIcon(mineIcon);
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "You are a loser!");
                // show all of the mines
                // stop the user from having the ability to click on buttons until they reset
                // the game
            } else {
                expand(row, col);
                checkWin();
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
        for (int r = 0; r < dimR; r++) {
            for (int c = 0; c < dimC; c++) {
                count = 0;
                int currToggle = (int) board[r][c].getClientProperty("state");
                if (currToggle != -1) {
                    for (int r3x3 = r - 1; r3x3 <= r + 1; r3x3++) {
                        for (int c3x3 = c - 1; c3x3 <= c + 1; c3x3++) {
                            try {
                                int toggleState = (int) board[r3x3][c3x3].getClientProperty("state");
                                if (toggleState == -1 && !(r3x3 == r && c3x3 == c))
                                    count++;
                            } catch (ArrayIndexOutOfBoundsException e) {

                            }
                        }
                    }
                    board[r][c].putClientProperty("state", count);
                }
            }
        }

        // comment this out when necessary
        // for (int r = 0; r < dimR; r++) {
        // for (int c = 0; c < dimC; c++) {
        // int state = (int) ((JToggleButton) board[r][c]).getClientProperty("state");
        // board[r][c].setText("" + state);
        // }
        // }
    }

    public void expand(int row, int col) {
        int state = (int) ((JToggleButton) board[row][col]).getClientProperty("state");
        if (!board[row][col].isSelected()) {
            board[row][col].setBackground(Color.LIGHT_GRAY);
            board[row][col].setOpaque(true);
            board[row][col].setSelected(true);
        }
        if (state != 0) {
            write(row, col, state);
        } else {
            for (int r3x3 = row - 1; r3x3 <= row + 1; r3x3++) {
                for (int c3x3 = col - 1; c3x3 <= col + 1; c3x3++) {
                    try {
                        if (!board[r3x3][c3x3].isSelected()) {
                            expand(r3x3, c3x3);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
            }
        }
    }

    public void checkWin() {
        int dimR = board.length;
        int dimC = board[0].length;
        int totalSpaces = dimR * dimC;
        int count = 0;
        for (int r = 0; r < dimR; r++) {
            for (int c = 0; c < dimC; c++) {
                if (board[r][c].isSelected()) {
                    count++;
                }
            }
        }

        if (numMines == totalSpaces - count)
            JOptionPane.showMessageDialog(null, "How Cool are you");
    }

    public void actionPerformed(ActionEvent e) {

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

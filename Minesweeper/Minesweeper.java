import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Minesweeper extends JFrame implements ActionListener, MouseListener {

    JToggleButton[][] board;
    JPanel boardPanel;
    boolean firstClick = true, gameOn = true;
    int numMines = 10;
    ImageIcon[] numbers;
    ImageIcon mineIcon, flag, win, lose, playing, start;
    GraphicsEnvironment ge;
    Font mineFont, timeFont;
    Timer timer;
    int timePassed;
    JTextField timeField;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem beginner, inter, expert;
    JButton reset;
    int row = 9, column = 9;

    public Minesweeper() {
        // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // UIManager.put("ToggleButton.select", new Color(255, 140, 0));
        // does not work

        try {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            mineFont = Font.createFont(Font.TRUETYPE_FONT, new File("mine-sweeper.ttf"));
            timeFont = Font.createFont(Font.TRUETYPE_FONT, new File("digital-7.ttf"));
            ge.registerFont(mineFont);
            ge.registerFont(timeFont);
        } catch (IOException | FontFormatException e) {

        }

        System.out.println(mineFont); // to check if font shows up
        System.out.println(timeFont); // to check if font shows up

        mineIcon = new ImageIcon("mine.png");
        mineIcon = new ImageIcon(mineIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

        flag = new ImageIcon("flag.png");
        flag = new ImageIcon(flag.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

        win = new ImageIcon("win0.png");
        win = new ImageIcon(win.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

        lose = new ImageIcon("lose0.png");
        lose = new ImageIcon(lose.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

        playing = new ImageIcon("wait0.png");
        playing = new ImageIcon(playing.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

        start = new ImageIcon("smile0.png");
        start = new ImageIcon(start.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

        numbers = new ImageIcon[8];
        for (int x = 0; x < 8; x++) {
            numbers[x] = new ImageIcon((x + 1) + ".png");
            numbers[x] = new ImageIcon(numbers[x].getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        }

        createBoard(row, column);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void createBoard(int row, int col) {
        timeField = new JTextField("0");
        timeField.setFont(timeFont.deriveFont(25f));
        timeField.setHorizontalAlignment(JTextField.CENTER);
        timeField.setBackground(Color.BLACK);
        timeField.setForeground(Color.WHITE);
        beginner = new JMenuItem("beginner");
        beginner.addActionListener(this);
        inter = new JMenuItem("intermediate");
        inter.addActionListener(this);
        expert = new JMenuItem("expert");
        expert.addActionListener(this);
        reset = new JButton("reset");
        reset.addActionListener(this);
        reset.setIcon(start);
        menu = new JMenu("difficulty menu");
        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout());
        menu.add(beginner);
        menu.add(inter);
        menu.add(expert);
        menuBar.add(menu);
        menuBar.add(reset);
        menuBar.add(timeField);

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
        this.setJMenuBar(menuBar);
        this.add(boardPanel);
        this.revalidate();
    }

    public void write(int row, int col, int state) {
        // Color color;
        // switch (state) {
        // case 1:
        // color = Color.BLUE;
        // break;
        // case 2:
        // color = Color.GREEN;
        // break;
        // case 3:
        // color = Color.RED;
        // break;
        // case 4:
        // color = new Color(128, 0, 128);
        // break;
        // case 5:
        // color = new Color(0, 128, 0);
        // break;
        // case 6:
        // color = Color.MAGENTA;
        // break;
        // case 7:
        // color = Color.CYAN;
        // break;
        // default:
        // color = Color.ORANGE;
        // break;
        // }
        if (state > 0) {
            // board[row][col].setForeground(color);
            // board[row][col].setText("" + state);
            board[row][col].setIcon(numbers[state - 1]);
            board[row][col].setDisabledIcon(numbers[state - 1]);
        }
    }

    public void mouseReleased(MouseEvent e) {
        int row = (int) ((JToggleButton) e.getComponent()).getClientProperty("row");
        int col = (int) ((JToggleButton) e.getComponent()).getClientProperty("column");

        if (e.getButton() == MouseEvent.BUTTON1 && board[row][col].isEnabled()) {
            board[row][col].setBackground(Color.LIGHT_GRAY);
            board[row][col].setOpaque(true);
            if (firstClick) {
                timer = new Timer();
                timer.schedule(new UpdateTimer(), 0, 1000);
                setMinesAndCounts(row, col);
                firstClick = false;
                gameOn = true;
                reset.setIcon(playing);
            }
            if ((int) board[row][col].getClientProperty("state") == -1) {
                // board[row][col].setContentAreaFilled(false);
                // board[row][col].setOpaque(true);
                // board[row][col].setBackground(Color.RED);
                revealMines();
                timer.cancel();
                reset.setIcon(lose);
                gameOn = false;
                // JOptionPane.showMessageDialog(null, "You are a loser!");
                // show all of the mines
                // stop the user from having the ability to click on buttons until they reset
                // the game
            } else {
                expand(row, col);
                checkWin();
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (!board[row][col].isSelected() && !firstClick) {
                if (board[row][col].getIcon() == null) {
                    board[row][col].setIcon(flag);
                    board[row][col].setDisabledIcon(flag);
                    board[row][col].setEnabled(false);
                } else {
                    board[row][col].setIcon(null);
                    board[row][col].setDisabledIcon(null);
                    board[row][col].setEnabled(true);
                }
            }
        }
    }

    public void revealMines() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if ((int) board[r][c].getClientProperty("state") == -1) {
                    board[r][c].setIcon(mineIcon);
                    board[r][c].setDisabledIcon(mineIcon);
                    board[r][c].setSelected(true);
                    // board[r][c].setContentAreaFilled(false);
                    board[r][c].setOpaque(true);
                    board[r][c].setBackground(Color.RED);
                }
                board[r][c].setEnabled(false);
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

        if (numMines == totalSpaces - count) {
            timer.cancel();
            reset.setIcon(win);
            // JOptionPane.showMessageDialog(null, "How Cool are you");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == beginner) {
            numMines = 10;
            row = column = 9;
            createBoard(row, column);
        } else if (e.getSource() == inter) {
            numMines = 40;
            row = column = 16;
            createBoard(row, column);
        } else if (e.getSource() == expert) {
            numMines = 99;
            row = 16;
            column = 40;
            createBoard(row, column);
        } else if (e.getSource() == reset) {
            createBoard(row, column);
            timer.cancel();
            timeField.setText("0");
            timePassed = 0;
            firstClick = true;
            gameOn = false;
            reset.setIcon(start);
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

    class UpdateTimer extends TimerTask {
        public void run() {
            if (gameOn) {
                timePassed++;
                timeField.setText(timePassed + "");
                System.out.println(timePassed);
            }
        }
    }
}

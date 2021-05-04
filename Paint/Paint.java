import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Paint extends JPanel implements MouseMotionListener, ActionListener {
    JFrame frame;
    ArrayList<Point> points;
    Color currentColor;
    JMenuBar bar;
    JMenu colorMenu;
    JButton[] colorOptions;
    Color[] colors;

    public Paint() {
        frame = new JFrame("Bestest Paint Program Ever Created by Me");
        frame.add(this);

        bar = new JMenuBar();
        colorMenu = new JMenu("Color Options");

        colors = new Color[] { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN,
                Color.MAGENTA };
        colorOptions = new JButton[colors.length];
        colorMenu.setLayout(new GridLayout(7, 1));
        for (int x = 0; x < colorOptions.length; x++) {
            colorOptions[x] = new JButton();
            colorOptions[x].putClientProperty("colorIndex", x);
            colorOptions[x].setBackground(colors[x]);
            colorOptions[x].addActionListener(this);
            colorMenu.add(colorOptions[x]);

        }
        currentColor = colors[0];

        points = new ArrayList<Point>();
        currentColor = Color.RED;
        this.addMouseMotionListener(this);

        bar.add(colorMenu);
        frame.add(bar, BorderLayout.NORTH);
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // connect the dots!
        for (int x = 0; x < points.size() - 1; x++) {
            Point p1 = points.get(x);
            Point p2 = points.get(x + 1);
            g2.setColor(p1.getColor());
            g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int index = (int) ((JButton) e.getSource()).getClientProperty("colorIndex");
        currentColor = colors[index];

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        // make the dots
        points.add(new Point(e.getX(), e.getY(), currentColor));
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public static void main(String args[]) {
        new Paint();
    }

    public class Point {
        int x, y;
        Color color;

        public Point(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Color getColor() {
            return color;
        }

    }

}

import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Paint extends JPanel
        implements MouseMotionListener, ActionListener, MouseListener, AdjustmentListener, ChangeListener {

    JFrame frame;
    ArrayList<Point> points;
    Color currentColor;
    JMenuBar bar;
    JMenu colorMenu, fileMenu;
    // JButton[] colorOptions;
    JMenuItem save, load, clear, exit;
    JMenuItem[] colorOptions;
    JButton freeLineButton, rectangleButton, ovalButton;
    Color[] colors;
    Stack<Object> shapes;
    Shape currShape;
    boolean drawingFreeLine = true, drawingRectangle = false, drawingOval = false;
    boolean firstClick = true;
    int penWidth, currX, currY, currWidth, currHeight;
    JScrollBar penWidthBar;
    JFileChooser fileChooser;
    BufferedImage loadedImage;
    JColorChooser colorChooser;
    ImageIcon freeLineImg, rectImg, ovalImg;

    public Paint() {
        frame = new JFrame("Bestest Paint Program Ever Created by Me");
        frame.add(this);

        bar = new JMenuBar();
        colorMenu = new JMenu("Color Options");

        colors = new Color[] { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN,
                Color.MAGENTA };
        colorOptions = new JMenuItem[colors.length];
        colorMenu.setLayout(new GridLayout(8, 1));
        for (int x = 0; x < colorOptions.length; x++) {
            colorOptions[x] = new JMenuItem();
            colorOptions[x].putClientProperty("colorIndex", x);
            colorOptions[x].setBackground(colors[x]);
            colorOptions[x].setOpaque(true);
            // colorOptions[x].setBorderPainted(false);
            colorOptions[x].addActionListener(this);
            colorOptions[x].setPreferredSize(new Dimension(50, 30));
            colorMenu.add(colorOptions[x]);
        }

        currentColor = colors[0];
        colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(this);
        colorMenu.add(colorChooser);

        points = new ArrayList<Point>();
        shapes = new Stack<Object>();
        drawingFreeLine = false;

        penWidthBar = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 1, 40);// orientation, starting value, size of doodad,
                                                                         // minvalue, maxvalue
        penWidthBar.addAdjustmentListener(this);
        penWidth = penWidthBar.getValue();

        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        fileMenu = new JMenu("File");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        clear = new JMenuItem("New");
        exit = new JMenuItem("Exit");

        save.addActionListener(this);
        load.addActionListener(this);
        clear.addActionListener(this);
        exit.addActionListener(this);

        fileMenu.add(clear);
        fileMenu.add(load);
        fileMenu.add(save);
        fileMenu.add(exit);

        String currDir = System.getProperty("user.dir");
        fileChooser = new JFileChooser(currDir);

        freeLineImg = new ImageIcon("freeLineImg.png");
        freeLineImg = new ImageIcon(freeLineImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        freeLineButton = new JButton();
        freeLineButton.setIcon(freeLineImg);
        freeLineButton.setFocusPainted(false);
        freeLineButton.setBackground(Color.LIGHT_GRAY);
        freeLineButton.setOpaque(true);
        freeLineButton.setBorderPainted(false);
        freeLineButton.addActionListener(this);

        rectImg = new ImageIcon("rectImg.png");
        rectImg = new ImageIcon(rectImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        rectangleButton = new JButton();
        rectangleButton.setIcon(rectImg);
        rectangleButton.setFocusPainted(false);
        // rectangleButton.setBackground(Color.LIGHT_GRAY);
        rectangleButton.setOpaque(true);
        rectangleButton.setBorderPainted(false);
        rectangleButton.addActionListener(this);

        ovalImg = new ImageIcon("ovalImg.png");
        ovalImg = new ImageIcon(ovalImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ovalButton = new JButton();
        ovalButton.setIcon(ovalImg);
        ovalButton.setFocusPainted(false);
        // ovalButton.setBackground(Color.LIGHT_GRAY);
        ovalButton.setOpaque(true);
        ovalButton.setBorderPainted(false);
        ovalButton.addActionListener(this);

        bar.add(fileMenu);
        bar.add(colorMenu);
        bar.add(freeLineButton);
        bar.add(rectangleButton);
        bar.add(ovalButton);
        bar.add(penWidthBar);
        frame.add(bar, BorderLayout.NORTH);
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        if (loadedImage != null) {
            g2.drawImage(loadedImage, 0, 0, null);
        }
        Iterator<Object> it = shapes.iterator();

        while (it.hasNext()) {// draw free lines from the past
            Object next = it.next();

            if (next instanceof Rectangle) {
                Rectangle temp = (Rectangle) next;
                g2.setColor(temp.getColor());
                g2.setStroke(new BasicStroke(temp.getPenWidth()));
                g2.draw(temp.getShape());
            } else if (next instanceof Oval) {
                Oval temp = (Oval) next;
                g2.setColor(temp.getColor());
                g2.setStroke(new BasicStroke(temp.getPenWidth()));
                g2.draw(temp.getShape());
            } else {
                @SuppressWarnings("unchecked")
                ArrayList<Point> temp = (ArrayList<Point>) next;
                if (temp.size() > 0) {
                    g2.setStroke(new BasicStroke(temp.get(0).getPenWidth()));
                    g2.setColor(temp.get(0).getColor());
                    // connect the dots!
                    for (int x = 0; x < temp.size() - 1; x++) {
                        Point p1 = temp.get(x);
                        Point p2 = temp.get(x + 1);
                        g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                    }
                }
            }
        }

        if (drawingFreeLine && points.size() > 0) {// draw in process of dragging a free line
            g2.setStroke(new BasicStroke(points.get(0).getPenWidth()));
            g2.setColor(points.get(0).getColor());
            for (int x = 0; x < points.size() - 1; x++) {
                Point p1 = points.get(x);
                Point p2 = points.get(x + 1);
                g2.setColor(p1.getColor());
                g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            }
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            FileFilter filter = new FileNameExtensionFilter("*.png", "png");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try {
                    String st = file.getAbsolutePath();
                    if (st.indexOf(".png") >= 0)
                        st = st.substring(0, st.length() - 4);
                    ImageIO.write(createImage(), "png", new File(st + ".png"));
                } catch (IOException ioe) {
                    // TODO: handle exception
                }
            }
        } else if (e.getSource() == load) {
            fileChooser.showOpenDialog(null);
            File imgFile = fileChooser.getSelectedFile();
            if (imgFile != null && imgFile.toString().indexOf(".png") >= 0) {
                try {
                    loadedImage = ImageIO.read(imgFile);
                } catch (IOException ioe) {
                }
                shapes = new Stack<Object>();
                repaint();
            } else {
                if (imgFile != null)
                    JOptionPane.showMessageDialog(null, "Wrong file type. Please select a PNG file.");
            }
        } else if (e.getSource() == clear) {
            shapes = new Stack<Object>();
            loadedImage = null;
            repaint();
        } else if (e.getSource() == exit) {
            System.exit(0);
        } else if (e.getSource() == freeLineButton) {
            drawingFreeLine = true;
            drawingRectangle = false;
            drawingOval = false;
            freeLineButton.setBackground(Color.LIGHT_GRAY);
            rectangleButton.setBackground(null);
            ovalButton.setBackground(null);

        } else if (e.getSource() == rectangleButton) {
            drawingFreeLine = false;
            drawingRectangle = true;
            drawingOval = false;
            freeLineButton.setBackground(null);
            rectangleButton.setBackground(Color.LIGHT_GRAY);
            ovalButton.setBackground(null);
        } else if (e.getSource() == ovalButton) {
            drawingFreeLine = false;
            drawingRectangle = false;
            drawingOval = true;
            freeLineButton.setBackground(null);
            rectangleButton.setBackground(null);
            ovalButton.setBackground(Color.LIGHT_GRAY);
        } else {
            int index = (int) ((JMenuItem) e.getSource()).getClientProperty("colorIndex");
            currentColor = colors[index];
        }

    }

    public void mouseDragged(MouseEvent e) {
        // make the dots!
        if (drawingRectangle || drawingOval) {
            if (firstClick) {
                currX = e.getX();
                currY = e.getY();
                if (drawingRectangle)
                    currShape = new Rectangle(currX, currY, currentColor, penWidth, 0, 0);
                else
                    currShape = new Oval(currX, currY, currentColor, penWidth, 0, 0);
                firstClick = false;
                shapes.add(currShape);
            } else {
                currWidth = Math.abs(e.getX() - currX);
                currHeight = Math.abs(e.getY() - currY);
                currShape.setWidth(currWidth);
                currShape.setHeight(currHeight);
                if (currX <= e.getX() && currY >= e.getY()) {
                    currShape.setY(e.getY());
                } else if (currX >= e.getX() && currY <= e.getY()) {
                    currShape.setX(e.getX());
                } else if (currX >= e.getX() && currY >= e.getY()) {
                    currShape.setX(e.getX());
                    currShape.setY(e.getY());
                }
            }
        } else {
            points.add(new Point(e.getX(), e.getY(), currentColor, penWidth));
            drawingFreeLine = true;
        }

        repaint();
    }

    public static void main(String[] args) {
        new Paint();
    }

    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (drawingRectangle || drawingOval) {
            shapes.push(currShape);
            firstClick = true;
        } else {
            shapes.push(points);
            points = new ArrayList<Point>();
        }
        repaint();
    }

    public BufferedImage createImage() {
        int width = this.getWidth();
        int height = this.getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        this.paint(g2);
        g2.dispose();
        return img;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        penWidth = penWidthBar.getValue();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        currentColor = colorChooser.getColor();
    }

    public class Point {

        int x, y, penWidth;
        Color color;

        public Point(int x, int y, Color color, int penWidth) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.penWidth = penWidth;
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

        public int getPenWidth() {
            return penWidth;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    public class Shape extends Point {
        private int width, height;

        public Shape(int x, int y, Color color, int penWidth, int width, int height) {
            super(x, y, color, penWidth);
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

    }

    public class Rectangle extends Shape {
        public Rectangle(int x, int y, Color color, int penWidth, int width, int height) {
            super(x, y, color, penWidth, width, height);
        }

        public Rectangle2D.Double getShape() {
            return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
        }
    }

    public class Oval extends Shape {
        public Oval(int x, int y, Color color, int penWidth, int width, int height) {
            super(x, y, color, penWidth, width, height);
        }

        public Ellipse2D.Double getShape() {
            return new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        }
    }

}
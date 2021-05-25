import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.*;

public class Paint extends JPanel
        implements MouseMotionListener, ActionListener, MouseListener, AdjustmentListener, ChangeListener, KeyListener {
    JFrame frame;
    ArrayList<Point> points;
    Color currentColor, backgroundColor, oldColor;
    JMenuBar bar;
    Shape currShape;
    JMenu colorMenu, fileMenu;
    JMenuItem save, load, clear, exit;
    JMenuItem[] colorOptions;
    JButton freeLineButton, rectangleButton, ovalButton, undoButton, redoButton, eraserButton;
    Color[] colors;
    Stack<Object> shapes, undoRedoStack;
    boolean drawingFreeLine = true, drawingRectangle = false, drawingOval = false, eraserOn = false;
    boolean firstClick = true;
    int penWidth, currX, currY, currWidth, currHeight;
    JScrollBar penWidthBar;
    JColorChooser colorChooser;
    ImageIcon freeLineImg, rectImg, ovalImg, saveImg, undoImg, redoImg, eraserImg, loadImg;
    JFileChooser fileChooser;
    BufferedImage loadedImage;
    boolean shiftPressed;

    public Paint() {
        frame = new JFrame("Paint Program");
        frame.add(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        points = new ArrayList<Point>();

        shapes = new Stack<Object>();
        undoRedoStack = new Stack<Object>();
        drawingFreeLine = false;
        bar = new JMenuBar();
        colorMenu = new JMenu("Color Options");

        colors = new Color[] { Color.RED, Color.ORANGE, Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.BLUE,
                Color.CYAN };

        colorMenu.setLayout(new GridLayout(7, 1));
        colorOptions = new JMenuItem[colors.length];
        for (int x = 0; x < colorOptions.length; x++) {
            colorOptions[x] = new JMenuItem();
            colorOptions[x].putClientProperty("colorIndex", x);
            colorOptions[x].setBackground(colors[x]);
            colorOptions[x].addActionListener(this);
            colorOptions[x].setFocusable(false);
            colorMenu.add(colorOptions[x]);
        }

        colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(this);

        colorMenu.add(colorChooser);

        penWidthBar = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 1, 40);
        penWidthBar.addAdjustmentListener(this);
        penWidthBar.setFocusable(false);
        penWidth = penWidthBar.getValue();

        fileMenu = new JMenu("File");
        save = new JMenuItem("Save", KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        load = new JMenuItem("Load", KeyEvent.VK_L);
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        clear = new JMenuItem("New");
        exit = new JMenuItem("Exit");

        saveImg = new ImageIcon("saveImg.png");
        loadImg = new ImageIcon("loadImg.png");
        saveImg = new ImageIcon(saveImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        loadImg = new ImageIcon(loadImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        save.setIcon(saveImg);
        load.setIcon(loadImg);

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
        freeLineButton.setFocusable(false);
        freeLineButton.setBackground(Color.LIGHT_GRAY);
        freeLineButton.addActionListener(this);

        rectImg = new ImageIcon("rectImg.png");
        rectImg = new ImageIcon(rectImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        rectangleButton = new JButton();
        rectangleButton.setIcon(rectImg);
        rectangleButton.setFocusPainted(false);
        rectangleButton.setFocusable(false);
        rectangleButton.setBackground(Color.LIGHT_GRAY);
        rectangleButton.addActionListener(this);

        ovalImg = new ImageIcon("ovalImg.png");
        ovalImg = new ImageIcon(ovalImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ovalButton = new JButton();
        ovalButton.setIcon(ovalImg);
        ovalButton.setFocusable(false);
        ovalButton.setFocusPainted(false);
        ovalButton.setBackground(Color.LIGHT_GRAY);
        ovalButton.addActionListener(this);

        eraserImg = new ImageIcon("eraserImg.png");
        eraserImg = new ImageIcon(eraserImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        eraserButton = new JButton();
        eraserButton.setIcon(eraserImg);
        eraserButton.setFocusable(false);
        eraserButton.setFocusPainted(false);
        eraserButton.setBackground(Color.LIGHT_GRAY);
        eraserButton.addActionListener(this);

        undoImg = new ImageIcon("undoImg.png");
        undoImg = new ImageIcon(undoImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        undoButton = new JButton();
        undoButton.setIcon(undoImg);
        undoButton.setFocusable(false);
        undoButton.setFocusPainted(false);
        undoButton.setBackground(Color.LIGHT_GRAY);
        undoButton.addActionListener(this);

        redoImg = new ImageIcon("redoImg.png");
        redoImg = new ImageIcon(redoImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        redoButton = new JButton();
        redoButton.setIcon(redoImg);
        redoButton.setFocusable(false);
        redoButton.setFocusPainted(false);
        redoButton.setBackground(Color.LIGHT_GRAY);
        redoButton.addActionListener(this);

        bar.add(fileMenu);
        bar.add(colorMenu);
        bar.add(freeLineButton);
        bar.add(rectangleButton);
        bar.add(ovalButton);
        bar.add(undoButton);
        bar.add(redoButton);
        bar.add(eraserButton);
        bar.add(penWidthBar);
        frame.add(bar, BorderLayout.NORTH);

        backgroundColor = Color.WHITE;
        currentColor = colors[0];
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        if (loadedImage != null) {
            g2.drawImage(loadedImage, 0, 0, null);
        }
        Iterator it = shapes.iterator();
        while (it.hasNext()) {
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
                ArrayList<?> temp = (ArrayList<?>) next;
                if (temp.size() > 0) {
                    g2.setStroke(new BasicStroke(((Point) temp.get(0)).getPenWidth()));
                    g2.setColor(((Point) temp.get(0)).getColor());
                    for (int x = 0; x < temp.size() - 1; x++) {
                        Point p1 = (Point) temp.get(x);
                        Point p2 = (Point) temp.get(x + 1);
                        g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                    }
                }
            }
        }

        if (drawingFreeLine && points.size() > 0) {
            g2.setStroke(new BasicStroke(points.get(0).getPenWidth()));
            g2.setColor(points.get(0).getColor());
            for (int x = 0; x < points.size() - 1; x++) {
                Point p1 = points.get(x);
                Point p2 = points.get(x + 1);
                g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            }
        }
    }

    public static void main(String[] args) {
        new Paint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            FileFilter filter = new FileNameExtensionFilter("*.png", "png");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try {
                    String st = file.getAbsolutePath();
                    if (st.indexOf(".png") >= 0) {
                        st = st.substring(0, st.length() - 4);
                    }
                    ImageIO.write(createImage(), "png", new File(st + ".png"));
                } catch (IOException ioe) {

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
                    JOptionPane.showMessageDialog(null, "Wrong file type. Please select a PNG File");
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
            eraserOn = false;
            freeLineButton.setBackground(Color.LIGHT_GRAY);
            rectangleButton.setBackground(null);
            ovalButton.setBackground(null);
            eraserButton.setBackground(null);
            currentColor = oldColor;
        } else if (e.getSource() == rectangleButton) {
            drawingFreeLine = false;
            drawingRectangle = true;
            drawingOval = false;
            eraserOn = false;
            freeLineButton.setBackground(null);
            rectangleButton.setBackground(Color.LIGHT_GRAY);
            ovalButton.setBackground(null);
            eraserButton.setBackground(null);
            currentColor = oldColor;
        } else if (e.getSource() == ovalButton) {
            drawingFreeLine = false;
            drawingRectangle = false;
            drawingOval = true;
            eraserOn = false;
            freeLineButton.setBackground(null);
            rectangleButton.setBackground(null);
            eraserButton.setBackground(null);
            ovalButton.setBackground(Color.LIGHT_GRAY);
            currentColor = oldColor;
        } else if (e.getSource() == eraserButton) {
            drawingFreeLine = false;
            drawingRectangle = false;
            drawingOval = false;
            eraserOn = true;
            freeLineButton.setBackground(null);
            rectangleButton.setBackground(null);
            eraserButton.setBackground(null);
            ovalButton.setBackground(Color.LIGHT_GRAY);
            oldColor = currentColor;
            currentColor = backgroundColor;
        } else if (e.getSource() == undoButton) {
            undo();
        } else if (e.getSource() == redoButton) {
            redo();
        } else {
            if (eraserOn) {
                drawingFreeLine = true;
                drawingRectangle = false;
                drawingOval = false;
                eraserOn = false;
                freeLineButton.setBackground(Color.LIGHT_GRAY);
                rectangleButton.setBackground(null);
                ovalButton.setBackground(null);
                eraserButton.setBackground(null);
            }
            int index = (int) ((JMenuItem) e.getSource()).getClientProperty("colorIndex");
            currentColor = colors[index];
        }
    }

    public void redo() {
        if (!undoRedoStack.isEmpty()) {
            shapes.push(undoRedoStack.pop());
            repaint();
        }
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            undoRedoStack.push(shapes.pop());
            repaint();
        }
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
    public void mouseDragged(MouseEvent e) {

        if (drawingRectangle || drawingOval) {
            if (firstClick) {
                currX = e.getX();
                currY = e.getY();
                if (drawingRectangle) {
                    currShape = new Rectangle(currX, currY, currentColor, penWidth, 0, 0);
                } else {
                    currShape = new Oval(currX, currY, currentColor, penWidth, 0, 0);
                }
                firstClick = false;
                shapes.push(currShape);
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
                    currShape.setY(e.getY());
                    currShape.setX(e.getX());
                }
            }
        } else {
            drawingFreeLine = true;
            points.add(new Point(e.getX(), e.getY(), currentColor, penWidth));
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (drawingRectangle || drawingOval) {
            shapes.push(currShape);
            firstClick = true;
        } else {
            shapes.push(points);
            points = new ArrayList<Point>();
            drawingFreeLine = false;
        }
        undoRedoStack = new Stack<Object>();
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

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
        Color c;

        public Point(int x, int y, Color c, int penWidth) {
            this.x = x;
            this.y = y;
            this.c = c;
            this.penWidth = penWidth;
        }

        public int getPenWidth() {
            return penWidth;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Color getColor() {
            return c;
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

        public Shape(int x, int y, Color c, int penWidth, int width, int height) {
            super(x, y, c, penWidth);
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
        public Rectangle(int x, int y, Color c, int penWidth, int width, int height) {
            super(x, y, c, penWidth, width, height);
        }

        public Rectangle2D.Double getShape() {
            return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
        }
    }

    public class Oval extends Shape {
        public Oval(int x, int y, Color c, int penWidth, int width, int height) {
            super(x, y, c, penWidth, width, height);
        }

        public Ellipse2D.Double getShape() {
            return new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown()) {
            if (e.getKeyCode() == KeyEvent.VK_Z) {
                undo();
            }
            if (e.getKeyCode() == KeyEvent.VK_Y) {
                redo();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
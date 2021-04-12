import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class SideScroller extends JPanel implements KeyListener, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JFrame frame;
    BufferedImage aladdinSheet, smallCity;
    BufferedImage[] aladdin = new BufferedImage[13];
    int aladdinCount = 0, count = 0, closeCity = 0;
    boolean right = false;
    Thread timer;

    public SideScroller() {
        frame = new JFrame("Aladdin's Not So Exciting Adventure");
        frame.add(this);

        try {
            aladdinSheet = ImageIO.read(new File("/Users/adityaamk/Desktop/DataStructures/SideScroller/Aladdin.png"));
            smallCity = ImageIO.read(new File("smallCity.png"));
        } catch (IOException e) {

        }
        int[][] locsAndDims = new int[][] {
                // x,y,width,height
                { 337, 3, 23, 53 }, /* standing */ { 4, 64, 31, 53 }, { 34, 64, 31, 51 }, { 62, 64, 31, 51 },
                { 92, 64, 31, 51 }, { 127, 64, 37, 51 }, { 166, 64, 31, 51 }, { 205, 64, 31, 51 }, { 233, 64, 30, 51 },
                { 263, 61, 30, 56 }, { 292, 61, 34, 56 }, { 325, 60, 41, 56 }, { 367, 60, 36, 56 } };

        for (int x = 0; x < 13; x++) {
            aladdin[x] = aladdinSheet.getSubimage(locsAndDims[x][0], locsAndDims[x][1], locsAndDims[x][2],
                    locsAndDims[x][3]);
        }

        frame.addKeyListener(this);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        timer = new Thread(this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, 900, 600);
        g.drawImage(smallCity, closeCity, -40, this);
        g.drawImage(aladdin[aladdinCount], 100, 500, this);

    }

    public void run() {
        while (true) {
            if (right) {
                count++;
                if (count % 20 == 0) {
                    aladdinCount++;
                    if (aladdinCount == 13)
                        aladdinCount = 1;
                }
                closeCity--;
            }
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {

            }
            repaint();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 39)
            right = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 39) {
            right = false;
            aladdinCount = 0;
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {
        new SideScroller();
    }
}

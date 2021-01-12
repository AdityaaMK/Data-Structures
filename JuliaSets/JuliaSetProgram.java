import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class JuliaSetProgram extends JPanel implements AdjustmentListener, MouseListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JFrame frame;
    double a, b, zoom;
    float hue = 0.66f;
    float imageHue = 1.0f;
    float sat = 1.0f;
    float bright = 1.0f;
    float maxIter = 300.0f;
    int pixelSize = 1;
    JScrollBar aBar, bBar, zoomBar, hueBar, imageHueBar, satBar, brightBar;
    JPanel scrollPanel, labelPanel, bigPanel, huePanel, imageHuePanel, satPanel, brightPanel;
    JLabel aLabel, bLabel, zoomLabel, hueLabel, satLabel, imageHueLabel, brightLabel;

    public JuliaSetProgram() {
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(1000, 600);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // orientation,starting value,doodad size,min value,max value

        aBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        a = aBar.getValue() / 1000.0;
        aBar.addMouseListener(this);
        aBar.addAdjustmentListener(this);

        bBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        b = bBar.getValue() / 1000.0;
        bBar.addMouseListener(this);
        bBar.addAdjustmentListener(this);

        hueBar = new JScrollBar(JScrollBar.HORIZONTAL, 666, 0, 0, 1000);
        hue = hueBar.getValue() / 1000.0f;
        hueBar.addMouseListener(this);
        hueBar.addAdjustmentListener(this);

        imageHueBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 0, 1000);
        imageHue = imageHueBar.getValue() / 1000.0f;
        imageHueBar.addMouseListener(this);
        imageHueBar.addAdjustmentListener(this);

        satBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 0, 1000);
        sat = satBar.getValue() / 1000.0f;
        satBar.addMouseListener(this);
        satBar.addAdjustmentListener(this);

        brightBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 0, 1000);
        bright = brightBar.getValue() / 1000.0f;
        brightBar.addMouseListener(this);
        brightBar.addAdjustmentListener(this);

        zoomBar = new JScrollBar(JScrollBar.HORIZONTAL, 10, 0, 0, 100);
        zoom = zoomBar.getValue() / 10.0;
        zoomBar.addMouseListener(this);
        zoomBar.addAdjustmentListener(this);

        aLabel = new JLabel("a");
        bLabel = new JLabel("bi");
        hueLabel = new JLabel("hue");
        imageHueLabel = new JLabel("image hue");
        satLabel = new JLabel("saturation");
        brightLabel = new JLabel("brightness");
        zoomLabel = new JLabel("zoom");

        GridLayout grid = new GridLayout(7, 1);
        labelPanel = new JPanel();
        labelPanel.setLayout(grid);
        labelPanel.add(aLabel);
        labelPanel.add(bLabel);
        labelPanel.add(hueLabel);
        labelPanel.add(imageHueLabel);
        labelPanel.add(satLabel);
        labelPanel.add(brightLabel);
        labelPanel.add(zoomLabel);

        scrollPanel = new JPanel();
        scrollPanel.setLayout(grid);
        scrollPanel.add(aBar);
        scrollPanel.add(bBar);
        scrollPanel.add(hueBar);
        scrollPanel.add(imageHueBar);
        scrollPanel.add(satBar);
        scrollPanel.add(brightBar);
        scrollPanel.add(zoomBar);

        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(labelPanel, BorderLayout.WEST);
        bigPanel.add(scrollPanel, BorderLayout.CENTER);

        frame.add(bigPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawJulia(), 0, 0, null);

    }

    public BufferedImage drawJulia() {
        int width = frame.getWidth();
        int height = frame.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x += pixelSize) {
            for (int y = 0; y < height; y += pixelSize) {
                float i = maxIter;
                double zx = 1.5 * (x - width / 2.0) / (.5 * zoom * width);
                double zy = (y - height / 2.0) / (.5 * zoom * height);
                while (zx * zx + zy * zy < 6 && i > 0) {
                    double temp = zx * zx - zy * zy + a;
                    zy = 2.0 * zx * zy + b;
                    zx = temp;
                    i--;
                }
                int c;
                if (i > 0) {
                    c = Color.HSBtoRGB(hue * (i / maxIter) % 1, sat, bright);
                    // hue, saturation, brightness
                } else
                    c = Color.HSBtoRGB(imageHue, sat, bright);
                image.setRGB(x, y, c);
            }
        }
        return image;
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getSource() == aBar) {
            a = aBar.getValue() / 1000.0;
            aLabel.setText("a: " + a + "\t\t");
        } else if (e.getSource() == bBar) {
            b = bBar.getValue() / 1000.0;
            bLabel.setText("b: " + b + "\t\t");
        } else if (e.getSource() == zoomBar) {
            zoom = zoomBar.getValue() / 10.0;
            zoomLabel.setText("zoom: " + zoom + "\t\t");
        } else if (e.getSource() == hueBar) {
            hue = hueBar.getValue() / 1000.0f;
            hueLabel.setText("hue: " + hue + "\t\t");
        } else if (e.getSource() == imageHueBar) {
            imageHue = imageHueBar.getValue() / 1000.0f;
            imageHueLabel.setText("image hue: " + imageHue + "\t\t");
        } else if (e.getSource() == satBar) {
            sat = satBar.getValue() / 1000.0f;
            satLabel.setText("saturation: " + sat + "\t\t");
        } else if (e.getSource() == brightBar) {
            bright = brightBar.getValue() / 1000.0f;
            brightLabel.setText("brightness: " + bright + "\t\t");
        }
        repaint();
    }

    public void mousePressed(MouseEvent e) {
        pixelSize = 3;
    }

    public void mouseReleased(MouseEvent e) {
        pixelSize = 1;
        repaint();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        JuliaSetProgram app = new JuliaSetProgram();
    }

}
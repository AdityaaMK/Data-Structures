import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JuliaSetProgram extends JPanel implements AdjustmentListener, MouseListener, ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JFrame frame;
    double a, b, zoom, radius;
    int version;
    float hue, imageHue, sat, bright;
    float maxIter = 300.0f;
    int pixelSize = 1;
    JScrollPane juliaPane;
    JScrollBar aBar, bBar, zoomBar, hueBar, imageHueBar, satBar, brightBar, versionBar, radiusBar;
    JPanel scrollPanel, labelPanel, bigPanel, huePanel, imageHuePanel, satPanel, brightPanel, buttonPanel, versionPanel,
            radiusPanel;
    JLabel aLabel, bLabel, zoomLabel, hueLabel, satLabel, imageHueLabel, brightLabel, versionLabel, radiusLabel;
    JButton clear, save;
    JFileChooser fileChooser;
    BufferedImage image;

    public JuliaSetProgram() {
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(1000, 600);
        // this.setLayout(new FlowLayout());

        String currDir = System.getProperty("user.dir");
        fileChooser = new JFileChooser(currDir);

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

        versionBar = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 1, 4);
        version = versionBar.getValue();
        versionBar.addMouseListener(this);
        versionBar.addAdjustmentListener(this);

        radiusBar = new JScrollBar(JScrollBar.HORIZONTAL, 60, 0, 1, 60);
        radius = radiusBar.getValue();
        radiusBar.addMouseListener(this);
        radiusBar.addAdjustmentListener(this);

        aLabel = new JLabel("a");
        bLabel = new JLabel("bi");
        hueLabel = new JLabel("hue");
        imageHueLabel = new JLabel("image hue");
        satLabel = new JLabel("saturation");
        brightLabel = new JLabel("brightness");
        zoomLabel = new JLabel("zoom");
        versionLabel = new JLabel("version");
        radiusLabel = new JLabel("radius");

        GridLayout grid = new GridLayout(9, 1);
        labelPanel = new JPanel();
        labelPanel.setLayout(grid);
        labelPanel.add(aLabel);
        labelPanel.add(bLabel);
        labelPanel.add(hueLabel);
        labelPanel.add(imageHueLabel);
        labelPanel.add(satLabel);
        labelPanel.add(brightLabel);
        labelPanel.add(zoomLabel);
        labelPanel.add(versionLabel);
        labelPanel.add(radiusLabel);
        scrollPanel = new JPanel();
        scrollPanel.setLayout(grid);
        scrollPanel.add(aBar);
        scrollPanel.add(bBar);
        scrollPanel.add(hueBar);
        scrollPanel.add(imageHueBar);
        scrollPanel.add(satBar);
        scrollPanel.add(brightBar);
        scrollPanel.add(zoomBar);
        scrollPanel.add(versionBar);
        scrollPanel.add(radiusBar);

        clear = new JButton("Reset");
        save = new JButton("Save");
        clear.addActionListener(this);
        save.addActionListener(this);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(clear);
        buttonPanel.add(save);

        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(labelPanel, BorderLayout.WEST);
        bigPanel.add(scrollPanel, BorderLayout.CENTER);
        bigPanel.add(buttonPanel, BorderLayout.EAST);

        frame.add(bigPanel, BorderLayout.SOUTH);

        // juliaPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        // JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // this.setPreferredSize(new Dimension(frame.getWidth() * 2, frame.getHeight() *
        // 2));

        // juliaPane.getVerticalScrollBar().addMouseListener(this);
        // juliaPane.getHorizontalScrollBar().addMouseListener(this);

        // frame.add(juliaPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawJulia(), 0, 0, null);

    }

    public BufferedImage drawJulia() {
        int width = frame.getWidth();
        int height = frame.getHeight();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x += pixelSize) {
            for (int y = 0; y < height; y += pixelSize) {
                float i = maxIter;
                double zx = 1.5 * (x - width / 2.0) / (.5 * zoom * width);
                double zy = (y - height / 2.0) / (.5 * zoom * height);
                switch (version) {
                    case 1:
                        while (zx * zx + zy * zy < radius && i > 0) {
                            double temp = zx * zx - zy * zy + a;
                            zy = 2.0 * zx * zy + b;
                            zx = temp;
                            i--;
                        }
                        break;
                    case 2:
                        while (zx + zy * zy < radius && i > 0) {
                            double temp = zx - zy * zy + a;
                            zy = 2.0 * zx * zy + b;
                            zx = temp;
                            i--;
                        }
                        break;
                    case 3:
                        while (zx * zx + zy < radius && i > 0) {
                            double temp = zx * zx - zy + a;
                            zy = 2.0 * zx * zy + b;
                            zx = temp;
                            i--;
                        }
                    case 4:
                        while (zx + zy < radius && i > 0) {
                            double temp = zx - zy + a;
                            zy = 2.0 * zx * zy + b;
                            zx = temp;
                            i--;
                        }
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
        } else if (e.getSource() == versionBar) {
            version = versionBar.getValue();
            versionLabel.setText("version: " + version + "\t\t");
        } else if (e.getSource() == radiusBar) {
            radius = radiusBar.getValue() / 10.0;
            radiusLabel.setText("radius: " + radius + "\t\t");
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

    public void saveImage() {
        if (image != null) // juliaImage is the BufferedImage I declared globally (and used in
                           // the drawJulia method)
        {
            FileFilter filter = new FileNameExtensionFilter("*.png", "png");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String st = file.getAbsolutePath();
                    if (st.indexOf(".png") >= 0)
                        st = st.substring(0, st.length() - 4);
                    ImageIO.write(image, "png", new File(st + ".png"));
                } catch (IOException e) {
                }

            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clear) {
            a = b = 0;
            zoom = 1;
            hue = 0.66f;
            imageHue = 1.0f;
            sat = 1.0f;
            bright = 1.0f;
            maxIter = 300.0f;
            repaint();
        }
        if (e.getSource() == save) {
            saveImage();
        }
    }

    public static void main(String[] args) {
        app = new JuliaSetProgram();
    }

}
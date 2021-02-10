import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class SoundMatrix extends JFrame implements Runnable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JToggleButton button[][] = new JToggleButton[2][2];
    JPanel panel = new JPanel();
    boolean notStopped = true;
    JFrame frame = new JFrame();
    String[] clipNames;
    Clip[] clip;

    public SoundMatrix() {
        clipNames = new String[] { "MB1-B3-1.wav" };
        clip = new Clip[clipNames.length];
        try {
            for (int x = 0; x < clipNames.length; x++) {
                URL url = this.getClass().getClassLoader().getResource(clipNames[x]);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                clip[x] = AudioSystem.getClip();
                clip[x].open(audioIn);
            }

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        panel.setLayout(new GridLayout(2, 2, 10, 10)); // the last two numbers "space out" the buttons
        for (int y = 0; y < 2; y++)
            for (int x = 0; x < 2; x++) {

                button[x][y] = new JToggleButton();
                panel.add(button[x][y]);
            }
        this.add(panel);
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Thread timing = new Thread(this);
        timing.start();
    }

    public void run() {
        do {
            try {
                if (button[0][0].isSelected()) {
                    clip[0].start();
                }
                new Thread().sleep(1000);
                for (int x = 0; x < clip.length; x++) {
                    clip[x].stop();
                    clip[x].setFramePosition(0);
                }
            } catch (InterruptedException e) {
            }
        } while (notStopped);

    }

    public static void main(String args[]) {
        new SoundMatrix();

    }
}
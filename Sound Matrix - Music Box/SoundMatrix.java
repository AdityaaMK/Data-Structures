import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SoundMatrix extends JFrame implements Runnable, AdjustmentListener, ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JToggleButton button[][] = new JToggleButton[37][180];
    JScrollPane buttonPane;
    JScrollBar tempoBar;
    JMenuBar menuBar;
    JMenu file, instrumentMenu;
    JMenuItem save, load;
    JMenuItem[] instrumentItems;
    JButton stopPlay, clear;
    JFileChooser fileChooser;
    JLabel[] labels = new JLabel[button.length];
    JPanel buttonPanel, labelPanel, tempoPanel, menuButtonPanel;
    JLabel tempoLabel;
    boolean notStopped = true;
    JFrame frame = new JFrame();
    String[] clipNames;
    Clip[] clip;
    int tempo;
    boolean playing = false;
    int row = 0, col = 0;
    Font font = new Font("Times New Roman", Font.PLAIN, 10);
    String[] instrumentNames = { "Bell", "Piano" };

    public SoundMatrix() {
        setSize(100, 800);
        clipNames = new String[] { "C0", "B1", "ASharp1", "A1", "GSharp1", "G1", "FSharp1", "F1", "E1", "DSharp1", "D1",
                "CSharp1", "C1", "B2", "ASharp2", "A2", "GSharp2", "G2", "FSharp2", "F2", "E2", "DSharp2", "D2",
                "CSharp2", "C2", "B3", "ASharp3", "A3", "GSharp3", "G3", "FSharp3", "F3", "E3", "DSharp3", "D3",
                "CSharp3", "C3", };
        clip = new Clip[clipNames.length];
        String initInstrument = instrumentNames[0] + "/" + instrumentNames[0];
        try {
            for (int x = 0; x < clipNames.length; x++) {
                URL url = this.getClass().getClassLoader().getResource(initInstrument + " - " + clipNames[x] + ".wav");
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

        buttonPanel = new JPanel();

        buttonPanel.setLayout(new GridLayout(button.length, button[0].length, 2, 5)); // the last two numbers "space
                                                                                      // out" the buttons
        for (int r = 0; r < button.length; r++) {

            String name = clipNames[r].replaceAll("Sharp", "#");
            for (int c = 0; c < button[0].length; c++) {
                button[r][c] = new JToggleButton();
                button[r][c].setFont(font);
                button[r][c].setText(name);
                button[r][c].setPreferredSize(new Dimension(30, 30));
                button[r][c].setMargin(new Insets(0, 0, 0, 0));
                buttonPanel.add(button[r][c]);
            }
        }

        tempoBar = new JScrollBar(JScrollBar.HORIZONTAL, 200, 0, 50, 1000);
        tempoBar.addAdjustmentListener(this);
        tempo = tempoBar.getValue();
        tempoLabel = new JLabel("Tempo: " + tempo);
        tempoPanel = new JPanel(new BorderLayout());
        tempoPanel.add(tempoLabel, BorderLayout.WEST);
        tempoPanel.add(tempoBar, BorderLayout.CENTER);

        String currDir = System.getProperty("user.dir");
        fileChooser = new JFileChooser(currDir);

        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(1, 1));
        file = new JMenu("File");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        file.add(save);
        file.add(load);
        save.addActionListener(this);
        load.addActionListener(this);

        instrumentMenu = new JMenu("instruments");
        instrumentItems = new JMenuItem[instrumentNames.length];
        for (int x = 0; x < instrumentNames.length; x++) {
            instrumentItems[x] = new JMenuItem(instrumentNames[x]);
            instrumentItems[x].addActionListener(this);
            instrumentMenu.add(instrumentItems[x]);
        }
        menuBar.add(file);
        menuBar.add(instrumentMenu);

        menuButtonPanel = new JPanel();
        menuButtonPanel.setLayout(new GridLayout(1, 2));
        stopPlay = new JButton("Play");
        stopPlay.addActionListener(this);
        menuButtonPanel.add(stopPlay);
        clear = new JButton("Clear");
        clear.addActionListener(this);
        menuButtonPanel.add(clear);
        menuBar.add(menuButtonPanel, BorderLayout.EAST);

        buttonPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(buttonPane, BorderLayout.CENTER);
        this.add(tempoPanel, BorderLayout.SOUTH);
        this.add(menuBar, BorderLayout.NORTH);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Thread timing = new Thread(this);
        timing.start();
    }

    public void run() {
        do {
            try {
                if (!playing) {
                    new Thread().sleep(0);
                } else {

                    for (int r = 0; r < button.length; r++) {
                        if (button[r][col].isSelected()) {
                            clip[r].start();
                            button[r][col].setForeground(Color.YELLOW);

                        }

                    }

                    new Thread().sleep(tempo);
                    for (int r = 0; r < button.length; r++) {
                        if (button[r][col].isSelected()) {
                            clip[r].stop();
                            clip[r].setFramePosition(0);
                            button[r][col].setForeground(Color.BLACK);
                        }
                    }
                    col++;
                    if (col == button[0].length)
                        col = 0;
                }

            } catch (InterruptedException e) {
            }
        } while (notStopped);

    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        tempo = tempoBar.getValue();
        tempoLabel.setText("Tempo: " + tempo);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stopPlay) {
            playing = !playing;
            if (!playing)
                stopPlay.setText("Play");
            else
                stopPlay.setText("Stop");

        }
        if (e.getSource() == load) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File loadFile = fileChooser.getSelectedFile();
                    BufferedReader input = new BufferedReader(new FileReader(loadFile));
                    String temp;
                    temp = input.readLine();
                    tempo = Integer.parseInt(temp.substring(0, 3));
                    tempoBar.setValue(tempo);
                    Character[][] song = new Character[button.length][temp.length() - 2];

                    int r = 0;
                    System.out.println("song: " + song.length + ", " + song[0].length);
                    while ((temp = input.readLine()) != null) {
                        for (int c = 2; c < song[0].length; c++) {
                            song[r][c - 2] = temp.charAt(c);
                        }
                        r++;
                    }
                    setNotes(song);
                } catch (IOException ee) {
                }
                col = 0;
                playing = false;
                stopPlay.setText("Play");
            }
        }
        if (e.getSource() == save) {
            saveSong();
        }
        if (e.getSource() == clear) {
            for (int r = 0; r < button.length; r++) {

                for (int c = 0; c < button[0].length; c++) {
                    button[r][c].setSelected(false);
                }
            }
        }
        for (int y = 0; y < instrumentItems.length; y++) {
            if (e.getSource() == instrumentItems[y]) {
                String selectedInstrument = "\\" + instrumentNames[y] + "\\" + instrumentNames[y];
                try {
                    for (int x = 0; x < clipNames.length; x++) {
                        URL url = this.getClass().getClassLoader()
                                .getResource(selectedInstrument + " - " + clipNames[x] + ".wav");
                        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                        clip[x] = AudioSystem.getClip();
                        clip[x].open(audioIn);
                    }
                } catch (UnsupportedAudioFileException ee) {
                    ee.printStackTrace();
                } catch (IOException ee) {
                    ee.printStackTrace();
                } catch (LineUnavailableException ee) {
                    ee.printStackTrace();
                }
                col = 0;
                playing = false;
                stopPlay.setText("Play");
            }
        }

    }

    public void setNotes(Character[][] notes) {
        buttonPane.remove(buttonPanel);

        for (int r = 0; r < button.length; r++) {

            for (int c = 0; c < button[0].length; c++) {
                buttonPanel.remove(button[r][c]);
            }
        }
        button = new JToggleButton[37][notes[0].length];
        buttonPanel.setLayout(new GridLayout(button.length, button[0].length));
        for (int r = 0; r < button.length; r++) {

            String name = clipNames[r].replaceAll("Sharp", "#");
            for (int c = 0; c < button[0].length; c++) {
                button[r][c] = new JToggleButton();
                button[r][c].setFont(font);
                button[r][c].setText(name);
                button[r][c].setPreferredSize(new Dimension(30, 30));
                button[r][c].setMargin(new Insets(0, 0, 0, 0));
                buttonPanel.add(button[r][c]);
            }
        }
        this.remove(buttonPane);
        buttonPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(buttonPane, BorderLayout.CENTER);

        for (int r = 0; r < button.length; r++) {

            for (int c = 0; c < button[0].length; c++) {
                try {
                    if (notes[r][c] == 'x')
                        button[r][c].setSelected(true);
                    else
                        button[r][c].setSelected(false);
                } catch (NullPointerException npe) {
                } catch (ArrayIndexOutOfBoundsException ae) {
                }
            }
        }
        this.revalidate();

    }

    public void saveSong() {

        FileFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String st = file.getAbsolutePath();
                if (st.indexOf(".txt") >= 0)
                    st = st.substring(0, st.length() - 4);
                String output = "";
                String[] noteNames = { "  ", "c ", "b ", "a-", "a ", "g-", "g ", "f-", "f ", "e ", "d-", "d ", "c-" };

                for (int r = 0; r < button.length + 1; r++) {
                    output += noteNames[r];
                    if (r == 0) {
                        for (int x = 0; x < button[0].length; x++)
                            output += x % 10;
                    } else {
                        for (int c = 0; c < button[0].length; c++) {
                            if (button[r - 1][c].isSelected())
                                output += "X";
                            else
                                output += "-";
                        }
                    }
                    output += "\n";
                }
                BufferedWriter outputStream = null;
                outputStream = new BufferedWriter(new FileWriter(st + ".txt"));
                outputStream.write(output);
                outputStream.close();
            } catch (IOException exc) {

            }
        }
    }

    public static void main(String args[]) {
        new SoundMatrix();
    }
}

import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Task extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JFrame frame;
    JPanel btnPanel, bigPanel;
    JMenuBar menu;
    GridLayout btnLayout, menuLayout, bigLayout;
    JButton northBtn, southBtn, eastBtn, westBtn, resetBtn;
    JMenu fontOptionMenu, fontSizeMenu, textColorMenu, bgColorMenu, btnOutlineColorMenu;
    JMenuItem[] fontOptions, fontSizes, textColors, bgColors, btnOutlineColors;
    String[] fontNames, bgColorNames, textColorNames, outlineColorNames;
    int[] fontSizeArr;
    JTextArea textArea;
    Font currFont;
    int currFontSize = 14;
    Font[] allFonts;
    Color[] textColorArr, outlineColorArr, bgColorArr;

    public Task() {
        frame = new JFrame("GUI Task");
        frame.setLayout(new BorderLayout());
        menu = new JMenuBar();
        fontOptionMenu = new JMenu("Font");
        fontSizeMenu = new JMenu("Font Size");
        textColorMenu = new JMenu("Text Color");
        bgColorMenu = new JMenu("BG Color");
        btnOutlineColorMenu = new JMenu("Button Color");

        menu.setLayout(new GridLayout(1, 5));
        menu.add(fontOptionMenu);
        menu.add(fontSizeMenu);
        menu.add(textColorMenu);
        menu.add(bgColorMenu);
        menu.add(btnOutlineColorMenu);

        fontNames = new String[] { "Times New Roman", "Arial", "Consolas", "Courier New" };
        allFonts = new Font[fontNames.length];
        fontOptions = new JMenuItem[fontNames.length];

        fontSizeArr = new int[] { 14, 18, 24, 36, 48 };
        fontSizes = new JMenuItem[fontSizeArr.length];

        Random rand = new Random();
        outlineColorArr = new Color[] { Color.WHITE, Color.BLUE, Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW,
                new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)) };
        outlineColorNames = new String[] { "No Color", "Blue", "Red", "Black", "Green", "Yellow", "Random" };
        btnOutlineColors = new JMenuItem[outlineColorArr.length];

        bgColorArr = new Color[] { Color.WHITE, Color.BLUE, Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW,
                new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)) };
        bgColorNames = new String[] { "White", "Blue", "Red", "Black", "Green", "Yellow", "Random" };
        bgColors = new JMenuItem[bgColorArr.length];

        textColorArr = new Color[] { Color.WHITE, Color.BLUE, Color.RED, Color.BLACK, Color.GREEN, Color.YELLOW,
                new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)) };
        textColorNames = new String[] { "White", "Blue", "Red", "Black", "Green", "Yellow", "Random" };
        textColors = new JMenuItem[textColorArr.length];

        for (int i = 0; i < fontNames.length; i++) {
            allFonts[i] = new Font(fontNames[i], Font.PLAIN, currFontSize);
            fontOptions[i] = new JMenuItem(fontNames[i]);
            fontOptions[i].setFont(allFonts[i]);
            fontOptions[i].addActionListener(this);
            fontOptionMenu.add(fontOptions[i]);
        }

        currFont = allFonts[0];
        for (int i = 0; i < fontSizeArr.length; i++) {
            Font temp = new Font(currFont.getName(), Font.PLAIN, currFontSize);
            fontSizes[i] = new JMenuItem(fontSizeArr[i] + "");
            fontSizes[i].setFont(temp);
            fontSizes[i].addActionListener(this);
            fontSizeMenu.add(fontSizes[i]);
        }
        for (int i = 0; i < textColorArr.length; i++) {
            Font temp = new Font(currFont.getName(), Font.PLAIN, currFontSize);
            textColors[i] = new JMenuItem(textColorNames[i] + "");
            textColors[i].setFont(temp);
            textColors[i].setForeground(textColorArr[i]);
            textColors[i].addActionListener(this);
            textColorMenu.add(textColors[i]);
        }

        for (int i = 0; i < outlineColorArr.length; i++) {
            Font temp = new Font(currFont.getName(), Font.PLAIN, currFontSize);
            btnOutlineColors[i] = new JMenuItem(outlineColorNames[i] + "");
            btnOutlineColors[i].setFont(temp);
            btnOutlineColors[i].setForeground(outlineColorArr[i]);
            btnOutlineColors[i].addActionListener(this);
            btnOutlineColorMenu.add(btnOutlineColors[i]);
        }

        for (int i = 0; i < bgColorArr.length; i++) {
            Font temp = new Font(currFont.getName(), Font.PLAIN, currFontSize);
            bgColors[i] = new JMenuItem(bgColorNames[i] + "");
            bgColors[i].setFont(temp);
            bgColors[i].setForeground(bgColorArr[i]);
            bgColors[i].addActionListener(this);
            bgColorMenu.add(bgColors[i]);
        }

        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(this);
        menu.add(resetBtn);

        northBtn = new JButton("North");
        northBtn.addActionListener(this);
        southBtn = new JButton("South");
        southBtn.addActionListener(this);
        eastBtn = new JButton("East");
        eastBtn.addActionListener(this);
        westBtn = new JButton("West");
        westBtn.addActionListener(this);

        bigLayout = new GridLayout();
        bigPanel = new JPanel();
        bigPanel.setLayout(bigLayout);

        btnPanel = new JPanel();
        btnLayout = new GridLayout(1, 2);
        btnPanel.setLayout(btnLayout);
        btnPanel.add(northBtn);
        btnPanel.add(southBtn);
        btnPanel.add(eastBtn);
        btnPanel.add(westBtn);

        northBtn.setBorder(new LineBorder(outlineColorArr[0]));
        eastBtn.setBorder(new LineBorder(outlineColorArr[0]));
        westBtn.setBorder(new LineBorder(outlineColorArr[0]));
        southBtn.setBorder(new LineBorder(outlineColorArr[0]));
        resetBtn.setBorder(new LineBorder(outlineColorArr[0]));

        textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setFont(allFonts[0]);

        bigPanel.add(btnPanel);
        bigPanel.add(menu);

        frame.add(bigPanel, BorderLayout.NORTH);
        frame.add(textArea, BorderLayout.CENTER);
        frame.setSize(1500, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == northBtn || e.getSource() == southBtn) {
            frame.remove(bigPanel);
            btnLayout = new GridLayout(1, 4);
            bigLayout = new GridLayout(1, 2);
            btnPanel.setLayout(btnLayout);
            bigPanel.setLayout(bigLayout);
            bigPanel.remove(menu);
            bigPanel.remove(btnPanel);
            bigPanel.add(btnPanel);
            bigPanel.add(menu);
            menu.setLayout(new GridLayout());
            menu.remove(fontOptionMenu);
            menu.remove(fontSizeMenu);
            menu.remove(resetBtn);
            menu.remove(textColorMenu);
            menu.remove(bgColorMenu);
            menu.remove(btnOutlineColorMenu);
            menu.add(fontOptionMenu);
            menu.add(fontSizeMenu);
            menu.add(textColorMenu);
            menu.add(bgColorMenu);
            menu.add(btnOutlineColorMenu);
            menu.add(resetBtn);
            if (e.getSource() == northBtn)
                frame.add(bigPanel, BorderLayout.NORTH);
            else
                frame.add(bigPanel, BorderLayout.SOUTH);
        }
        if (e.getSource() == eastBtn || e.getSource() == westBtn) {
            frame.remove(bigPanel);
            btnLayout = new GridLayout(4, 1);
            bigLayout = new GridLayout(2, 1);
            btnPanel.setLayout(btnLayout);
            bigPanel.setLayout(bigLayout);
            bigPanel.remove(menu);
            bigPanel.remove(btnPanel);
            bigPanel.add(btnPanel);
            bigPanel.add(menu);
            menu.setLayout(new GridLayout(6, 1));
            menu.remove(fontOptionMenu);
            menu.remove(fontSizeMenu);
            menu.remove(resetBtn);
            menu.remove(textColorMenu);
            menu.remove(bgColorMenu);
            menu.remove(btnOutlineColorMenu);
            menu.add(fontOptionMenu);
            menu.add(fontSizeMenu);
            menu.add(textColorMenu);
            menu.add(bgColorMenu);
            menu.add(btnOutlineColorMenu);
            menu.add(resetBtn);
            if (e.getSource() == eastBtn)
                frame.add(bigPanel, BorderLayout.EAST);
            else
                frame.add(bigPanel, BorderLayout.WEST);
        }
        for (int x = 0; x < fontOptions.length; x++) {
            if (e.getSource() == fontOptions[x]) {
                currFont = new Font(allFonts[x].getName(), Font.PLAIN, currFontSize);
                textArea.setFont(currFont);
                northBtn.setFont(currFont);
                eastBtn.setFont(currFont);
                westBtn.setFont(currFont);
                southBtn.setFont(currFont);
                fontOptionMenu.setFont(currFont);
                fontSizeMenu.setFont(currFont);
                textColorMenu.setFont(currFont);
                bgColorMenu.setFont(currFont);
                btnOutlineColorMenu.setFont(currFont);
                resetBtn.setFont(currFont);
                for (int i = 0; i < fontSizes.length; i++) {
                    fontSizes[i].setFont(currFont);
                }
                for (int i = 0; i < textColors.length; i++) {
                    textColors[i].setFont(currFont);
                }
                for (int i = 0; i < bgColors.length; i++) {
                    bgColors[i].setFont(currFont);
                }
                for (int i = 0; i < btnOutlineColors.length; i++) {
                    btnOutlineColors[i].setFont(currFont);
                }
            }
        }
        for (int x = 0; x < fontSizes.length; x++) {
            if (e.getSource() == fontSizes[x]) {
                currFont = new Font(currFont.getName(), Font.PLAIN, fontSizeArr[x]);
                textArea.setFont(currFont);
            }
        }
        for (int x = 0; x < textColors.length; x++) {
            if (e.getSource() == textColors[x]) {
                textArea.setForeground(textColorArr[x]);
            }
        }
        for (int x = 0; x < bgColors.length; x++) {
            if (e.getSource() == bgColors[x]) {
                textArea.setBackground(bgColorArr[x]);
            }
        }
        for (int x = 0; x < btnOutlineColors.length; x++) {
            if (e.getSource() == btnOutlineColors[x]) {
                northBtn.setBorder(new LineBorder(outlineColorArr[x]));
                eastBtn.setBorder(new LineBorder(outlineColorArr[x]));
                westBtn.setBorder(new LineBorder(outlineColorArr[x]));
                southBtn.setBorder(new LineBorder(outlineColorArr[x]));
                resetBtn.setBorder(new LineBorder(outlineColorArr[x]));
            }
        }
        if (e.getSource() == resetBtn) {
            frame.remove(bigPanel);
            btnLayout = new GridLayout(1, 4);
            bigLayout = new GridLayout(1, 2);
            btnPanel.setLayout(btnLayout);
            bigPanel.setLayout(bigLayout);
            bigPanel.remove(menu);
            bigPanel.remove(btnPanel);
            bigPanel.add(btnPanel);
            bigPanel.add(menu);
            menu.setLayout(new GridLayout());
            menu.remove(fontOptionMenu);
            menu.remove(fontSizeMenu);
            menu.remove(resetBtn);
            menu.remove(textColorMenu);
            menu.remove(bgColorMenu);
            menu.remove(btnOutlineColorMenu);
            menu.add(fontOptionMenu);
            menu.add(fontSizeMenu);
            menu.add(textColorMenu);
            menu.add(bgColorMenu);
            menu.add(btnOutlineColorMenu);
            menu.add(resetBtn);
            frame.add(bigPanel, BorderLayout.NORTH);
            textArea.setText(null);
            textArea.setBackground(bgColorArr[0]);
            textArea.setForeground(textColorArr[0]);
            Font temp = new Font(fontNames[0], Font.PLAIN, fontSizeArr[0]);
            textArea.setFont(temp);
            northBtn.setBorder(new LineBorder(outlineColorArr[0]));
            eastBtn.setBorder(new LineBorder(outlineColorArr[0]));
            westBtn.setBorder(new LineBorder(outlineColorArr[0]));
            southBtn.setBorder(new LineBorder(outlineColorArr[0]));
            resetBtn.setBorder(new LineBorder(outlineColorArr[0]));
        }
        frame.revalidate();
    }

    public static void main(String[] args) {
        new Task();
    }
}